package com.kaaneneskpc.game.domain

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.w3c.dom.Audio

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class AudioPlayer: KoinComponent {
    private val settings: Settings by inject()
    private val audioElements = mutableMapOf<String, Audio>()

    actual var isSoundEnabled: Boolean
        get() = settings.isSoundEnabled
        set(value) {
            settings.isSoundEnabled = value
            if (!value) {
                stopAllSounds()
            }
        }

    actual fun playGameOverSound() {
        if (!isSoundEnabled) return
        stopFallingSound()
        playSound(fileName = "game_over.wav")
    }

    actual fun playJumpSound() {
        if (!isSoundEnabled) return
        stopFallingSound()
        playSound(fileName = "jump.wav")
    }

    actual fun playFallingSound() {
        if (!isSoundEnabled) return
        playSound(fileName = "falling.wav")
    }

    actual fun stopFallingSound() {
        stopSound(fileName = "falling.wav")
    }

    actual fun playGameSoundInLoop() {
        if (!isSoundEnabled) return
        playSound(fileName = "game_sound.wav", loop = true)
    }

    actual fun stopGameSound() {
        playGameOverSound()
        stopSound(fileName = "game_sound.wav")
    }

    actual fun stopGameSoundWithOutGameOver() {
        stopSound(fileName = "game_sound.wav")
    }

    actual fun release() {
        stopAllSounds()
        audioElements.clear()
    }

    private fun playSound(
        fileName: String,
        loop: Boolean = false
    ) {
        val audio = audioElements[fileName] ?: createAudioElement(fileName).also {
            audioElements[fileName] = it
        }
        audio.loop = loop
        audio.play().catch {
            println("Error playing sound: $fileName")
            it
        }
    }

    private fun stopSound(fileName: String) {
        audioElements[fileName]?.let { audio ->
            audio.pause()
            audio.currentTime = 0.0
        }
    }

    private fun stopAllSounds() {
        audioElements.values.forEach { audio ->
            audio.pause()
            audio.currentTime = 0.0
        }
    }

    private fun createAudioElement(fileName: String): Audio {
        val path = try {
            "composeResources/gamingcmp.composeapp.generated.resources/files/$fileName".takeIf { it.isNotBlank() }
                ?: throw IllegalArgumentException("Invalid audio file path")
        } catch (e: Exception) {
            println("Error creating audio path: ${e.message}")
            fileName // Fallback to just the filename if path creation fails
        }
        
        return Audio(path).apply {
            onerror = { _, _, _, _, _ ->
                println("Error loading audio file: $path")
                null
            }
            onloadeddata = {
                println("Audio file loaded successfully: $path")
                null
            }
        }
    }
}