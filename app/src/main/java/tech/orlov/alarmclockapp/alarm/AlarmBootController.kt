package tech.orlov.alarmclockapp.alarm

import tech.orlov.alarmclockapp.domain.RestartAllAlarmsCommand
import javax.inject.Inject

class AlarmBootController @Inject constructor(
    private val restartAllAlarmsCommand: RestartAllAlarmsCommand
) {
    fun restartAlarms() {
        restartAllAlarmsCommand.restartAlarms()
            .subscribe()
    }
}