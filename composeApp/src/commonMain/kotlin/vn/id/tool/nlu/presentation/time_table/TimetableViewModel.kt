package vn.id.tool.nlu.presentation.time_table

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import vn.id.tool.nlu.core.domain.IAPI
import vn.id.tool.nlu.core.network.onError
import vn.id.tool.nlu.core.network.onSuccess
import vn.id.tool.nlu.core.presentation.toText
import vn.id.tool.nlu.domain.sortByDayOfWeekAndTime

class TimetableViewModel(
    private val api: IAPI,
) : ViewModel() {
    private val _state = MutableStateFlow(TimetableState())
    val state = _state.asStateFlow()

    init {
        fetchSemester()
    }
    fun onAction(action: TimetableAction) {
        when (action) {
            is TimetableAction.OnSelectedDateChange -> {
                _state.update {
                    it.copy(selectedDate = action.date)
                }
            }

            is TimetableAction.OnSemesterChange -> {
                _state.update {
                    it.copy(semester = action.semester)
                }
            }

            is TimetableAction.OnDropDownExpandedChange -> {
                _state.update {
                    it.copy(isDropDownExpanded = action.isDropDownExpanded)
                }
            }

            is TimetableAction.OnListClassScheduleChange -> {
                fetchClassSchedule()
            }
        }
    }

    private fun fetchSemester() {
        viewModelScope.launch {
            api
                .getListSemester()
                .onSuccess { semesters ->
                    _state.update {
                        it.copy(listSemester = semesters)
                    }
                }
                .onError {
                    println(it.toText())
                }
        }
    }

    private fun fetchClassSchedule() {
        viewModelScope.launch {
            api
                .timetable(semester = _state.value.semester)
                .onSuccess { timeTable ->
                    _state.update {
//                        println(timeTable.schedules)
                        it.copy(listClassSchedule = timeTable.schedules.sortByDayOfWeekAndTime())
                    }
                }
                .onError {
                    println(it.toText())
                }
        }
    }
}