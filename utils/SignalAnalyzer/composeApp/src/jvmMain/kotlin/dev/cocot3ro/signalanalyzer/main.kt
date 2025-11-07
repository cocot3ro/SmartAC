package dev.cocot3ro.signalanalyzer

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.cocot3ro.signalanalyzer.di.initKoin

fun main() = application {

    initKoin()

    Window(
        onCloseRequest = ::exitApplication,
        title = "SignalAnalyzer",
    ) {
        App()
    }
}