package com.kaaneneskpc.game

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.kaaneneskpc.game.di.initializeKoin

fun main() = application {
    initializeKoin()
    Window(
        onCloseRequest = ::exitApplication,
        title = "GamingCMP",
    ) {
        App()
    }
}