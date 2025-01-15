package com.kaaneneskpc.game


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kaaneneskpc.game.util.ChewyFontFamily
import gamingcmp.composeapp.generated.resources.Res
import gamingcmp.composeapp.generated.resources.background
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(Res.drawable.background),
                contentDescription = "Background",
                contentScale = ContentScale.Crop
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
    }
}