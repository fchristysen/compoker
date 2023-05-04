package org.greenfroyo.compoker.model

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.greenfroyo.compoker.BET_HIGH
import org.greenfroyo.compoker.BET_LOW
import org.greenfroyo.compoker.BET_MEDIUM
import org.greenfroyo.compoker.getPokerHand

class GameViewModel: ViewModel() {
    private val _gameState = MutableStateFlow(GameUiState())
    val gameState = _gameState.asStateFlow()

    /** User's action to switch betting value
     */
    fun switchBet() {
        _gameState.update {
            if (it.phase == BETTING) {
                it.copy(bet = rotateBetValue(it.bet))
            } else {
                it
            }
        }
    }

    /** User's action to draw 5 cards, beginning the round
     */
    fun draw() {
        _gameState.update {
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

    /** User's action to toggle card droppings
     */
    fun toggleDropCardAt(position: Int) {
        _gameState.update {
            if (it.phase == DRAWN) {
                val afterSelection = it.dropSelection.toggle(position)
                it.copy(dropSelection = afterSelection)
            } else {
                it
            }
        }
    }

    /** User's action to drop selected cards then continue to poker hand results
     */
    fun drop() {
        _gameState.update {
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

    private fun rotateBetValue(currentBet: Int): Int{
        return when(currentBet){
            BET_LOW -> BET_MEDIUM
            BET_MEDIUM -> BET_HIGH
            else -> BET_LOW
        }
    }
}