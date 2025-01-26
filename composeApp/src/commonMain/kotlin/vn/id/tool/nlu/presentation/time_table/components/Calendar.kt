package vn.id.tool.nlu.presentation.time_table.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.OutDateStyle
import com.kizitonwose.calendar.core.YearMonth
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.minusMonths
import com.kizitonwose.calendar.core.now
import com.kizitonwose.calendar.core.plusMonths
import kotlinx.coroutines.launch
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import vn.id.tool.nlu.Device
import vn.id.tool.nlu.presentation.time_table.TimetableAction
import vn.id.tool.nlu.presentation.time_table.TimetableState

val example5ToolbarColor = Color(0xFF282828)
val example5TextGreyLight = Color(0xFF616161)

private val toolbarColor: Color = example5ToolbarColor
private val inActiveTextColor: Color = example5TextGreyLight
private val squareDay = 60.dp

private var highLightHeight = 2.dp
private var spacerHeight = 0.dp
val calendarMaxWidth = (squareDay * 7 + 8.dp)

@Composable
fun Calendar(
    device: Device,
    modifier: Modifier = Modifier,
    timetableState: TimetableState,
    selectedDate: (TimetableAction) -> Unit
) {
    spacerHeight = if (device.isMobile()) 3.dp else 4.dp

    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(500) }
    val endMonth = remember { currentMonth.plusMonths(500) }
    var selection by remember { mutableStateOf<CalendarDay?>(null) }
    val daysOfWeek = remember { daysOfWeek() }
    val backgroundToday: Color = MaterialTheme.colorScheme.scrim.copy(alpha = 0.9f)
    val backgroundSelectedDay: Color = MaterialTheme.colorScheme.scrim
    val backgroundNormalDay: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f)
    Column(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val state = rememberCalendarState(
            startMonth = startMonth,
            endMonth = endMonth,
            firstVisibleMonth = currentMonth,
            firstDayOfWeek = daysOfWeek.first(),
            outDateStyle = OutDateStyle.EndOfGrid,
        )
        val currentDate = remember { LocalDate.now() }
        val coroutineScope = rememberCoroutineScope()
        val visibleMonth = rememberFirstCompletelyVisibleMonth(state)
        LaunchedEffect(visibleMonth) {
            selection = null
        }

        CompositionLocalProvider(LocalContentColor provides Color.White) {
            CalendarTitle(
                modifier = Modifier
                    .background(toolbarColor)
                    .padding(horizontal = 8.dp, vertical = 10.dp),
                currentMonth = visibleMonth.yearMonth,
                goToPrevious = {
                    coroutineScope.launch {
                        state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.previous)
                    }
                },
                goToNext = {
                    coroutineScope.launch {
                        state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.next)
                    }
                },
            )
            HorizontalCalendar(
                modifier = Modifier.widthIn(max = calendarMaxWidth).wrapContentHeight()
                    .padding(bottom = 8.dp),
                state = state,
                dayContent = { day ->
                    val isToday = day.date == currentDate
                    val colors = if (day.position == DayPosition.MonthDate) {
                        val classScheduleAtDay = timetableState.listClassSchedule.filter {
                            (day.date in it.startDate..it.endDate) && (it.dayOfWeek == day.date.dayOfWeek)
                        }
                        val existingPeriods = classScheduleAtDay.map { it.period }.toSet()
                        (1..5).map { i ->
                            if (i in existingPeriods) {
                                when (i) {
                                    1 -> Color.Red
                                    2 -> Color.Green
                                    3 -> Color.Blue
                                    4 -> Color.White
                                    else -> Color.Yellow
                                }
                            } else {
                                Color.Transparent
                            }
                        }
                    } else {
                        emptyList()
                    }
                    Day(
                        isToday = isToday,
                        backgroundToday = backgroundToday,
                        backgroundNormalDay = backgroundNormalDay,
                        backgroundSelectedDay = backgroundSelectedDay,
                        day = day,
                        isSelected = (selection == day),
                        colors = colors,
                    ) { clicked ->
                        selection = clicked
                        selectedDate(TimetableAction.OnSelectedDateChange(clicked.date))
                    }
                },
                monthHeader = {
                    MonthHeader(
                        modifier = Modifier.padding(vertical = 7.dp),
                        daysOfWeek = daysOfWeek,
                    )
                },
            )

        }

    }
}

@Composable
private fun Day(
    day: CalendarDay,
    backgroundToday: Color,
    backgroundSelectedDay: Color,
    backgroundNormalDay: Color,
    isToday: Boolean = false,
    isSelected: Boolean = false,
    colors: List<Color> = emptyList(),
    onClick: (CalendarDay) -> Unit = {},
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(1.dp)
            .background(
                color = if (isSelected) backgroundSelectedDay else if (isToday) backgroundToday else backgroundNormalDay,
            )
            .clickable(
                enabled = day.position == DayPosition.MonthDate,
                onClick = { onClick(day) },
            ),
    ) {
        val textColor = when (day.position) {
            DayPosition.MonthDate -> Color.Unspecified
            DayPosition.InDate, DayPosition.OutDate -> inActiveTextColor
        }
        Text(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding( end = 4.dp),
            text = day.date.dayOfMonth.toString(),
            color = textColor,
            fontSize = 12.sp,
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 6.dp),
            verticalArrangement = Arrangement.spacedBy(spacerHeight),
        ) {
            for (color in colors) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(highLightHeight)
                        .background(color),
                )
            }
        }
    }
}

@Composable
private fun MonthHeader(
    modifier: Modifier = Modifier,
    daysOfWeek: List<DayOfWeek> = emptyList(),
) {
    Row(modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.scrim,
                text = dayOfWeek.displayText(uppercase = true),
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}
