package vn.id.tool.nlu.presentation.account

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import vn.id.tool.nlu.core.domain.IAPI
import vn.id.tool.nlu.presentation.time_table.TimetableAction

class AccountViewModel(
    private val api: IAPI,
) : ViewModel() {
    private val _state = MutableStateFlow(AccountState())
    val state = _state.asStateFlow()
    fun onAction(action: AccountAction) {
        when (action) {
            else -> Unit
        }
    }
}