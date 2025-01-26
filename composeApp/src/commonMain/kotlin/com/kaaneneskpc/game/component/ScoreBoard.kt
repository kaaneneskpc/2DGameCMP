package com.kaaneneskpc.game.component

import androidx.compose.animation.core.Spring
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kaaneneskpc.game.domain.Game
import com.kaaneneskpc.game.util.ChewyFontFamily
import kotlinx.coroutines.delay
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.ui.draw.scale

@Composable
fun ScoreBoard(game: Game) {
    var showLevelUpAnimation by remember { mutableStateOf(false) }
    var previousLevel by remember { mutableStateOf(1) }
    
    val scale by animateFloatAsState(
        targetValue = if (showLevelUpAnimation) 1.5f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    
    LaunchedEffect(game.currentLevel) {
        if (game.currentLevel > previousLevel) {
            showLevelUpAnimation = true
            delay(1000)
            showLevelUpAnimation = false
        }
        previousLevel = game.currentLevel
    }

    Column(
        modifier = Modifier.fillMaxWidth().padding(top = 48.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 48.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "BEST: ${game.bestScore}",
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.displaySmall.fontSize,
                fontFamily = ChewyFontFamily()
            )
            
            Text(
                text = "${game.currentScore}",
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.displaySmall.fontSize,
                fontFamily = ChewyFontFamily()
            )
        }
        
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "LEVEL ${game.currentLevel}",
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontFamily = ChewyFontFamily(),
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .scale(scale)
            )
        }
    }
}