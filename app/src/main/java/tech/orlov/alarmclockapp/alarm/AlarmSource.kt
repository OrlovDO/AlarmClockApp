package tech.orlov.alarmclockapp.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.*
import javax.inject.Inject


class AlarmSource @Inject constructor(val context: Context) {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun setRepeatableAlarm(alarmInfo: AlarmInfoDto): List<Long> {
        cancelAlarm(alarmInfo.id)
        val dateList = mapToDateListInMills(alarmInfo)
        dateList.forEach {
            setRepeatableAlarmOnTime(it.first, buildRequestId(alarmInfo.id, it.second))
        }
        return dateList.map { it.first }
    }

    fun cancelAlarm(alarmId: Long) {
        val days = AlarmDayOfWeekDto.values()
        days.forEach {
            cancelAlarmByRequestId(buildRequestId(alarmId, it))
        }
    }

    fun snoozeAlarm(){
        alarmManager.set(
            AlarmManager.RTC,
            Calendar.getInstance().timeInMillis + 5000,
            buildIntent(0)
        )
    }

    private fun setRepeatableAlarmOnTime(timeInMills: Long, requestCode: Int) {
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            timeInMills,
            AlarmManager.INTERVAL_DAY * 7,
            buildIntent(requestCode)
        )
    }

    private fun cancelAlarmByRequestId(requestCode: Int) {
        alarmManager.cancel(buildIntent(requestCode))
    }

    private fun mapToDateListInMills(alarmInfo: AlarmInfoDto): List<Pair<Long, AlarmDayOfWeekDto>> {
        val dateList: MutableList<Pair<Long, AlarmDayOfWeekDto>> = mutableListOf()
        alarmInfo.daysOfWeek.forEach { day ->
            val currentDateCalendar = Calendar.getInstance()
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, alarmInfo.hours)
            calendar.set(Calendar.MINUTE, alarmInfo.minutes)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            while (calendar.get(Calendar.DAY_OF_WEEK) != day.value
                || calendar.before(currentDateCalendar)
            ) {
                calendar.add(Calendar.DATE, 1)
            }
            dateList.add(calendar.timeInMillis to day)
        }
        dateList.sortBy { it.first }
        return dateList
    }

    private fun buildIntent(requestCode: Int): PendingIntent {
        val receiverIntent = Intent(context, AlarmReceiver::class.java)
        return PendingIntent.getBroadcast(context, requestCode, receiverIntent, 0)
    }

    private fun buildRequestId(alarmId: Long, day: AlarmDayOfWeekDto): Int{
        return (alarmId * 10 + day.value).toInt()
    }

}