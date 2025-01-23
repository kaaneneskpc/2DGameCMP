package com.kaaneneskpc.game.domain

import android.content.Context
import android.media.SoundPool
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.kaaneneskpc.game.R
import gamingcmp.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class AudioPlayer(context: Context) {

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
        stopFallingSound()
        soundPool.play(gameOverSound, 1f, 1f, 0, 0, 1f)
    }

    actual fun playJumpSound() {
        stopFallingSound()
        soundPool.play(jumpSound, 1f, 1f, 0, 0, 1f)
    }

    actual fun playFallingSound() {
        fallingSoundId = soundPool.play(fallingSound, 1f, 1f, 0, 0, 1f)
    }

    actual fun stopFallingSound() {
        soundPool.stop(fallingSoundId)
    }

    actual fun playGameSoundInLoop() {
        loopingPlayer.apply {
            repeatMode = Player.REPEAT_MODE_ONE
            mediaItems.getOrNull(2)?.let { setMediaItem(it) }
            play()
        }
    }

    actual fun stopGameSound() {
        loopingPlayer.stop()
        playGameOverSound()
    }

    actual fun release() {
        loopingPlayer.apply {
            stop()
            clearMediaItems()
            release()
        }
        soundPool.release()
    }
}