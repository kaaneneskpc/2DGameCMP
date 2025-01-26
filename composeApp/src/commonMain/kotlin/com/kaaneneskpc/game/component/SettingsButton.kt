package com.kaaneneskpc.game.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.kaaneneskpc.game.domain.AudioPlayer
import com.kaaneneskpc.game.domain.Game
import org.koin.compose.koinInject

@Composable
fun SettingsButton(game: Game) {
    var showSettings by remember { mutableStateOf(false) }
    val audioPlayer = koinInject<AudioPlayer>()
    var isSoundEnabled by remember { mutableStateOf(audioPlayer.isSoundEnabled) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.wrapContentSize()
    ) {
        IconButton(
            modifier = Modifier
                .size(36.dp)
                .background(
                    color = Color.Black.copy(alpha = 0.7f),
                    shape = RoundedCornerShape(8.dp)
                )
                .align(Alignment.Center),
            onClick = { showSettings = !showSettings }
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }

        AnimatedVisibility(
            visible = showSettings,
            enter = fadeIn() + slideInVertically { -it },
            exit = fadeOut() + slideOutVertically { -it },
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = 45.dp)
                .zIndex(2f)
        ) {
            Box(
                modifier = Modifier
                    .width(120.dp)
                    .background(
                        color = Color.Black.copy(alpha = 0.9f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            "Sound",
                            color = Color.White,
                            fontSize = MaterialTheme.typography.bodySmall.fontSize
                        )
                        Switch(
                            checked = isSoundEnabled,
                            onCheckedChange = { enabled ->
                                isSoundEnabled = enabled
                                audioPlayer.isSoundEnabled = enabled
                            },
                            modifier = Modifier.scale(0.7f)
                        )
                    }
                    
                    Button(
                        onClick = { 
                            game.clearData()
                            showSettings = false
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red.copy(alpha = 0.7f)
                        ),
                        contentPadding = PaddingValues(4.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear",
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                "Clear Score",
                                color = Color.White,
                                fontSize = MaterialTheme.typography.bodySmall.fontSize
                            )
                        }
                    }
                }
            }
        }
    }
} 