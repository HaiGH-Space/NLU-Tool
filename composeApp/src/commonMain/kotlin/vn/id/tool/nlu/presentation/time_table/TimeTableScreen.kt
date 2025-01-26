package vn.id.tool.nlu.presentation.time_table

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import vn.id.tool.nlu.Device
import vn.id.tool.nlu.domain.ClassSchedule
import vn.id.tool.nlu.domain.isSameDayOfWeek
import vn.id.tool.nlu.presentation.time_table.components.Calendar
import vn.id.tool.nlu.presentation.time_table.components.calendarMaxWidth
import vn.id.tool.nlu.res.Res
import vn.id.tool.nlu.res.room
import vn.id.tool.nlu.res.time


@Composable
fun TimetableScreenRoot(
    viewModel: TimetableViewModel = koinViewModel(),
    device: Device,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(state.semester) {
        viewModel.onAction(TimetableAction.OnListClassScheduleChange)
    }
    TimetableScreen(
        modifier = modifier.fillMaxSize(),
        onAction = { viewModel.onAction(it) },
        state = state,
        device = device
    )
}
@Composable
fun TimetableScreen(
    modifier: Modifier = Modifier,
    state: TimetableState,
    device: Device,
    onAction: (TimetableAction) -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        Calendar(
            device = device,
            selectedDate = onAction,
            timetableState = state
        )
        HorizontalDivider(
            color = MaterialTheme.colorScheme.scrim,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        SpinnerSemester(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            state = state,
            onAction = onAction
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(state.listClassSchedule.size) { index ->
                if (state.listClassSchedule[index].isSameDayOfWeek(state.selectedDate)) {
                    ClassSchedule(
                        modifier = Modifier.widthIn(max = calendarMaxWidth).fillMaxWidth()
                            .padding(vertical = 8.dp),
                        classSchedule = state.listClassSchedule[index]
                    )
                }
            }
        }
    }
}

@Composable
fun SpinnerSemester(
    modifier: Modifier = Modifier,
    state: TimetableState,
    onAction: (TimetableAction) -> Unit
) {
    val itemPosition = remember {
        mutableStateOf(0)
    }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                onAction(TimetableAction.OnDropDownExpandedChange(true))
            }
        ) {
            Text(text = state.semester.description)
            Image(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "DropDown Icon"
            )
        }
        Box {
            DropdownMenu(
                expanded = state.isDropDownExpanded,
                onDismissRequest = {
                    onAction(TimetableAction.OnDropDownExpandedChange(false))
                }) {
                state.listSemester.forEachIndexed { index, semester ->
                    DropdownMenuItem(text = {
                        Text(text = semester.description)
                    },
                        onClick = {
                            onAction(TimetableAction.OnDropDownExpandedChange(false))
                            onAction(TimetableAction.OnSemesterChange(semester))
                            itemPosition.value = index
                        }
                    )
                }
            }
        }
    }

}

@Composable
fun ClassSchedule(
    modifier: Modifier = Modifier,
    classSchedule: ClassSchedule
) {
    Card(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = classSchedule.subject.name,
                style = MaterialTheme.typography.titleMedium
            )
            Text(text = "${stringResource(Res.string.room)}: ${classSchedule.room}")
            Text(text = "${stringResource(Res.string.time)}: ${classSchedule.startTime} - ${classSchedule.endTime}")
        }
    }
}