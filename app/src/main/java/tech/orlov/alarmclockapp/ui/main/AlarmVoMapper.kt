package tech.orlov.alarmclockapp.ui.main

import tech.orlov.alarmclockapp.domain.AlarmDayOfWeek
import tech.orlov.alarmclockapp.domain.AlarmInfo
import javax.inject.Inject

class AlarmVoMapper @Inject constructor() {

    fun map(alarmInfo: AlarmInfo): AlarmVo {
        return AlarmVo(
            alarmInfo.id,
            alarmInfo.hours,
            alarmInfo.minutes,
            format(alarmInfo.hours, alarmInfo.minutes),
            alarmInfo.isEnabled,
            alarmInfo.daysOfWeek.map(::map)
        )
    }

    fun map(alarmDayOfWeekVo: AlarmDayOfWeekVo): AlarmDayOfWeek {
        return AlarmDayOfWeek.valueOf(alarmDayOfWeekVo.name)
    }

    private fun map(alarmDayOfWeek: AlarmDayOfWeek): AlarmDayOfWeekVo {
        return AlarmDayOfWeekVo.valueOf(alarmDayOfWeek.name)
    }

    private fun format(hours: Int, minutes: Int): String{
        return "$hours:$minutes"
    }

}