package vn.id.tool.nlu.presentation.login

import vn.id.tool.nlu.domain.Student
import vn.id.tool.nlu.presentation.subject_list.SubjectListAction

sealed interface LoginAction {
    data class OnLoginClick(val username: String, val password: String) : LoginAction
    data class OnRememberMeChange(val isRememberMe: Boolean) : LoginAction
    data class OnUsernameChange(val username: String) : LoginAction
    data class OnPasswordChange(val password: String) : LoginAction
    data class OnPasswordShow(val showPassword: Boolean) : LoginAction
    data class OnLoginSuccessChange(val student: Student) : LoginAction
    data class OnIsLoginSuccessChange(val isSuccess: Boolean) : LoginAction

}
