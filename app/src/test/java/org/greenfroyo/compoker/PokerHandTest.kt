package org.greenfroyo.compoker

import org.greenfroyo.compoker.model.*
import org.greenfroyo.compoker.model.CardSuit.*
import org.junit.Assert.assertFalse
import org.junit.Test

internal class PokerHandTest {

    @Test
    fun `test isStraight`() {
        //arrange
        val cards = listOf(
            Card(SPADE, TEN),
            Card(HEART, JACK),
            Card(SPADE, QUEEN),
            Card(DIAMOND, KING),
            Card(DIAMOND, ACE),
        )

        //act assert
        assert(cards.isStraight())
    }

    @Test
    fun `test isStraight cross TWO`() {
        //arrange
        val cards = listOf(
            Card(SPADE, ACE),
            Card(HEART, TWO),
            Card(CLUB, THREE),
            Card(DIAMOND, FOUR),
            Card(SPADE, FIVE),
        )

        //act assert
        assert(cards.isStraight())
    }

    @Test
    fun `test isStraight jumbled position`() {
        //arrange
        val cards = listOf(
            Card(SPADE, EIGHT),
            Card(CLUB, SIX),
            Card(CLUB, SEVEN),
            Card(DIAMOND, FOUR),
            Card(SPADE, FIVE),
        )

        //act assert
        assert(cards.isStraight())
    }

    @Test
    fun `test isStraight return false 1`() {
        //arrange
        val cards = listOf(
            Card(SPADE, EIGHT),
            Card(CLUB, THREE),
            Card(SPADE, SEVEN),
            Card(HEART, FOUR),
            Card(SPADE, FIVE),
        )

        //act assert
        assertFalse(cards.isStraight())
    }

    @Test
    fun `test isStraight return false 2`() {
        //arrange
        val cards = listOf(
            Card(SPADE, EIGHT),
            Card(DIAMOND, THREE),
            Card(DIAMOND, SEVEN),
            Card(DIAMOND, ACE),
            Card(SPADE, FIVE),
        )

        //act assert
        assertFalse(cards.isStraight())
    }

    @Test
    fun `test isFlush`() {
        //arrange
        val cards = listOf(
            Card(SPADE, EIGHT),
            Card(SPADE, THREE),
            Card(SPADE, SEVEN),
            Card(SPADE, ACE),
            Card(SPADE, FIVE),
        )

        //act assert
        assert(cards.isFlush())
    }

    @Test
    fun `test isFlush return false`() {
        //arrange
        val cards = listOf(
            Card(SPADE, EIGHT),
            Card(HEART, THREE),
            Card(SPADE, SEVEN),
            Card(SPADE, ACE),
            Card(SPADE, FIVE),
        )

        //act assert
        assertFalse(cards.isFlush())
    }

    @Test
    fun `test isFourOfAKind 1`(){
        //arrange
        val cards = listOf(
            Card(SPADE, EIGHT),
            Card(HEART, EIGHT),
            Card(CLUB, EIGHT),
            Card(DIAMOND, EIGHT),
            Card(SPADE, FIVE),
        )

        //act assert
        assert(cards.isFourOfAKind())
    }

    @Test
    fun `test isFourOfAKind 2`(){
        //arrange
        val cards = listOf(
            Card(SPADE, ACE),
            Card(HEART, ACE),
            Card(CLUB, ACE),
            Card(DIAMOND, ACE),
            Card(SPADE, FIVE),
        )

        //act assert
        assert(cards.isFourOfAKind())
    }

    @Test
    fun `test isThreeOfAKind`(){
        //arrange
        val cards = listOf(
            Card(SPADE, ACE),
            Card(HEART, ACE),
            Card(CLUB, ACE),
            Card(DIAMOND, FOUR),
            Card(SPADE, FIVE),
        )

        //act assert
        assert(cards.isThreeOfAKind())
    }

    @Test
    fun `test isFullHouse`(){
        //arrange
        val cards = listOf(
            Card(SPADE, ACE),
            Card(HEART, ACE),
            Card(CLUB, ACE),
            Card(DIAMOND, TWO),
            Card(SPADE, TWO),
        )

        //act assert
        assert(cards.isFullHouse())
    }

    @Test
    fun `test isTwoPair`(){
        //arrange
        val cards = listOf(
            Card(SPADE, ACE),
            Card(HEART, ACE),
            Card(CLUB, EIGHT),
            Card(DIAMOND, TWO),
            Card(SPADE, TWO),
        )

        //act assert
        assert(cards.isTwoPair())
    }

    @Test
    fun `test isOnePair`(){
        //arrange
        val cards = listOf(
            Card(SPADE, ACE),
            Card(HEART, ACE),
            Card(CLUB, SEVEN),
            Card(DIAMOND, JACK),
            Card(SPADE, TWO),
        )

        //act assert
        assert(cards.isOnePair())
    }

    @Test
    fun `test isStraightFlush`(){
        //arrange
        val cards = listOf(
            Card(HEART, SEVEN),
            Card(HEART, EIGHT),
            Card(HEART, SIX),
            Card(HEART, NINE),
            Card(HEART, TEN),
        )

        //act assert
        assert(cards.isStraightFlush())
    }

    @Test
    fun `test isRoyalStraightFlush`(){
        //arrange
        val cards = listOf(
            Card(SPADE, TEN),
            Card(SPADE, ACE),
            Card(SPADE, JACK),
            Card(SPADE, KING),
            Card(SPADE, QUEEN),
        )

        //act assert
        assert(cards.isStraightFlush())
    }

    @Test
    fun `test isJacksOrBetter`(){
        //arrange
        val cards = listOf(
            Card(HEART, QUEEN),
            Card(HEART, ACE),
            Card(SPADE, JACK),
            Card(SPADE, KING),
            Card(SPADE, QUEEN),
        )

        //act assert
        assert(cards.isJacksOrBetter())
    }

    @Test
    fun `test isJacksOrBetter with two`(){
        //arrange
        val cards = listOf(
            Card(HEART, QUEEN),
            Card(HEART, ACE),
            Card(SPADE, JACK),
            Card(SPADE, KING),
            Card(SPADE, TWO),
        )

        //act assert
        assertFalse(cards.isJacksOrBetter())
    }
}