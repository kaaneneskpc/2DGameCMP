package com.kaaneneskpc.game.domain

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.kaaneneskpc.game.Platform
import kotlin.random.Random

const val SCORE_KEY = "score"

data class Game(
    val platform: Platform,
    val screenWidth: Int = 0,
    val screenHeight: Int = 0,
    val gravity: Float = if (platform == Platform.Android) 0.8f else if (platform == Platform.iOS) 0.8f else 0.25f,
    val beeRadius: Float = 30f,
    val beeJumpImpulse: Float = if (platform == Platform.Android) -12f else if (platform == Platform.iOS) -12f else -8f,
    val beeMaxVelocity: Float = if (platform == Platform.Android) 25f else if(platform == Platform.iOS) 20f else 20f,
    val pipeWidth: Float = 150f,
    val pipeVelocity: Float = if (platform == Platform.Android) 5f else if(platform == Platform.iOS) 7f else 2.5f,
    val pipeGapSize: Float = if (platform == Platform.Android) 250f else 300f
) {
    var status by mutableStateOf(GameStatus.Idle)
        private set
    var beeVelocity by mutableStateOf(0f)
        private set
    var bee by mutableStateOf(
        Bee(
            x = (screenWidth / 2).toFloat(),
            y = (screenHeight / 2).toFloat(),
            radius = beeRadius
        )
    )
        private set

    var pipePairs = mutableStateListOf<PipePair>()
    var currentScore by mutableStateOf(0)
        private set
    var bestScore by mutableStateOf(0)
        private set
    private var isFallingSoundPlayed = false

    fun start() {
        status = GameStatus.Started
    }

    fun gameOver() {
        status = GameStatus.Over
    }

    fun jump() {
        beeVelocity = beeJumpImpulse
    }

    fun restart() {
        resetBeePosition()
        removePipes()
        start()
    }

    private fun resetBeePosition() {
        bee = bee.copy(y = (screenHeight / 2).toFloat())
        beeVelocity = 0f
    }

    private fun removePipes() {
        pipePairs.clear()
    }

    fun updateGameProgress() {
        if (bee.y < 0) {
            stopTheBee()
            return
        } else if (bee.y > screenHeight) {
            gameOver()
            return
        }

        beeVelocity = (beeVelocity + gravity).coerceIn(-beeMaxVelocity, beeMaxVelocity)
        bee = bee.copy(y = bee.y + beeVelocity)

        spawnPipes()
    }

    private fun spawnPipes() {
        pipePairs.forEach { it.x -= pipeVelocity }
        pipePairs.removeAll { it.x + pipeWidth < 0 }

        val isLandscape = screenWidth > screenHeight
        val spawnThreshold = if (isLandscape) screenWidth / 1.25
        else screenWidth / 2.0

        if (pipePairs.isEmpty() || pipePairs.last().x < spawnThreshold) {
            val initialPipeX = screenWidth.toFloat() + pipeWidth
            val topHeight = Random.nextFloat() * (screenHeight / 2)
            val bottomHeight = screenHeight - topHeight - pipeGapSize
            val newPipePair = PipePair(
                x = initialPipeX,
                y = topHeight + pipeGapSize / 2,
                topHeight = topHeight,
                bottomHeight = bottomHeight
            )
            pipePairs.add(newPipePair)
        }
    }


    fun stopTheBee() {
        beeVelocity = 0f
        bee = bee.copy(y = 0f)
    }

}