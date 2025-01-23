package com.kaaneneskpc.game.di

import com.kaaneneskpc.game.domain.AudioPlayer
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val targetModule = module {
    single<AudioPlayer> { AudioPlayer(context = androidContext()) }
}