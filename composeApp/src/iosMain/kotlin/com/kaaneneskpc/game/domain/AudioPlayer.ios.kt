package com.kaaneneskpc.game.domain

import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import platform.AVFAudio.AVAudioEngine
import platform.AVFAudio.AVAudioPlayer
import platform.AVFAudio.AVAudioSession
import platform.AVFAudio.AVAudioSessionCategoryPlayback
import platform.AVFAudio.setActive
import platform.Foundation.NSBundle
import platform.Foundation.NSURL

@OptIn(ExperimentalForeignApi::class)
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class AudioPlayer : KoinComponent {
    private val settings: Settings by inject()
    private val audioPlayers = mutableMapOf<String, AVAudioPlayer>()
    private var loopingPlayer: AVAudioPlayer? = null

    actual var isSoundEnabled: Boolean
        get() = settings.isSoundEnabled
        set(value) {
            settings.isSoundEnabled = value
            if (!value) {
                stopAllSounds()
            }
        }

    init {
        // Configure the audio session for playback.
        val session = AVAudioSession.sharedInstance()
        session.setCategory(AVAudioSessionCategoryPlayback, error = null)
        session.setActive(true, error = null)
        setupAudioPlayers()
    }

    private fun setupAudioPlayers() {
        soundResList.forEach { fileName ->
            val resourceName = fileName.substringAfterLast("/").substringBeforeLast(".")
            val resourceType = fileName.substringAfterLast(".")
            
            val url = NSBundle.mainBundle.URLForResource(
                resourceName,
                resourceType
            )
            
            url?.let {
                try {
                    val player = AVAudioPlayer(contentsOfURL = it, error = null)
                    player.prepareToPlay()
                    audioPlayers[fileName] = player
                } catch (e: Exception) {
                    println("Error loading audio file $fileName: $e")
                }
            } ?: println("Could not find audio file: $fileName")
        }
    }

    actual fun playGameOverSound() {
        if (!isSoundEnabled) return
        stopFallingSound()
        audioPlayers["files/game_over.wav"]?.play()
    }

    actual fun playJumpSound() {
        if (!isSoundEnabled) return
        stopFallingSound()
        audioPlayers["files/jump.wav"]?.play()
    }

    actual fun playFallingSound() {
        if (!isSoundEnabled) return
        audioPlayers["files/falling.wav"]?.play()
    }

    actual fun stopFallingSound() {
        audioPlayers["files/falling.wav"]?.stop()
        audioPlayers["files/falling.wav"]?.currentTime = 0.0
    }

    actual fun playGameSoundInLoop() {
        if (!isSoundEnabled) return
        loopingPlayer = audioPlayers["files/game_sound.wav"]
        loopingPlayer?.numberOfLoops = -1
        loopingPlayer?.play()
    }

    actual fun stopGameSound() {
        loopingPlayer?.stop()
        loopingPlayer?.currentTime = 0.0
        playGameOverSound()
    }

    actual fun release() {
        stopAllSounds()
        audioPlayers.clear()
    }

    private fun stopAllSounds() {
        audioPlayers.values.forEach { player ->
            player.stop()
            player.currentTime = 0.0
        }
        loopingPlayer?.stop()
        loopingPlayer?.currentTime = 0.0
    }
}