package vn.id.tool.nlu.data.nlu.mappers

import vn.id.tool.nlu.data.nlu.dto.TimetableDto
import vn.id.tool.nlu.domain.Timetable


fun TimetableDto.toTimetable(): Timetable {
    return Timetable(
        schedules = this.data?.classSchedule?.map {
            it.toClassSchedule()
        } ?: emptyList()
    )
}