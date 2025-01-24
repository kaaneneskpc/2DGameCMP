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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.kaaneneskpc.game.domain.AudioPlayer
import org.koin.compose.koinInject

@Composable
fun SettingsButton() {
    var showSettings by remember { mutableStateOf(false) }
    val audioPlayer = koinInject<AudioPlayer>()
    var isSoundEnabled by remember { mutableStateOf(audioPlayer.isSoundEnabled) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.wrapContentSize()
    ) {
        IconButton(
            modifier = Modifier
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
                tint = Color.White
            )
        }

        AnimatedVisibility(
            visible = showSettings,
            enter = fadeIn() + slideInVertically { -it },
            exit = fadeOut() + slideOutVertically { -it },
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = 60.dp)
                .zIndex(2f)
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = Color.Black.copy(alpha = 0.9f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Sound", color = Color.White)
                    Switch(
                        checked = isSoundEnabled,
                        onCheckedChange = { enabled ->
                            isSoundEnabled = enabled
                            audioPlayer.isSoundEnabled = enabled
                        }
                    )
                }
            }
        }
    }
} 