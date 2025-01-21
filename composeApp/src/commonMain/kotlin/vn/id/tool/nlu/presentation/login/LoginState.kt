package vn.id.tool.nlu.presentation.login

import vn.id.tool.nlu.core.presentation.UiText
import vn.id.tool.nlu.domain.Student

data class LoginState(
    val username: String = "",
    val password: String = "",
    val student: Student? = null,
    val isLoginSuccess: Boolean = false,
    val showPassword: Boolean = false,
    val isRememberMe: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: UiText? = null
)