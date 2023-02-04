package org.greenfroyo.compoker.model

import org.greenfroyo.compoker.model.Phase.*

data class GameState(
    var phase: Phase = BET,
    var credit: Int = 200,
    var bet: Int = 5,
    var deck: Deck = Deck(),
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

}

enum class Phase {
    BET,
    DRAWING,
    RESULT
}
