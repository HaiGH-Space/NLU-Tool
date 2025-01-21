package vn.id.tool.nlu.presentation.subject_list

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import vn.id.tool.nlu.core.domain.IAPI
import vn.id.tool.nlu.data.repository.DefaultRepository

class SubjectListViewModel(
    private val apiRepository: IAPI
): ViewModel() {
    private val _state  = MutableStateFlow(SubjectListState())
    val state = _state.asStateFlow()
    fun onAction(action: SubjectListAction) {
        when(action) {
            is SubjectListAction.OnSearchQueryChange -> {
               _state.update {
                   it.copy(searchQuery = action.query)
               }
            }
            is SubjectListAction.OnSubjectClick -> {

            }
            is SubjectListAction.OnTabSelected -> {
                _state.update {
                    it.copy(selectedTabIndex = action.index)
                }
            }
        }
    }
}