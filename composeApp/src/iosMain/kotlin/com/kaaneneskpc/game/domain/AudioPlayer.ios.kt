package com.kaaneneskpc.game.domain

import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFAudio.AVAudioPlayer
import platform.AVFAudio.AVAudioSession
import platform.AVFAudio.AVAudioSessionCategoryPlayback
import platform.AVFAudio.setActive
import platform.Foundation.NSBundle
import platform.Foundation.NSURL
import platform.Foundation.NSURL.Companion.fileURLWithPath

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
@OptIn(ExperimentalForeignApi::class)
actual class AudioPlayer {
    private var audioPlayers: MutableMap<String, AVAudioPlayer?> =
        mutableMapOf()
    private var fallingSoundPlayer: AVAudioPlayer? = null

    init {
        // Configure the audio session for playback.
        val session = AVAudioSession.sharedInstance()
        session.setCategory(AVAudioSessionCategoryPlayback, error = null)
        session.setActive(true, error = null)
    }

    actual fun playGameOverSound() {
        stopFallingSound() // Stop any ongoing falling sound before playing the game over sound.
        playSound("game_over")
    }

    actual fun playJumpSound() {
        stopFallingSound() // Stop any ongoing falling sound before playing the jump sound.
        playSound("jump")
    }

    actual fun playFallingSound() {
        // Start playing the falling sound and keep a reference for stopping later.
        fallingSoundPlayer = playSound("falling")
    }

    actual fun stopFallingSound() {
        // Stop the falling sound if it's playing.
        fallingSoundPlayer?.stop()
        fallingSoundPlayer = null
    }

    actual fun playGameSoundInLoop() {
        // Get the sound URL and create an AVAudioPlayer instance that loops indefinitely.
        val url = getSoundURL("game_sound")
        val player = url?.let { AVAudioPlayer(it, null) }
        player?.numberOfLoops = -1 // Loop indefinitely
        player?.prepareToPlay()
        player?.play()
        audioPlayers["game_sound"] = player
    }

    actual fun stopGameSound() {
        playGameOverSound()
        // Stop the looping game sound and remove it from the audio players map.
        audioPlayers["game_sound"]?.stop()
        audioPlayers["game_sound"] = null
    }

    actual fun release() {
        // Stop all audio players and clear references to free resources.
        audioPlayers.values.forEach { it?.stop() }
        audioPlayers.clear()
        fallingSoundPlayer?.stop()
        fallingSoundPlayer = null
    }

    private fun playSound(soundName: String): AVAudioPlayer? {
        val url = getSoundURL(soundName)
        val player = url?.let { AVAudioPlayer(it, error = null) }
        player?.prepareToPlay()
        player?.play()
        audioPlayers[soundName] = player // Store the player for future management.
        return player
    }

    private fun getSoundURL(resourceName: String): NSURL? {
        val bundle = NSBundle.mainBundle()
        val path = bundle.pathForResource(resourceName, "wav")
        return path?.let { fileURLWithPath(it) }
    }
}