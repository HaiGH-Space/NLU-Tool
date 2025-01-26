package vn.id.tool.nlu.presentation.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import vn.id.tool.nlu.domain.Student
import vn.id.tool.nlu.presentation.account.components.PersonalInformation
import vn.id.tool.nlu.presentation.login.LoginAction
import vn.id.tool.nlu.res.Res
import vn.id.tool.nlu.res.greeting

const val durationAction = 35
const val durationWait = 4000
@Composable
fun AccountScreenRoot(
    viewModel: AccountViewModel = koinViewModel(),
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
){
    val state by viewModel.state.collectAsStateWithLifecycle()
    AccountScreen(
        state = state,
        onAction = { action ->
            when(action){
                is AccountAction.OnLogOut -> {
                    onLogout()
                }
                else -> Unit
            }
            viewModel.onAction(action)
        } ,
        modifier = modifier
    )
}

@Composable
fun AccountScreen(
    state: AccountState,
    onAction: (AccountAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val student by remember {
        mutableStateOf(Student.student!!)
    }
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            var displayedText by remember { mutableStateOf("") }
            val text = "${stringResource(Res.string.greeting)} ${student.name}"
            LaunchedEffect(text) {
                while (true) {
                    displayedText = ""
                    for (char in text) {
                        displayedText += char
                        delay(durationAction.toLong())
                    }
                    delay(durationWait.toLong())
                    for (i in text.length downTo 1) {
                        displayedText = text.substring(0, i - 1)
                        delay(durationAction.toLong())
                    }
                    delay(1000)
                }
            }
            Image(
                imageVector = student.avatar,
                contentDescription = "avatar",
                modifier = Modifier.padding(end = 16.dp)
                    .sizeIn(minWidth = 40.dp, minHeight = 40.dp),
                contentScale = ContentScale.Crop,
            )
            Column {
                Text(text = displayedText, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(text = "${student.username} - ${student.email}", fontSize = 14.sp)
            }
        }
        Card(
            modifier.fillMaxWidth().padding(16.dp),
            shape = RoundedCornerShape(8),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            PersonalInformation(
                onAction = onAction
            )
        }
    }
}