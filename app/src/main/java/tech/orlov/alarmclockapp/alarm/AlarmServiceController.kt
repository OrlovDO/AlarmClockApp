package tech.orlov.alarmclockapp.alarm

import io.reactivex.disposables.CompositeDisposable
import tech.orlov.alarmclockapp.domain.SnoozeAlarmCommand
import javax.inject.Inject

class AlarmServiceController @Inject constructor(
    private val snoozeAlarmCommand: SnoozeAlarmCommand
) {

    private val disposables = CompositeDisposable()

    fun snoozeAlarm() {
        disposables.add(
            snoozeAlarmCommand.snoozeAlarm()
                .subscribe({

                }, {
                    it.printStackTrace()
                })
        )

    }
}