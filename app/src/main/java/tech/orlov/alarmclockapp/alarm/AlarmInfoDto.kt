package tech.orlov.alarmclockapp.alarm

data class AlarmInfoDto(
    val id: Long,
    val hours: Int,
    val minutes: Int,
    val daysOfWeek: List<AlarmDayOfWeekDto>
) {

}