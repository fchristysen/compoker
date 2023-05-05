package org.greenfroyo.compoker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.greenfroyo.compoker.ui.theme.CompokerTheme
import org.greenfroyo.compoker.ui.theme.DarkBlue
import org.greenfroyo.compoker.ui.theme.TextWhite

@Preview
@Composable
fun GameOverDialog(onRestart: () -> Unit = {}) {
    CompokerTheme {
        Dialog(
            onDismissRequest = { /*TODO*/ }) {
            Surface(
                modifier = Modifier
                    .heightIn(128.dp, Dp.Unspecified)
                    .widthIn(256.dp, Dp.Unspecified),
                shape = RoundedCornerShape(16.dp),
                color = DarkBlue
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center ) {
                    Text(
                        text = "Game Over",
                        style = MaterialTheme.typography.body2,
                        color = TextWhite
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(onClick = {onRestart()}) {
                        Text(
                            text = "Restart", style = MaterialTheme.typography.body2
                        )
                    }
                }
            }
        }
    }
}