package com.kaaneneskpc.game.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kaaneneskpc.game.util.ChewyFontFamily

@Composable
fun ScoreBoard() {
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
}