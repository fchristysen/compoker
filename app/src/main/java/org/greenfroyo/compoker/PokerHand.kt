package org.greenfroyo.compoker

import android.util.ArrayMap
import org.greenfroyo.compoker.model.*
import org.greenfroyo.compoker.model.CardSuit.SPADE
import kotlin.collections.List
import kotlin.collections.all
import kotlin.collections.map
import kotlin.collections.set
import kotlin.collections.sorted

sealed class PokerHand {}

class ROYAL_STRAIGHT_FLUSH : PokerHand()
class STRAIGHT_FLUSH : PokerHand()
class FOUR_OF_A_KIND : PokerHand()
class FULL_HOUSE : PokerHand()
class STRAIGHT : PokerHand()
class FLUSH : PokerHand()
class THREE_OF_A_KIND : PokerHand()
class TWO_PAIR : PokerHand()
class ONE_PAIR : PokerHand()
class JACKS_OR_BETTER : PokerHand()

fun List<Card>.getOccurences(): Map<CardNumber, Int> {
    val occurences = CardNumber.all().associateWith { 0 }.toMutableMap()
    this.map { occurences[it.number] = (occurences[it.number] ?: 0) + 1 }
    return occurences
}

fun List<Card>.isStraight(): Boolean {
    if (this.size != 5) throw IllegalArgumentException("Card must be 5")
    val sorted = this.map { it.number.value }.sorted()
    for (i in 1 until sorted.size) {
        if (sorted[i] != sorted[i - 1] + 1 && sorted[i] - sorted[i - 1] != 9) return false
    }
    return true
}

fun List<Card>.isFlush(): Boolean {
    if (this.size != 5) throw IllegalArgumentException("Card must be 5")
    return this.all { it.suit == this[0].suit }
}

fun List<Card>.isStraightFlush(): Boolean {
    return isStraight() && isFlush()
}

fun List<Card>.isRoyalStraightFlush(): Boolean {
    return contains(Card(SPADE, TEN)) &&
            contains(Card(SPADE, JACK)) &&
            contains(Card(SPADE, QUEEN)) &&
            contains(Card(SPADE, KING)) &&
            contains(Card(SPADE, ACE))
}

fun List<Card>.isFourOfAKind(): Boolean {
    if (this.size != 5) throw IllegalArgumentException("Card must be 5")
    return this.getOccurences().containsValue(4)
}

fun List<Card>.isFullHouse(): Boolean {
    if (this.size != 5) throw IllegalArgumentException("Card must be 5")
    val occurences = this.getOccurences()
    return occurences.containsValue(3) && occurences.containsValue(2)
}

fun List<Card>.isThreeOfAKind(): Boolean {
    if (this.size != 5) throw IllegalArgumentException("Card must be 5")
    return this.getOccurences().containsValue(3)
}

fun List<Card>.isTwoPair(): Boolean {
    if (this.size != 5) throw IllegalArgumentException("Card must be 5")
    val occurences = this.getOccurences()
    return occurences.count { it.value == 2 } == 2
}

fun List<Card>.isOnePair(): Boolean {
    if (this.size != 5) throw IllegalArgumentException("Card must be 5")
    val occurences = this.getOccurences()
    return occurences.count { it.value == 2 } == 1
}

fun List<Card>.isJacksOrBetter(): Boolean {
    if (this.size != 5) throw IllegalArgumentException("Card must be 5")
    return this.all { it.number.value >= 11 }
}