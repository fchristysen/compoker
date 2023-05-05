package org.greenfroyo.compoker

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.greenfroyo.compoker.model.Card
import org.greenfroyo.compoker.model.CardSuit
import org.greenfroyo.compoker.model.JACK
import org.greenfroyo.compoker.model.Selection

@Preview
@Composable
fun CardView(
    modifier: Modifier = Modifier,
    card: Card = Card(CardSuit.HIDDEN, JACK),
    onClickAction: () -> Unit = {}
) {
    var lastCard by remember { mutableStateOf(card) }
    val rotationAnimation by animateFloatAsState(
        targetValue = if (lastCard != card) 360f else 0f,
        animationSpec = keyframes {
            durationMillis = 500
            360f at 499 with FastOutSlowInEasing
        },
        finishedListener = {
            lastCard = card
        }
    )
    Image(
        painter = painterResource(id = card.getImageResources(LocalContext.current)),
        contentDescription = "cards",
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
            .width(64.dp)
            .height(96.dp)
            .clickable { onClickAction() }
            .graphicsLayer {
                rotationY = rotationAnimation
                cameraDistance = 8 * density
            }
            .then(modifier)
    )
}

@Preview
@Composable
fun BoardView(
    modifier: Modifier = Modifier,
    cards: Array<Card> = Array(5) { Card() },
    selection: Selection = Selection(),
    onSelect: (Int) -> Unit = { _ -> }
) {
    if (cards.size != 5) throw IllegalArgumentException("cards length should be 5")
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        for (i in 0..4) {
            Column() {
                CardView(
                    card = cards[i],
                    onClickAction = { onSelect(i) })
                val dropAnimation: Int by animateIntAsState(
                    targetValue = if (selection.isSelected(i)) 32 else 0,
                    animationSpec = tween(durationMillis = 100, easing = FastOutSlowInEasing)
                )
                Spacer(modifier = Modifier.height(dropAnimation.dp))
            }
        }
    }
}