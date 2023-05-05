package org.greenfroyo.compoker

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.greenfroyo.compoker.model.THREE
import org.greenfroyo.compoker.ui.theme.CompokerTheme
import org.greenfroyo.compoker.ui.theme.DarkBlue
import org.greenfroyo.compoker.ui.theme.TextWhite

@Preview
@Composable
fun HandTableDialog(onClose: () -> Unit = {}) {
    CompokerTheme {
        Dialog(
            onDismissRequest = { /*TODO*/ }) {
            Surface(
                modifier = Modifier
                    .heightIn(128.dp, Dp.Unspecified)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                color = DarkBlue
            ) {
                Column(
                    modifier = Modifier.padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val hands = arrayOf(
                        ROYAL_STRAIGHT_FLUSH,
                        STRAIGHT_FLUSH,
                        FOUR_OF_A_KIND,
                        FULL_HOUSE,
                        STRAIGHT,
                        FLUSH,
                        THREE_OF_A_KIND,
                        TWO_PAIR,
                        ONE_PAIR,
                        JACKS_OR_BETTER
                    )

                    for (hand in hands) { // <- What a romantic line
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = stringResource(id = hand.getDisplayText()),
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.SemiBold,
                            color = TextWhite,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Row(modifier = Modifier.bottomBorder(1.dp, TextWhite)) {
                            Spacer(modifier = Modifier.weight(2f))
                            Text(
                                modifier = Modifier.weight(1f),
                                text = (BET_LOW * hand.getWinningMultiplier()).toString(),
                                textAlign = TextAlign.Right,
                                style = MaterialTheme.typography.body1,
                                color = TextWhite,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                modifier = Modifier.weight(1f),
                                text = (BET_MEDIUM * hand.getWinningMultiplier()).toString(),
                                textAlign = TextAlign.Right,
                                style = MaterialTheme.typography.body1,
                                color = TextWhite,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                modifier = Modifier.weight(1f),
                                text = (BET_HIGH * hand.getWinningMultiplier()).toString(),
                                textAlign = TextAlign.Right,
                                style = MaterialTheme.typography.body1,
                                color = TextWhite,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Button(onClick = { onClose() }) {
                        Text(
                            text = "Close", style = MaterialTheme.typography.body2
                        )
                    }
                }
            }
        }
    }
}

fun Modifier.bottomBorder(strokeWidth: Dp, color: Color) = composed(
    factory = {
        val density = LocalDensity.current
        val strokeWidthPx = density.run { strokeWidth.toPx() }

        Modifier.drawBehind {
            val width = size.width
            val height = size.height - strokeWidthPx/2

            drawLine(
                color = color,
                start = Offset(x = 0f, y = height),
                end = Offset(x = width , y = height),
                strokeWidth = strokeWidthPx
            )
        }
    }
)



