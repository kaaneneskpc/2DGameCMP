package com.kaaneneskpc.game.domain

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.set

class Settings(private val settings: ObservableSettings) {
    companion object {
        private const val SOUND_ENABLED_KEY = "sound_enabled"
    }

    var isSoundEnabled: Boolean
        get() = settings.getBoolean(SOUND_ENABLED_KEY, true)
        set(value) {
            settings[SOUND_ENABLED_KEY] = value
        }
} 