package org.greenfroyo.compoker.model

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.greenfroyo.compoker.GameUseCase
import org.greenfroyo.compoker.getPokerHand

class GameViewModel(val useCase: GameUseCase = GameUseCase()) : ViewModel() {
    private val _state = MutableStateFlow(GameState())
    val state = _state.asStateFlow()

    fun switchBet() {
        _state.update {
            if (it.phase == BETTING) {
                it.copy(bet = useCase.switchBet(it.bet))
            } else {
                it
            }
        }
    }

    fun draw() {
        _state.update {
            val (draws, afterDeck) = Deck().draw(5)
            it.copy(
                phase = DRAWN,
                credit = it.credit-it.bet,
                cards = draws.toTypedArray(),
                deck = afterDeck,
                winningHand = null
            )
        }
    }

    fun toggleDropCardAt(position: Int) {
        _state.update {
            if (it.phase == DRAWN) {
                val afterSelection = it.dropSelection.toggle(position)
                it.copy(dropSelection = afterSelection)
            } else {
                it
            }
        }
    }

    fun drop() {
        _state.update {
            var deck = Deck()
            val afterCards = (0..4).map { i ->
                if (it.dropSelection.isSelected(i)) {
                    var pair = deck.drawOne()
                    deck = pair.second
                    return@map pair.first
                } else {
                    it.cards[i]
                }
            }.toTypedArray()
            val winningHand = afterCards.getPokerHand()
            val winningCredit =
                if (winningHand == null) 0 else winningHand.getWinningMultiplier() * it.bet
            it.copy(
                phase = BETTING,
                credit = it.credit+winningCredit,
                cards = afterCards,
                deck = deck,
                dropSelection = Selection(),
                winningHand = afterCards.getPokerHand(),
                winningCredit = winningCredit
            )
        }
    }
}