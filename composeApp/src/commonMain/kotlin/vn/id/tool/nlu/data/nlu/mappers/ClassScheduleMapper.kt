package vn.id.tool.nlu.data.nlu.mappers

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import vn.id.tool.nlu.data.nlu.dto.ClassScheduleDto
import vn.id.tool.nlu.domain.ClassSchedule
import vn.id.tool.nlu.domain.ClassType
import vn.id.tool.nlu.domain.Subject
import vn.id.tool.nlu.domain.WeekRange

fun ClassScheduleDto.toClassSchedule(): ClassSchedule {
    val (startDate, endDate) = this.tkb.split(" đến ").map {
        parse(it)
    }
    val (startWeek, endWeek) = this.weeks.getFirstAndLastDigit()
    return ClassSchedule(
        subject = Subject(
            name = this.name,
            id = this.id,
        ),
        dayOfWeek = DayOfWeek.of(this.dayOfWeek - 1),
        startTime =  LocalTime.parse(this.startTime),
        endTime =  LocalTime.parse(this.endTime),
        room = this.room,
        teacher = this.teacher,
        classType = if (this.numOfLab == 0) ClassType.THEORY else ClassType.UNDEFINED,
        startDate = startDate,
        endDate = endDate,
        weekRange = WeekRange.WEEK_RANGE(startWeek, endWeek),
        period = this.period.convertPeriodByRule()
    )
}

private fun Int.convertPeriodByRule(): Int {// rule 1,2,3,4 not 1,4,7,10
    return (this - 1) / 3 + 1
}

private fun parse(dateRange: String): LocalDate {
    val (day, month, year) = dateRange.split("/").map { it.toInt() }
    return LocalDate(dayOfMonth = day, monthNumber = month, year = year + 2000)
}

private fun String.getFirstAndLastDigit(): Pair<Int, Int> {
    val digits = this.filter { it.isDigit() }
    return if (digits.isEmpty()) {
        Pair(0, 0)
    } else {
        Pair(digits.first().digitToInt(), digits.last().digitToInt())
    }
}