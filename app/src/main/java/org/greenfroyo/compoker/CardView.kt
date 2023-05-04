package org.greenfroyo.compoker

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    isSelected: Boolean = false,
    onClickAction: () -> Unit = {}
) {
    Box {
        Image(
            painter = painterResource(id = card.getImageResources(LocalContext.current)),
            contentDescription = "cards",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .padding(bottom = if (isSelected) 12.dp else 0.dp)
                .width(64.dp)
                .height(96.dp)
                .clickable { onClickAction() }
        )
    }
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
    Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
        for (i in 0..4) {
            CardView(
                card = cards[i],
                isSelected = selection.isSelected(i),
                onClickAction = { onSelect(i) })
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}