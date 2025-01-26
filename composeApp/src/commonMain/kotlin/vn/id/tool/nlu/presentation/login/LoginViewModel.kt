package vn.id.tool.nlu.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import vn.id.tool.nlu.core.database.dao.AccountDao
import vn.id.tool.nlu.core.database.model.Account
import vn.id.tool.nlu.core.domain.IAPI
import vn.id.tool.nlu.core.network.Error
import vn.id.tool.nlu.core.network.NetworkErrorExt
import vn.id.tool.nlu.core.network.onError
import vn.id.tool.nlu.core.network.onSuccess
import vn.id.tool.nlu.core.presentation.UiText
import vn.id.tool.nlu.data.nlu.dto.ErrorDto
import vn.id.tool.nlu.domain.Student
import vn.id.tool.nlu.res.Res
import vn.id.tool.nlu.res.error_unknown

class LoginViewModel(
    private val api: IAPI,
    private val accountDao: AccountDao
) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            accountDao.getAll().map { account ->
                _state.update {
                    it.copy(
                        username = account.username,
                        password = account.password,
                        isRememberMe = true
                    )
                }
            }
        }
    }

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.OnLoginClick -> {
                login(_state.value.username, state.value.password)
            }

            is LoginAction.OnPasswordShow -> {
                _state.update {
                    it.copy(showPassword = !action.showPassword)
                }
            }

            is LoginAction.OnPasswordChange -> {
                _state.update {
                    it.copy(password = action.password)
                }
            }

            is LoginAction.OnUsernameChange -> {
                _state.update {
                    it.copy(username = action.username)
                }
            }

            is LoginAction.OnRememberMeChange -> {
                _state.update {
                    it.copy(isRememberMe = action.isRememberMe)
                }
            }
            is LoginAction.OnIsLoginSuccessChange -> {
                _state.update {
                    it.copy(
                        isLoginSuccess = action.isSuccess,
                    )
                }
            }

            else -> Unit
        }
    }

    private fun login(username: String, password: String) = viewModelScope.launch {
        _state.update {
            it.copy(
                isLoading = true,
            )
        }
        api.login(username, password)
            .onSuccess { student ->
                if (state.value.isRememberMe) {
                    Student.student = student
                    accountDao.insert(Account(username, password,student.accessToken))
                }
                _state.update {
                    it.copy(
                        isLoginSuccess = true,
                        isLoading = false,
                        student = student,
                        errorMessage = null
                    )
                }
            }.onError { error ->
                _state.update {
                    it.copy(
                        isLoginSuccess = false,
                        isLoading = false,
                        student = null,
                        errorMessage = responseToUiText(error)
                    )
                }
            }
    }

    private fun responseToUiText(error: Error): UiText {
        if (error is NetworkErrorExt) {
            error.message.let {
                try {
                    val json = Json {
                        ignoreUnknownKeys = true
                    }
                    val errorText = json.decodeFromString<ErrorDto>(it).message
                    return UiText.DynamicString(errorText)
                } catch (e: Exception) {
                    e.printStackTrace()
                    return UiText.StringResourceId(Res.string.error_unknown)
                }

            }
        }
        return UiText.StringResourceId(Res.string.error_unknown)
    }
}