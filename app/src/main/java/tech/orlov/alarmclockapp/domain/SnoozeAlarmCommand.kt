package tech.orlov.alarmclockapp.domain

import io.reactivex.Completable
import tech.orlov.alarmclockapp.alarm.AlarmManagerRepository
import javax.inject.Inject

class SnoozeAlarmCommand @Inject constructor(
    private val alarmManagerRepository: AlarmManagerRepository
) {
    fun snoozeAlarm(): Completable {
        return alarmManagerRepository.snoozeAlarm()
    }
}