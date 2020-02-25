package tech.orlov.alarmclockapp.alarm

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import tech.orlov.alarmclockapp.domain.AlarmInfo
import javax.inject.Inject

class AlarmManagerRepository @Inject constructor(
    private val alarmSource: AlarmSource,
    private val alarmDtoMapper: AlarmDtoMapper
) {
    fun setAlarm(alarmInfo: AlarmInfo): Single<List<Long>> {
        return Single.create<List<Long>> { emitter ->
            if(alarmInfo.isEnabled) {
                val dateList = alarmSource.setRepeatableAlarm(alarmDtoMapper.map(alarmInfo))
                emitter.onSuccess(dateList)
            } else {
                cancelAlarm(alarmInfo.id)
                emitter.onSuccess(emptyList())
            }
        }.subscribeOn(Schedulers.io())
    }

    fun cancelAlarm(id: Long): Completable {
        return Completable.fromAction {
            alarmSource.cancelAlarm(id)
        }.subscribeOn(Schedulers.io())
    }

    fun snoozeAlarm(): Completable {
        return Completable.fromAction {
            alarmSource.snoozeAlarm()
        }.subscribeOn(Schedulers.io())
    }
}