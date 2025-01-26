package vn.id.tool.nlu

import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import vn.id.tool.nlu.app.App
import vn.id.tool.nlu.core.database.DBFactory
import java.awt.Toolkit

fun main() = application {
    val state = WindowState(
        size = DpSize(800.dp,  (Toolkit.getDefaultToolkit().screenSize.height-50).dp),
        position = WindowPosition.Aligned(Alignment.Center),
    )
    Window(
        onCloseRequest = ::exitApplication,
        title = "NLUTool",
        state = state
    ) {
        val database = DBFactory().createDatabase()
        App(database,DevicePlatform())
    }
}