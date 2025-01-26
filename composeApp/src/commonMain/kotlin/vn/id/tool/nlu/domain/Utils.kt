package vn.id.tool.nlu.domain

import kotlinx.datetime.LocalDate

fun Timetable.getSchedulesByWeek(weekNumber: Int): List<ClassSchedule> {
    return schedules.filter { schedule ->
        when (schedule.weekRange) {
            is WeekRange.ALL_WEEKS -> true
            is WeekRange.SPECIFIC_WEEKS -> weekNumber in schedule.weekRange.weeks
            is WeekRange.WEEK_RANGE -> weekNumber in schedule.weekRange.startWeek..schedule.weekRange.endWeek
        }
    }
}
fun List<ClassSchedule>.sortByDayOfWeekAndTime(): List<ClassSchedule> {
    return this.sortedWith(compareBy<ClassSchedule> { it.dayOfWeek }.thenBy { it.startTime })
}
fun ClassSchedule.isSameDayOfWeek(date: LocalDate): Boolean {
    return this.dayOfWeek == date.dayOfWeek && date in this.startDate..this.endDate
}