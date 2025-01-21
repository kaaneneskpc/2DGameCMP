package com.kaaneneskpc.game

import android.app.Application
import com.kaaneneskpc.game.di.initializeKoin

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initializeKoin()
    }
}