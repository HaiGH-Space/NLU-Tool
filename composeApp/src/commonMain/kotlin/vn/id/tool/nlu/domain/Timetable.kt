package vn.id.tool.nlu.domain

data class Timetable(
    val semester: Semester? = null,
    val schedules: List<ClassSchedule> = emptyList()
)