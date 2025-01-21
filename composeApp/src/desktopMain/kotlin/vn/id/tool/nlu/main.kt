package vn.id.tool.nlu

import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import vn.id.tool.nlu.app.App
import vn.id.tool.nlu.core.database.DBFactory

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "NLUTool",
    ) {
        val database = DBFactory().createDatabase()
        App(database)
    }
}