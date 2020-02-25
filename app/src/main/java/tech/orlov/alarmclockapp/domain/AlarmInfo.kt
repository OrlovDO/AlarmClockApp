package tech.orlov.alarmclockapp.domain

data class AlarmInfo(
    val id: Long,
    val hours: Int,
    val minutes: Int,
    val isEnabled: Boolean,
    val daysOfWeek: List<AlarmDayOfWeek>
)