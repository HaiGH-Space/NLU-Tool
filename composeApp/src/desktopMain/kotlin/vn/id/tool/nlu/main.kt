package vn.id.tool.nlu

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "NLUTool",
    ) {
        App()
    }
}