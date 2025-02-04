package com.kaaneneskpc.game.domain

import android.content.Context
import android.media.SoundPool
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.kaaneneskpc.game.R
import gamingcmp.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@OptIn(ExperimentalResourceApi::class)
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class AudioPlayer(context: Context) : KoinComponent {
    private val settings: Settings by inject()

    actual var isSoundEnabled: Boolean
        get() = settings.isSoundEnabled
        set(value) {
            settings.isSoundEnabled = value
            if (!value) {
                stopAllSounds()
            }
        }

    private val loopingPlayer = ExoPlayer.Builder(context).build()
    private val mediaItems = soundResList.map {
        MediaItem.fromUri(Res.getUri(it))
    }

    private val soundPool = SoundPool.Builder().setMaxStreams(3).build()

    private val jumpSound = soundPool.load(context, R.raw.jump, 2)
    private val fallingSound = soundPool.load(context, R.raw.falling, 1)
    private var fallingSoundId: Int = 0
    private val gameOverSound = soundPool.load(context, R.raw.game_over, 1)

    init {
        loopingPlayer.prepare()
    }

    actual fun playGameOverSound() {
        if (!isSoundEnabled) return
        stopFallingSound()
        soundPool.play(gameOverSound, 1f, 1f, 1, 0, 1f)
    }

    actual fun playJumpSound() {
        if (!isSoundEnabled) return
        stopFallingSound()
        soundPool.play(jumpSound, 1f, 1f, 1, 0, 1f)
    }

    actual fun playFallingSound() {
        if (!isSoundEnabled) return
        fallingSoundId = soundPool.play(fallingSound, 1f, 1f, 1, 0, 1f)
    }

    actual fun stopFallingSound() {
        soundPool.stop(fallingSoundId)
    }

    actual fun playGameSoundInLoop() {
        if (!isSoundEnabled) return
        loopingPlayer.apply {
            stop()
            clearMediaItems()
            repeatMode = Player.REPEAT_MODE_ONE
            mediaItems.getOrNull(2)?.let { 
                setMediaItem(it)
                prepare()
                play()
            }
        }
    }

    actual fun stopGameSound() {
        loopingPlayer.stop()
        loopingPlayer.clearMediaItems()
        playGameOverSound()
    }

    actual fun stopGameSoundWithOutGameOver() {
        loopingPlayer.stop()
        loopingPlayer.clearMediaItems()
    }

    actual fun release() {
        loopingPlayer.apply {
            stop()
            clearMediaItems()
            release()
        }
        soundPool.release()
    }

    private fun stopAllSounds() {
        stopFallingSound()
        stopGameSound()
        loopingPlayer.stop()
    }
}