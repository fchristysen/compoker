package org.greenfroyo.compoker.model

import org.greenfroyo.compoker.model.CardSuit.*

//position points to next non-opened cards
data class Deck(val position: Int = 0, val cards: List<Card> = newDeck().shuffled()) {
    fun draw(nums: Int): Pair<List<Card>, Deck>{
        if(position+nums-1 >= cards.size){
            throw EndOfDeckException()
        }
        return Pair(cards.slice(position until position+nums), copy(position = position+nums))
    }
}

private fun newDeck() = arrayOf(SPADE, HEART, CLUB, DIAMOND)
    .flatMap { suit -> arrayOf(ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING)
        .map { num -> Card(suit, num) } }.toMutableList()

class EndOfDeckException: Exception("No more cards in the deck")