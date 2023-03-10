package org.greenfroyo.compoker.model

import org.greenfroyo.compoker.PokerHand
import org.greenfroyo.compoker.getPokerHand

data class GameState(
    val phase: Phase = BET(),
    val credit: Int = 200,
    val bet: Int = 5,
    val deck: Deck = Deck(),
    val cards: Array<Card> = Array(5) { Card() }
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GameState

        if (phase != other.phase) return false
        if (credit != other.credit) return false
        if (bet != other.bet) return false
        if (deck != other.deck) return false
        if (!cards.contentEquals(other.cards)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = phase.hashCode()
        result = 31 * result + credit
        result = 31 * result + bet
        result = 31 * result + deck.hashCode()
        result = 31 * result + cards.contentHashCode()
        return result
    }

    fun hand() = cards.getPokerHand()
}

sealed class Phase {}

class BET(): Phase(){

}

class DRAW(): Phase(){

}

data class PICK(val discards: Array<Boolean>): Phase(){

}

class RESULT(val hand: PokerHand): Phase(){
}