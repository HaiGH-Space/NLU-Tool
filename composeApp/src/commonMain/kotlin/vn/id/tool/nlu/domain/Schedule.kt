package vn.id.tool.nlu.domain

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

data class ClassSchedule(
    val subject: Subject,
    val dayOfWeek: DayOfWeek,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val room: String = "",
    val teacher: String = "",
    val startDate: LocalDate,
    val endDate: LocalDate,
    val period: Int,
    val classType: ClassType = ClassType.UNDEFINED,
    val weekRange: WeekRange = WeekRange.ALL_WEEKS
)
sealed class WeekRange {
    object ALL_WEEKS : WeekRange()
    data class SPECIFIC_WEEKS(
        val weeks: List<Int>
    ) : WeekRange()
    data class WEEK_RANGE(
        val startWeek: Int,
        val endWeek: Int
    ) : WeekRange()
}
enum class ClassType {
    THEORY,
    PRACTICE,
    UNDEFINED
}

