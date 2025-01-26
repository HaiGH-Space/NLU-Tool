package vn.id.tool.nlu.presentation.account

import vn.id.tool.nlu.domain.Student

data class AccountState(
    val student: Student? = Student.student,
)
