package com.kaaneneskpc.game


import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaaneneskpc.game.domain.Game
import com.kaaneneskpc.game.domain.GameStatus
import com.kaaneneskpc.game.util.ChewyFontFamily
import com.stevdza_san.sprite.component.drawSpriteView
import com.stevdza_san.sprite.domain.SpriteSheet
import com.stevdza_san.sprite.domain.SpriteSpec
import com.stevdza_san.sprite.domain.rememberSpriteState
import gamingcmp.composeapp.generated.resources.Res
import gamingcmp.composeapp.generated.resources.background
import gamingcmp.composeapp.generated.resources.bee_sprite
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

const val BEE_FRAME_SIZE = 80
const val TOTAL_FRAMES = 9
const val FRAMES_PER_ROW = 3

@Composable
@Preview
fun App() {
    MaterialTheme {
        var screenWidth by remember { mutableStateOf(0) }
        var screenHeight by remember { mutableStateOf(0) }
        var game by remember { mutableStateOf(Game()) }
        val spriteState = rememberSpriteState(
            totalFrames = TOTAL_FRAMES,
            framesPerRow = FRAMES_PER_ROW,

            )

        val spriteSpec = remember {
            SpriteSpec(
                screenWidth = screenWidth.toFloat(),
                default = SpriteSheet(
                    frameWidth = BEE_FRAME_SIZE,
                    frameHeight = BEE_FRAME_SIZE,
                    image = Res.drawable.bee_sprite
                )
            )
        }

        val currentFrame by spriteState.currentFrame.collectAsStateWithLifecycle()
        val sheetImage = spriteSpec.imageBitmap
        val animatedAngle by animateFloatAsState(
            targetValue = when {
                game.beeVelocity > game.beeMaxVelocity / 1.1 -> 30f
                else -> 0f
            }
        )

        DisposableEffect(Unit) {
            onDispose {
                spriteState.apply {
                    stop()
                    cleanup()
                }
            }
        }

        LaunchedEffect(game.status) {
            when (game.status) {
                GameStatus.Started -> {
                    while (true) {
                        withFrameMillis { _ ->
                            game.updateGameProgress()
                        }
                    }
                }

                GameStatus.Over -> {
                    spriteState.stop()
                }

                GameStatus.Idle -> {}
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(Res.drawable.background),
                contentDescription = "Background",
                contentScale = ContentScale.Crop
            )

            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .onGloballyPositioned {
                        val size = it.size
                        if (screenWidth != size.width || screenHeight != size.height) {
                            screenWidth = size.width
                            screenHeight = size.height
                            game = Game(screenWidth = screenWidth, screenHeight = screenHeight)
                        }
                    }
                    .clickable {
                        if (game.status == GameStatus.Started) {
                            game.jump()
                        }
                    }
            ) {
                rotate(
                    degrees = animatedAngle,
                    pivot = Offset(
                        x = game.bee.x - game.beeRadius,
                        y = game.bee.y - game.beeRadius
                    )
                ) {
                    drawSpriteView(
                        spriteState = spriteState,
                        spriteSpec = spriteSpec,
                        currentFrame = currentFrame,
                        image = sheetImage,
                        offset = IntOffset(
                            x = (game.bee.x - game.beeRadius).toInt(),
                            y = (game.bee.y - game.beeRadius).toInt()
                        )
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(all = 48.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "BEST: 0",
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.displaySmall.fontSize,
                    fontFamily = ChewyFontFamily()
                )
                Text(
                    text = "0",
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.displaySmall.fontSize,
                    fontFamily = ChewyFontFamily()
                )
            }

            if (game.status == GameStatus.Idle) {
                Box(
                    modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        modifier = Modifier.height(54.dp),
                        shape = RoundedCornerShape(size = 20.dp),
                        colors = ButtonDefaults.buttonColors(contentColor = Color.White),
                        onClick = {
                            game.start()
                            spriteState.start()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = null,
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "START",
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                            fontFamily = ChewyFontFamily()
                        )
                    }
                }
            }


            if (game.status == GameStatus.Over) {
                Column(
                    modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.5f)),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "GAME OVER",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.displayMedium.fontSize,
                        fontFamily = ChewyFontFamily()
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        modifier = Modifier.height(54.dp),
                        shape = RoundedCornerShape(size = 20.dp),
                        colors = ButtonDefaults.buttonColors(contentColor = Color.White),
                        onClick = {
                            game.restart()
                            spriteState.start()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = null,
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "START",
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                            fontFamily = ChewyFontFamily()
                        )
                    }
                }
            }
        }
    }
}