package tech.orlov.alarmclockapp.domain

import io.reactivex.Completable
import tech.orlov.alarmclockapp.alarm.AlarmManagerRepository
import tech.orlov.alarmclockapp.data.AlarmDataBaseRepository
import javax.inject.Inject

class DeleteAlarmCommand @Inject constructor(
    private val alarmManagerRepository: AlarmManagerRepository,
    private val alarmDataBaseRepository: AlarmDataBaseRepository
) {
    fun deleteAlarm(id: Long): Completable {
        return alarmManagerRepository.cancelAlarm(id)
            .andThen(alarmDataBaseRepository.deleteAlarm(id))
    }
}