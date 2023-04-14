package org.greenfroyo.compoker

import androidx.annotation.StringRes
import org.greenfroyo.compoker.model.*
import org.greenfroyo.compoker.model.CardSuit.SPADE
import kotlin.collections.set

sealed class PokerHand() {
    @StringRes
    abstract fun getDisplayText(): Int
    abstract fun getWinningMultiplier(): Int
}

object ROYAL_STRAIGHT_FLUSH : PokerHand() {
    override fun getDisplayText() = R.string.text_win_royal_straight_flush
    override fun getWinningMultiplier() = 250
}

object STRAIGHT_FLUSH : PokerHand() {
    override fun getDisplayText() = R.string.text_win_flush
    override fun getWinningMultiplier() = 50
}

object FOUR_OF_A_KIND : PokerHand() {
    override fun getDisplayText() = R.string.text_win_four_of_a_kind
    override fun getWinningMultiplier() = 25
}

object FULL_HOUSE : PokerHand() {
    override fun getDisplayText() = R.string.text_win_full_house
    override fun getWinningMultiplier() = 9
}

object STRAIGHT : PokerHand() {
    override fun getDisplayText() = R.string.text_win_straight
    override fun getWinningMultiplier() = 6
}

object FLUSH : PokerHand() {
    override fun getDisplayText() = R.string.text_win_flush
    override fun getWinningMultiplier() = 5
}

object THREE_OF_A_KIND : PokerHand() {
    override fun getDisplayText() = R.string.text_win_three_of_a_kind
    override fun getWinningMultiplier() = 3
}

object TWO_PAIR : PokerHand() {
    override fun getDisplayText() = R.string.text_win_two_pair
    override fun getWinningMultiplier() = 3
}

object ONE_PAIR : PokerHand() {
    override fun getDisplayText() = R.string.text_win_one_pair
    override fun getWinningMultiplier() = 1
}

object JACKS_OR_BETTER : PokerHand() {
    override fun getDisplayText() = R.string.text_win_jacks_or_better
    override fun getWinningMultiplier() = 1
}

private fun Array<Card>.getOccurences(): Map<CardNumber, Int> {
    val occurences = CardNumber.all().associateWith { 0 }.toMutableMap()
    this.map { occurences[it.number] = (occurences[it.number] ?: 0) + 1 }
    return occurences
}

fun Array<Card>.isStraight(): Boolean {
    if (this.size != 5) throw IllegalArgumentException("Card must be 5")
    val sorted = this.map { it.number.value }.sorted()
    for (i in 1 until sorted.size) {
        if (sorted[i] != sorted[i - 1] + 1 && sorted[i] - sorted[i - 1] != 9) return false
    }
    return true
}

fun Array<Card>.isFlush(): Boolean {
    if (this.size != 5) throw IllegalArgumentException("Card must be 5")
    return this.all { it.suit == this[0].suit }
}

fun Array<Card>.isStraightFlush(): Boolean {
    return isStraight() && isFlush()
}

fun Array<Card>.isRoyalStraightFlush(): Boolean {
    return contains(Card(SPADE, TEN)) &&
            contains(Card(SPADE, JACK)) &&
            contains(Card(SPADE, QUEEN)) &&
            contains(Card(SPADE, KING)) &&
            contains(Card(SPADE, ACE))
}

fun Array<Card>.isFourOfAKind(): Boolean {
    if (this.size != 5) throw IllegalArgumentException("Card must be 5")
    return this.getOccurences().containsValue(4)
}

fun Array<Card>.isFullHouse(): Boolean {
    if (this.size != 5) throw IllegalArgumentException("Card must be 5")
    val occurences = this.getOccurences()
    return occurences.containsValue(3) && occurences.containsValue(2)
}

fun Array<Card>.isThreeOfAKind(): Boolean {
    if (this.size != 5) throw IllegalArgumentException("Card must be 5")
    return this.getOccurences().containsValue(3)
}

fun Array<Card>.isTwoPair(): Boolean {
    if (this.size != 5) throw IllegalArgumentException("Card must be 5")
    val occurences = this.getOccurences()
    return occurences.count { it.value == 2 } == 2
}

fun Array<Card>.isOnePair(): Boolean {
    if (this.size != 5) throw IllegalArgumentException("Card must be 5")
    val occurences = this.getOccurences()
    return occurences.count { it.value == 2 } == 1
}

fun Array<Card>.isJacksOrBetter(): Boolean {
    if (this.size != 5) throw IllegalArgumentException("Card must be 5")
    return this.all { it.number.value >= 11 }
}

fun Array<Card>.getPokerHand(): PokerHand? {
    return if (isRoyalStraightFlush()) ROYAL_STRAIGHT_FLUSH
    else if (isStraightFlush()) STRAIGHT_FLUSH
    else if (isFourOfAKind()) FOUR_OF_A_KIND
    else if (isFullHouse()) FULL_HOUSE
    else if (isFlush()) FLUSH
    else if (isStraight()) STRAIGHT
    else if (isThreeOfAKind()) THREE_OF_A_KIND
    else if (isTwoPair()) TWO_PAIR
    else if (isOnePair()) ONE_PAIR
    else if (isJacksOrBetter()) JACKS_OR_BETTER
    else null
}