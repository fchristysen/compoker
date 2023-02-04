package org.greenfroyo.compoker

class GameUseCase {
    fun switchBet(currentBet: Int): Int{
        return when(currentBet){
            5 -> 10
            10 -> 25
            else -> 5
        }
    }
}