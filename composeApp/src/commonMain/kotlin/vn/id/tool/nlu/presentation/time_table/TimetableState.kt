package vn.id.tool.nlu.presentation.time_table

import com.kizitonwose.calendar.core.now
import kotlinx.datetime.LocalDate
import vn.id.tool.nlu.domain.ClassSchedule
import vn.id.tool.nlu.domain.Semester


data class TimetableState(
    val listSemester: List<Semester> = emptyList(),
    val isDropDownExpanded: Boolean = false,
    val semester: Semester = Semester(),
    val nowDate: LocalDate = LocalDate.now(),
    val selectedDate: LocalDate = LocalDate.now(),
    val listClassSchedule : List<ClassSchedule> = emptyList()
)