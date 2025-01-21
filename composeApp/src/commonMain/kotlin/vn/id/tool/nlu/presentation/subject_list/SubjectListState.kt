package vn.id.tool.nlu.presentation.subject_list

import vn.id.tool.nlu.core.presentation.UiText
import vn.id.tool.nlu.domain.Subject

data class SubjectListState(
    val searchQuery: String = "",
    val searchResult: List<Subject> = emptyList(),
    val favoriteSubject: List<Subject> = emptyList(),
    val isLoading: Boolean = false,
    val selectedTabIndex: Int = 0,
    val errorMessage: UiText? = null
)