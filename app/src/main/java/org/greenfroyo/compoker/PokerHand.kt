package org.greenfroyo.compoker

import androidx.annotation.StringRes
import org.greenfroyo.compoker.model.*
import org.greenfroyo.compoker.model.CardSuit.SPADE
import kotlin.collections.set

sealed class PokerHand() {
    @StringRes
    abstract fun getDisplayText(): Int
}

object ROYAL_STRAIGHT_FLUSH : PokerHand() {
    override fun getDisplayText() = R.string.text_win_royal_straight_flush
}

object STRAIGHT_FLUSH : PokerHand() {
    override fun getDisplayText() = R.string.text_win_flush
}

object FOUR_OF_A_KIND : PokerHand() {
    override fun getDisplayText() = R.string.text_win_four_of_a_kind
}

object FULL_HOUSE : PokerHand() {
    override fun getDisplayText() = R.string.text_win_full_house
}

object STRAIGHT : PokerHand() {
    override fun getDisplayText() = R.string.text_win_straight
}

object FLUSH : PokerHand() {
    override fun getDisplayText() = R.string.text_win_flush
}

object THREE_OF_A_KIND : PokerHand() {
    override fun getDisplayText() = R.string.text_win_three_of_a_kind
}

object TWO_PAIR : PokerHand() {
    override fun getDisplayText() = R.string.text_win_two_pair
}

object ONE_PAIR : PokerHand() {
    override fun getDisplayText() = R.string.text_win_one_pair
}

object JACKS_OR_BETTER : PokerHand() {
    override fun getDisplayText() = R.string.text_win_jacks_or_better
}

private fun Array<Card>.getOccurences(): Map<CardNumber, Int> {
    val occurences = CardNumber.all().associateWith { 0 }.toMutableMap()
    this.map { occurences[it.number] = (occurences[it.number] ?: 0) + 1 }
    return occurences
}

private fun Array<Card>.isStraight(): Boolean {
    if (this.size != 5) throw IllegalArgumentException("Card must be 5")
    val sorted = this.map { it.number.value }.sorted()
    for (i in 1 until sorted.size) {
        if (sorted[i] != sorted[i - 1] + 1 && sorted[i] - sorted[i - 1] != 9) return false
    }
    return true
}

private fun Array<Card>.isFlush(): Boolean {
    if (this.size != 5) throw IllegalArgumentException("Card must be 5")
    return this.all { it.suit == this[0].suit }
}

private fun Array<Card>.isStraightFlush(): Boolean {
    return isStraight() && isFlush()
}

private fun Array<Card>.isRoyalStraightFlush(): Boolean {
    return contains(Card(SPADE, TEN)) &&
            contains(Card(SPADE, JACK)) &&
            contains(Card(SPADE, QUEEN)) &&
            contains(Card(SPADE, KING)) &&
            contains(Card(SPADE, ACE))
}

private fun Array<Card>.isFourOfAKind(): Boolean {
    if (this.size != 5) throw IllegalArgumentException("Card must be 5")
    return this.getOccurences().containsValue(4)
}

private fun Array<Card>.isFullHouse(): Boolean {
    if (this.size != 5) throw IllegalArgumentException("Card must be 5")
    val occurences = this.getOccurences()
    return occurences.containsValue(3) && occurences.containsValue(2)
}

private fun Array<Card>.isThreeOfAKind(): Boolean {
    if (this.size != 5) throw IllegalArgumentException("Card must be 5")
    return this.getOccurences().containsValue(3)
}

private fun Array<Card>.isTwoPair(): Boolean {
    if (this.size != 5) throw IllegalArgumentException("Card must be 5")
    val occurences = this.getOccurences()
    return occurences.count { it.value == 2 } == 2
}

private fun Array<Card>.isOnePair(): Boolean {
    if (this.size != 5) throw IllegalArgumentException("Card must be 5")
    val occurences = this.getOccurences()
    return occurences.count { it.value == 2 } == 1
}

private fun Array<Card>.isJacksOrBetter(): Boolean {
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