package org.greenfroyo.compoker.model

import android.content.Context
import androidx.annotation.DrawableRes

class Card(val suit: CardSuit = CardSuit.HIDDEN, val number: CardNumber = ACE) {
    @DrawableRes
    fun getImageResources(context: Context): Int{
        val drawableName =
            if (suit == CardSuit.HIDDEN) "back"
            else "${suitToDrawableName()}_${numberToDrawableName()}"
        return context.resources.getIdentifier(drawableName, "drawable", context.packageName)
    }

    private fun suitToDrawableName() = when(suit){
        CardSuit.HIDDEN -> "back"
        CardSuit.SPADE -> "spades"
        CardSuit.HEART -> "hearts"
        CardSuit.CLUB -> "clubs"
        CardSuit.DIAMOND -> "diamonds"
    }

    private fun numberToDrawableName() = when(number){
        ACE -> "ace"
        EIGHT -> "08"
        FIVE -> "05"
        FOUR -> "04"
        JACK -> "j"
        KING -> "k"
        NINE -> "09"
        QUEEN -> "q"
        SEVEN -> "07"
        SIX -> "06"
        TEN -> "10"
        THREE -> "03"
        TWO -> "02"
    }
}

enum class CardSuit() {
    HIDDEN,
    SPADE,
    HEART,
    CLUB,
    DIAMOND
}

sealed class CardNumber(val value: Int) {
    companion object{
        fun all() = listOf(
            TWO,
            THREE,
            FOUR,
            FIVE,
            SIX,
            SEVEN,
            EIGHT,
            NINE,
            TEN,
            JACK,
            QUEEN,
            KING,
            ACE,
        )
    }
}

object TWO : CardNumber(2)
object THREE : CardNumber(3)
object FOUR : CardNumber(4)
object FIVE : CardNumber(5)
object SIX : CardNumber(6)
object SEVEN : CardNumber(7)
object EIGHT : CardNumber(8)
object NINE : CardNumber(9)
object TEN : CardNumber(10)
object JACK : CardNumber(11)
object QUEEN : CardNumber(12)
object KING : CardNumber(13)
object ACE : CardNumber(14)