package vn.id.tool.nlu.presentation.time_table

import kotlinx.datetime.LocalDate
import vn.id.tool.nlu.domain.Semester

sealed interface TimetableAction {
    data class OnSelectedDateChange(val date: LocalDate) : TimetableAction
    data class OnSemesterChange(val semester: Semester) : TimetableAction
    data class OnDropDownExpandedChange(val isDropDownExpanded: Boolean) : TimetableAction
    data object OnListClassScheduleChange : TimetableAction

}