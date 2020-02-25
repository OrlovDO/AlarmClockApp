package tech.orlov.alarmclockapp.domain

import io.reactivex.Completable
import io.reactivex.Observable
import tech.orlov.alarmclockapp.alarm.AlarmManagerRepository
import javax.inject.Inject

class RestartAllAlarmsCommand @Inject constructor(
    private val alarmManagerRepository: AlarmManagerRepository,
    private val getAllAlarmsCommand: GetAllAlarmsCommand
) {
    fun restartAlarms(): Completable {
        return getAllAlarmsCommand.getAllAlarms()
            .flatMapObservable { Observable.fromIterable(it)}
            .flatMapCompletable { alarmManagerRepository.setAlarm(it).ignoreElement()}
    }
}