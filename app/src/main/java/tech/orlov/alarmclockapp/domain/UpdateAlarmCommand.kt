package tech.orlov.alarmclockapp.domain

import io.reactivex.Single
import io.reactivex.functions.BiFunction
import tech.orlov.alarmclockapp.alarm.AlarmManagerRepository
import tech.orlov.alarmclockapp.data.AlarmDataBaseRepository
import javax.inject.Inject

class UpdateAlarmCommand @Inject constructor(
    private val alarmManagerRepository: AlarmManagerRepository,
    private val alarmDataBaseRepository: AlarmDataBaseRepository
) {
    fun updateAlarm(
        id: Long,
        hours: Int? = null,
        minutes: Int? = null,
        isEnabled: Boolean? = null,
        daysOfWeek: List<AlarmDayOfWeek>? = null
    ): Single<Pair<AlarmInfo, Long?>> {
        return alarmDataBaseRepository.getAlarmById(id)
            .flatMap { alarmDataBaseRepository.updateAlarm(AlarmInfo(
                id,
                hours?: it.hours,
                minutes?: it.minutes,
                isEnabled?: it.isEnabled,
                daysOfWeek?: it.daysOfWeek
            )) }.flatMap { updatedAlarm ->
                Single.zip(
                    alarmManagerRepository.setAlarm(updatedAlarm),
                    Single.just(updatedAlarm),
                    BiFunction<List<Long>, AlarmInfo, Pair<AlarmInfo, Long?>> { timeList, alarm ->
                        alarm to timeList.min()
                    }
                )
            }
        }
}