package vn.id.tool.nlu.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import vn.id.tool.nlu.core.presentation.components.LoadingIndicator
import vn.id.tool.nlu.core.presentation.components.TextField
import vn.id.tool.nlu.core.presentation.components.TrailingIconPassword
import vn.id.tool.nlu.res.Res
import vn.id.tool.nlu.res.login
import vn.id.tool.nlu.res.password
import vn.id.tool.nlu.res.remember_me
import vn.id.tool.nlu.res.username
import vn.id.tool.nlu.resource.defaultItemSpacing
import vn.id.tool.nlu.resource.defaultPadding

@Composable
fun LoginScreenRoot(
    viewModel: LoginViewModel = koinViewModel(),
    onLoginSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(state.isLoginSuccess) {
        if (state.isLoginSuccess) {
            onLoginSuccess()
        }
    }
    LoginScreen(
        state = state,
        onAction = { action ->
            viewModel.onAction(action)
        },
        modifier = modifier
    )
}

@Composable
fun LoginScreen(
    state: LoginState,
    onAction: (LoginAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    )
    {
        Column(
            modifier = modifier.fillMaxSize().padding(defaultPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = state.username,
                onValueChange = { onAction(LoginAction.OnUsernameChange(it)) },
                labelText = stringResource(Res.string.username),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { onAction(LoginAction.OnPasswordChange(state.password)) }
                ),
                singleLine = true,
                leadingIcon = Icons.Filled.Person,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(defaultPadding))
            TextField(
                value = state.password,
                onValueChange = { onAction(LoginAction.OnPasswordChange(it)) },
                labelText = stringResource(Res.string.password),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        onAction(LoginAction.OnLoginClick(state.username, state.password))
                    }
                ),
                trailingIcon = {
                    TrailingIconPassword(state.showPassword, value = state.password,
                        { onAction(LoginAction.OnPasswordShow(state.showPassword)) })
                },
                singleLine = true,
                visualTransformation = if (state.showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                leadingIcon = Icons.Filled.Lock,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(defaultItemSpacing))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = state.isRememberMe,
                    onCheckedChange = { onAction(LoginAction.OnRememberMeChange(it)) })
                Text(stringResource(Res.string.remember_me))
            }
            Spacer(modifier = Modifier.padding(defaultItemSpacing))
            Text(
                text = state.errorMessage?.asString() ?: "",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.padding(defaultItemSpacing))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onAction(LoginAction.OnLoginClick(state.username, state.password))
                }
            ) {
                Text(stringResource(Res.string.login))
            }

        }
        if (state.isLoading) {
            LoadingIndicator()
        }
    }

}