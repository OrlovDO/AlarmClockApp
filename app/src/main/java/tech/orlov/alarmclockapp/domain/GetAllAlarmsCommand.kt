package tech.orlov.alarmclockapp.domain

import io.reactivex.Single
import tech.orlov.alarmclockapp.data.AlarmDataBaseRepository
import javax.inject.Inject

class GetAllAlarmsCommand @Inject constructor(
    private val alarmDataBaseRepository: AlarmDataBaseRepository
) {
    fun getAllAlarms(): Single<List<AlarmInfo>>{
        return alarmDataBaseRepository.getAllAlarms()
    }
}