package vn.id.tool.nlu.presentation.time_table.components

import vn.id.tool.nlu.domain.Timetable

data class TimetableState(
    val isLoading: Boolean = false,
    val timetable: Timetable? = null,
    val error: String? = null
)
