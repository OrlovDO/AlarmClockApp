package tech.orlov.alarmclockapp.ui.main

import java.util.*
import javax.inject.Inject

class NextAlarmMessageFormatter @Inject constructor() {

    private val startString = "Будильник сработает через"

    fun format(alarmTime: Long): String {
        val currentDate = Calendar.getInstance()
        val beforeAlarmCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"))
        beforeAlarmCalendar.timeInMillis = alarmTime - currentDate.timeInMillis
        val beforeAlarmYears = beforeAlarmCalendar.get(Calendar.YEAR) - 1970
        val beforeAlarmDays = beforeAlarmCalendar.get(Calendar.DAY_OF_YEAR) - 1
        val beforeAlarmHours = beforeAlarmCalendar.get(Calendar.HOUR)
        val beforeAlarmMinutes = beforeAlarmCalendar.get(Calendar.MINUTE) + 1
        return formatMessageStrung(
            beforeAlarmYears,
            beforeAlarmDays,
            beforeAlarmHours,
            beforeAlarmMinutes
        )
    }

    private fun formatMessageStrung(years: Int, days: Int, hours: Int, minutes: Int): String {
        val resultStringBuilder = StringBuilder(startString)
        if (years > 0) {
            resultStringBuilder.append(" $years л.")
        }
        if (days > 0) {
            resultStringBuilder.append(" $days д.")
        }
        if (hours > 0) {
            resultStringBuilder.append(" $hours ч.")
        }
        if (minutes > 0) {
            resultStringBuilder.append(" $minutes м.")
        }
        return resultStringBuilder.toString()
    }

}