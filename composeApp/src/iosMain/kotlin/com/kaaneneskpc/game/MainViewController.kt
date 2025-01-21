package com.kaaneneskpc.game

import androidx.compose.ui.window.ComposeUIViewController
import com.kaaneneskpc.game.di.initializeKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initializeKoin() }
) { App() }