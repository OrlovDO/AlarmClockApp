package tech.orlov.alarmclockapp.ui.main

data class AlarmVo(
    val id: Long,
    val hours: Int,
    val minutes: Int,
    val formattedTime: String,
    val isEnabled: Boolean,
    val daysOfWeek: List<AlarmDayOfWeekVo>
)