package tech.orlov.alarmclockapp.data

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import tech.orlov.alarmclockapp.domain.AlarmDayOfWeek
import tech.orlov.alarmclockapp.domain.AlarmInfo
import javax.inject.Inject

class AlarmDataBaseRepository @Inject constructor(
    private val alarmDao: AlarmDao,
    private val alarmDboMapper: AlarmDboMapper
) {
    fun saveNewAlarm(
        hours: Int,
        minutes: Int,
        isEnabled: Boolean,
        daysOfWeek: List<AlarmDayOfWeek>
    ): Single<AlarmInfo> {
        return alarmDao.saveAlarm(alarmDboMapper.map(hours, minutes, isEnabled, daysOfWeek))
            .subscribeOn(Schedulers.io())
            .flatMap { alarmDao.getAlarmById(it) }
            .map { alarmDboMapper.map(it) }
            .toSingle()
    }

    fun getAlarmById(id: Long): Single<AlarmInfo> {
        return alarmDao.getAlarmById(id)
            .subscribeOn(Schedulers.io())
            .map { alarmDboMapper.map(it) }
            .toSingle()
    }

    fun updateAlarm(alarmInfo: AlarmInfo): Single<AlarmInfo> {
        return alarmDao.saveAlarm(alarmDboMapper.map(alarmInfo))
            .subscribeOn(Schedulers.io())
            .flatMap { alarmDao.getAlarmById(it) }
            .map { alarmDboMapper.map(it) }
            .toSingle()
    }

    fun getAllAlarms(): Single<List<AlarmInfo>> {
        return alarmDao.getAllAlarms()
            .subscribeOn(Schedulers.io())
            .map {alarmList ->
                alarmList.map {
                    alarmDboMapper.map(it)
                }
            }.toSingle(emptyList())
    }

    fun deleteAlarm(id: Long): Completable {
        return alarmDao.deleteAlarm(id)
            .subscribeOn(Schedulers.io())
    }

}