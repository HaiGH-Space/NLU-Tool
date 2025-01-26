package vn.id.tool.nlu.presentation.account.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.LockReset
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.stringResource
import vn.id.tool.nlu.presentation.account.AccountAction
import vn.id.tool.nlu.res.Res
import vn.id.tool.nlu.res.change_password
import vn.id.tool.nlu.res.logout
import vn.id.tool.nlu.res.personal_information

@Composable
fun PersonalInformation(
    onAction: (AccountAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(modifier = Modifier.padding(8.dp),text = stringResource(Res.string.personal_information), fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.padding(8.dp))
        ItemPersonalInformation(
            leadingIcon = {
                Icon(
                    Icons.Filled.LockReset,
                    contentDescription = stringResource(Res.string.change_password)
                )
            },
            text = stringResource(Res.string.change_password),
            onClick = {

            }
        )
        HorizontalDivider(
            thickness = 2.dp,
        )
        ItemPersonalInformation(
            leadingIcon = {
                Icon(
                    Icons.AutoMirrored.Filled.Logout,
                    contentDescription = stringResource(Res.string.logout)
                )
            },
            text = stringResource(Res.string.logout),
            onClick = {
                onAction(AccountAction.OnLogOut)
            }
        )
    }
}

@Composable
private fun ItemPersonalInformation(
    leadingIcon: @Composable () -> Unit,
    text: String,
    onClick: () -> Unit
) {
    Column(
        Modifier.fillMaxWidth().clickable {
            onClick()
        }
    ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                leadingIcon()
                Spacer(modifier = Modifier.padding(horizontal = 8.dp, vertical = 24.dp))
                Text(text = text, fontSize = 15.sp)
            }
    }


}