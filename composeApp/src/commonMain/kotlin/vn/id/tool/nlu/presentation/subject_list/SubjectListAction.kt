package vn.id.tool.nlu.presentation.subject_list

import vn.id.tool.nlu.domain.Subject

sealed interface SubjectListAction {
    data class OnSearchQueryChange(val query: String) : SubjectListAction
    data class OnSubjectClick(val subject: Subject) : SubjectListAction
    data class OnTabSelected(val index: Int) : SubjectListAction
}