package org.greenfroyo.compoker.model

import org.greenfroyo.compoker.PokerHand

data class GameState(
    val phase: Phase = BETTING,
    val credit: Int = 200,
    val bet: Int = 5,
    val deck: Deck = Deck(),
    val cards: Array<Card> = Array(5) { Card() },
    val dropSelection: Selection = Selection(),
    val winningHand: PokerHand? = null,
    val winningCredit: Int = 0
) {
    fun enableDraw() = phase == BETTING

    fun enableDrop() = phase == DRAWN
}

sealed class Phase

object BETTING: Phase()

object DRAWN: Phase()

//object DROP: Phase()