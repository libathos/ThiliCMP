package compose.thili.demo

import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    val state = rememberWindowState(
        position = WindowPosition(Alignment.Center), size = DpSize(500.dp, 720.dp),
    )
    Window(
        onCloseRequest = ::exitApplication,
        title = "ThiliCMP",
        state = state,
        resizable = false
    ) {
        App()
    }
}