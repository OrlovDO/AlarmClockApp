package tech.orlov.alarmclockapp.domain

import io.reactivex.Single
import io.reactivex.functions.BiFunction
import tech.orlov.alarmclockapp.alarm.AlarmManagerRepository
import tech.orlov.alarmclockapp.data.AlarmDataBaseRepository
import javax.inject.Inject

class AddNewAlarmCommand @Inject constructor(
    private val alarmManagerRepository: AlarmManagerRepository,
    private val alarmDataBaseRepository: AlarmDataBaseRepository
) {
    fun addNewAlarm(hours: Int, minutes: Int): Single<Pair<AlarmInfo, Long?>> {
        return alarmDataBaseRepository.saveNewAlarm(
            hours,
            minutes,
            false,
            emptyList()
        )
            .flatMap { newAlarm ->
                Single.zip(
                    alarmManagerRepository.setAlarm(newAlarm),
                    Single.just(newAlarm),
                    BiFunction<List<Long>, AlarmInfo, Pair<AlarmInfo, Long?>> { timeList, alarm ->
                        alarm to timeList.min()
                    }
                )
            }
    }
}