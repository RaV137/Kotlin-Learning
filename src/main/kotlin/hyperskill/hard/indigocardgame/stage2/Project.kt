package hyperskill.hard.indigocardgame.stage2

import java.util.*

enum class Suit(val value: String) {
    SPADE("♠"),
    HEART("♥"),
    DIAMOND("♦"),
    CLUB("♣")
}

enum class Figure(val display: String, val value: Int) {
    ACE("A", 1),
    TWO("2", 2),
    THREE("3", 3),
    FOUR("4", 4),
    FIVE("5", 5),
    SIX("6", 6),
    SEVEN("7", 7),
    EIGHT("8", 8),
    NINE("9", 9),
    TEN("10", 10),
    JACK("J", 11),
    QUEEN("Q", 12),
    KING("K", 13)
}

data class Card(val suit: Suit, val figure: Figure) {
    override fun toString() = "${figure.display}${suit.value}"
}

class NotEnoughCardsException(message: String) : Exception(message)
class InvalidNumberOfCardsException(message: String) : Exception(message)

class Deck {
    companion object {
        const val MIN_NO_CARDS = 1
        const val MAX_NO_CARDS = 52
    }

    private val cards: Stack<Card> = Stack<Card>()

    init {
        cards.addAll(createAllCards())
    }

    fun get(number: Int): List<Card> {
        if (number !in MIN_NO_CARDS..MAX_NO_CARDS) {
            throw InvalidNumberOfCardsException("Invalid number of cards.")
        }

        if (number > cards.size) {
            throw NotEnoughCardsException("The remaining cards are insufficient to meet the request.")
        }

        return MutableList(number) { cards.pop() }
    }

    fun shuffle() {
        cards.shuffle()
        println("Card deck is shuffled.")
    }

    fun reset() {
        cards.clear()
        cards.addAll(createAllCards())
        println("Card deck is reset.")
    }

    private fun createAllCards(): List<Card> {
        val cards = mutableListOf<Card>()
        for (suit in Suit.values()) {
            for (figure in Figure.values()) {
                cards.add(Card(suit, figure))
            }
        }
        return cards
    }
}

fun main() {
    val deck = Deck()

    while (true) {
        println("Choose an action (reset, shuffle, get, exit):")
        val input = readLine()!!

        if (input == "exit") {
            println("Bye")
            return
        }

        when (input) {
            "reset" -> deck.reset()
            "shuffle" -> deck.shuffle()
            "get" -> getCardsFromDeck(deck)
            else -> println("Wrong action.")
        }
    }
}

fun getCardsFromDeck(deck: Deck) {
    println("Number of cards:")
    try {
        val number = readLine()!!.toInt()
        deck.get(number).joinToString(" ") { it.toString() }.let(::println)
    } catch (e: NumberFormatException) {
        println("Invalid number of cards.")
    } catch(e: Exception) {
        println(e.message)
    }
}
