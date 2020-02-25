package tech.orlov.alarmclockapp.data

import tech.orlov.alarmclockapp.domain.AlarmDayOfWeek
import tech.orlov.alarmclockapp.domain.AlarmInfo
import javax.inject.Inject

class AlarmDboMapper @Inject constructor() {
    fun map(
        hours: Int,
        minutes: Int,
        isEnabled: Boolean,
        daysOfWeek: List<AlarmDayOfWeek>
    ): AlarmDbo {
        return AlarmDbo(
            null,
            hours,
            minutes,
            isEnabled,
            map(daysOfWeek)
        )
    }

    fun map(alarmInfo: AlarmInfo): AlarmDbo {
        return AlarmDbo(
            alarmInfo.id,
            alarmInfo.hours,
            alarmInfo.minutes,
            alarmInfo.isEnabled,
            map(alarmInfo.daysOfWeek)
        )
    }

    fun map(alarmDbo: AlarmDbo): AlarmInfo {
        return AlarmInfo(
            requireNotNull(alarmDbo.id),
            alarmDbo.hours,
            alarmDbo.minutes,
            alarmDbo.isEnabled,
            map(alarmDbo.daysOfWeek)
        )
    }

    private fun map(daysOfWeek: List<AlarmDayOfWeek>): String {
        return daysOfWeek.map { it.name }
            .joinToString(separator = ",")
    }

    private fun map(daysOfWeek: String): List<AlarmDayOfWeek> {
        if (daysOfWeek.isEmpty())
            return emptyList()
        return daysOfWeek.split(",")
            .map { AlarmDayOfWeek.valueOf(it) }
            .toList()
    }
}