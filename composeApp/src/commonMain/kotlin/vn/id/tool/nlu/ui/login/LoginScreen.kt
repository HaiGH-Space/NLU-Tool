package vn.id.tool.nlu.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import kotlinx.coroutines.launch
import vn.id.tool.nlu.api.IAPI
import vn.id.tool.nlu.network.onError
import vn.id.tool.nlu.network.onSuccess
import vn.id.tool.nlu.resource.defaultItemSpacing
import vn.id.tool.nlu.resource.defaultPadding
import vn.id.tool.nlu.resource.defaultRoundedPercent
import vn.id.tool.nlu.ui.component.TextFieldCompose

@Composable
fun LoginScreen(modifier: Modifier = Modifier, onLoginClick: () -> Unit, client: IAPI) {
    var mssv by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var checked by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    Column(
        modifier = modifier.padding(defaultPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextFieldCompose(
            value = mssv,
            onValueChange = { mssv = it },
            labelText = "MSSV",
            leadingIcon = Icons.Default.Person,
            modifier = Modifier.fillMaxWidth(),
            keyboardType = KeyboardType.Number,
            roundedPercent = defaultRoundedPercent
        )
        Spacer(modifier = Modifier.padding(defaultItemSpacing))
        TextFieldCompose(
            value = pass,
            onValueChange = { pass = it },
            labelText = "Mật khẩu",
            leadingIcon = Icons.Default.Lock,
            modifier = Modifier.fillMaxWidth(),
            keyboardType = KeyboardType.Password,
            visualTransformation = PasswordVisualTransformation(),
            roundedPercent = defaultRoundedPercent
        )
        Spacer(modifier = Modifier.padding(defaultItemSpacing))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = checked, onCheckedChange = { checked = it })
            Text("Ghi nhớ đăng nhập")
        }
        Spacer(modifier = Modifier.padding(defaultItemSpacing))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                scope.launch {
                    client.login(mssv, pass)
                        .onSuccess {
                            mssv = it.name.toString()
                        }.onError {
                            mssv = it.name
                        }
                }
            }
        ) {
            Text("Đăng nhập")
        }
    }
}