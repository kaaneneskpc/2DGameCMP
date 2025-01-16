package com.kaaneneskpc.game


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kaaneneskpc.game.domain.Game
import com.kaaneneskpc.game.domain.GameStatus
import com.kaaneneskpc.game.util.ChewyFontFamily
import gamingcmp.composeapp.generated.resources.Res
import gamingcmp.composeapp.generated.resources.background
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        var screenWidth by remember { mutableStateOf(0) }
        var screenHeight by remember { mutableStateOf(0) }
        var game by remember { mutableStateOf(Game()) }

        LaunchedEffect(Unit) {
            game.start()
        }

        LaunchedEffect(game.status) {
            if (game.status == GameStatus.Started) {
                while (true) {
                    withFrameMillis { _ ->
                        game.updateGameProgress()
                    }
                }
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
                drawCircle(
                    color = Color.Blue,
                    radius = game.bee.radius,
                    center = Offset(game.bee.x, game.bee.y)
                )
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
                }
            }
        }
    }
}