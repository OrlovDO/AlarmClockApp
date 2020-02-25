package tech.orlov.alarmclockapp.alarm

import tech.orlov.alarmclockapp.domain.AlarmDayOfWeek
import tech.orlov.alarmclockapp.domain.AlarmInfo
import javax.inject.Inject

class AlarmDtoMapper @Inject constructor() {

    fun map(alarmInfo: AlarmInfo): AlarmInfoDto{
        return AlarmInfoDto(
            alarmInfo.id,
            alarmInfo.hours,
            alarmInfo.minutes,
            alarmInfo.daysOfWeek.map(::map)
        )
    }

    private fun map(alarmDayOfWeek: AlarmDayOfWeek): AlarmDayOfWeekDto{
        return AlarmDayOfWeekDto.valueOf(alarmDayOfWeek.name)
    }
}