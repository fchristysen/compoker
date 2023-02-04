package org.greenfroyo.compoker

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.greenfroyo.compoker.model.Card
import org.greenfroyo.compoker.model.CardSuit
import org.greenfroyo.compoker.model.JACK

@Preview
@Composable
fun CardView(modifier: Modifier = Modifier, card: Card = Card(CardSuit.SPADE, JACK)) {
    Box() {
        Image(painter = painterResource(id = card.getImageResources(LocalContext.current))
            , contentDescription = "cards"
            , contentScale = ContentScale.FillBounds
            , modifier = Modifier
                .height(64.dp)
                .width(64.dp))
    }
}

@Preview
@Composable
fun BoardView(modifier: Modifier = Modifier, cards: Array<Card> = Array(5) {Card()}) {
    if(cards.size != 5) throw IllegalArgumentException("cards length should be 5")
    Row(horizontalArrangement = Arrangement.SpaceBetween) {
        CardView(card = cards[0])
        CardView(card = cards[1])
        CardView(card = cards[2])
        CardView(card = cards[3])
        CardView(card = cards[4])
    }
}