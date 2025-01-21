package com.kaaneneskpc.game.domain

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.kaaneneskpc.game.Platform
import com.russhwolf.settings.ObservableSettings
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.random.Random

const val SCORE_KEY = "score"

data class Game(
    val platform: Platform,
    val screenWidth: Int = 0,
    val screenHeight: Int = 0,
    val gravity: Float = if (platform == Platform.Android) 0.8f else if (platform == Platform.iOS) 0.8f else 0.25f,
    val beeRadius: Float = 30f,
    val beeJumpImpulse: Float = if (platform == Platform.Android) -12f else if (platform == Platform.iOS) -12f else -8f,
    val beeMaxVelocity: Float = if (platform == Platform.Android) 25f else if (platform == Platform.iOS) 20f else 20f,
    val pipeWidth: Float = 150f,
    val pipeVelocity: Float = if (platform == Platform.Android) 5f else if (platform == Platform.iOS) 7f else 2.5f,
    val pipeGapSize: Float = if (platform == Platform.Android) 250f else 300f
) : KoinComponent {
    private val settings: ObservableSettings by inject()
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

    init {
        bestScore = settings.getInt(
            key = SCORE_KEY,
            defaultValue = 0
        )
        settings.addIntListener(
            key = SCORE_KEY,
            defaultValue = 0
        ) {
            bestScore = it
        }
    }

    fun start() {
        status = GameStatus.Started
    }

    fun gameOver() {
        status = GameStatus.Over
        saveScore()
    }

    fun jump() {
        beeVelocity = beeJumpImpulse
    }

    private fun saveScore() {
        if (currentScore > bestScore) {
            settings.putInt(SCORE_KEY, bestScore)
            bestScore = currentScore
        }
    }

    fun restart() {
        resetBeePosition()
        removePipes()
        restartScore()
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

        pipePairs.forEach { pipePair ->
            if (isCollision(pipePair)) {
                gameOver()
                return
            }
            if (!pipePair.scored && bee.x > pipePair.x + pipeWidth / 2) {
                pipePair.scored = true
                currentScore += 1
            }
        }


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

    private fun restartScore() {
        currentScore = 0
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

    private fun isCollision(pipePair: PipePair): Boolean {

        // Check horizontal collision
        val beeRightHedge = bee.x + bee.radius
        val beeLeftHedge = bee.x - bee.radius
        val pipeLeftHedge = pipePair.x - pipeWidth / 2
        val pipeRightHedge = pipePair.x + pipeWidth / 2

        val horizontalCollision = beeRightHedge > pipeLeftHedge && beeLeftHedge < pipeRightHedge

        // Check if the bee is within the vertical gap
        val beeTopHedge = bee.y - bee.radius
        val beeBottomHedge = bee.y + bee.radius
        val gapTopHedge = pipePair.y - pipeGapSize / 2
        val gapBottomHedge = pipePair.y + pipeGapSize / 2

        val beeInGap = beeTopHedge > gapTopHedge && beeBottomHedge < gapBottomHedge

        return horizontalCollision && !beeInGap
    }


    fun stopTheBee() {
        beeVelocity = 0f
        bee = bee.copy(y = 0f)
    }

}