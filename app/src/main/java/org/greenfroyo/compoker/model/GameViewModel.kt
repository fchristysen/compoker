package org.greenfroyo.compoker.model

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.greenfroyo.compoker.GameUseCase

class GameViewModel(val useCase: GameUseCase = GameUseCase()): ViewModel(){
    private val _state = MutableStateFlow(GameState())
    val state = _state.asStateFlow()

    fun switchBet(){
        _state.update {
            it.copy(bet = useCase.switchBet(it.bet))
        }
    }

    fun draw(){
        _state.update {
            val (draws, newDeck) = it.deck.draw(5)
            it.copy(cards = draws.toTypedArray(), deck = newDeck)
        }
    }
}