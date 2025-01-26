package vn.id.tool.nlu.presentation.time_table.components

import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import com.kizitonwose.calendar.core.YearMonth
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.Month
import java.time.format.TextStyle
import java.time.DayOfWeek as JavaDayOfWeek
import java.util.Locale as JavaLocale
import java.time.Month as JavaMonth
val enLocale = Locale.current



fun YearMonth.displayText(short: Boolean = false): String {
    return "${month.displayText(short = short)} $year"
}

fun Month.displayText(short: Boolean = true): String {
    return getDisplayName(short, enLocale)
}

fun DayOfWeek.displayText(uppercase: Boolean = false, narrow: Boolean = true): String {
    return getDisplayName(narrow, enLocale).let { value ->
        if (uppercase) value.toUpperCase(enLocale) else value
    }
}

fun Month.getDisplayName(short: Boolean, locale: Locale): String {
    val style = if (short) TextStyle.SHORT else TextStyle.FULL
    val javaLocale = JavaLocale.Builder()
        .setLanguage(locale.language)
        .setRegion(locale.region)
        .build()
    return JavaMonth.valueOf(name).getDisplayName(style, javaLocale)
}

fun DayOfWeek.getDisplayName(narrow: Boolean, locale: Locale): String {
    val style = if (narrow) TextStyle.SHORT else TextStyle.FULL
    val javaLocale = JavaLocale.Builder()
        .setLanguage(locale.language)
        .setRegion(locale.region)
        .build()
    return JavaDayOfWeek.valueOf(name).getDisplayName(style, javaLocale)
}

