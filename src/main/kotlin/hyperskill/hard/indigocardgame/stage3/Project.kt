package hyperskill.hard.indigocardgame.stage3

import java.util.*

// enums

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

// data classes

data class Card(val suit: Suit, val figure: Figure) {
    override fun toString() = "${figure.display}${suit.value}"
}

// exceptions

class NotEnoughCardsException(message: String) : Exception(message)
class InvalidNumberOfCardsException(message: String) : Exception(message)
class GameOverException : Exception()

// classes

class Deck {
    companion object {
        const val MIN_NO_CARDS = 1
        const val MAX_NO_CARDS = 52
    }

    private val cards: Stack<Card> = Stack<Card>()

    init {
        cards.addAll(createAllCards())
        cards.shuffle()
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

class Player(val isComputer: Boolean = false) {
    private val hand = mutableListOf<Card>()

    fun printHand() {
        val cardString = hand.withIndex().joinToString(" ") { "${it.index + 1})${it.value}" }
        println("Cards in hand: $cardString")
    }

    fun playCard(cardNumber: Int): Card {
        return hand.removeAt(cardNumber - 1)
    }

    fun drawCards(cards: List<Card>) {
        hand.addAll(cards)
    }

    fun hasEmptyHand() = hand.isEmpty()
    fun cardsSize() = hand.size
}

class Game {
    private val cardStack = Stack<Card>()
    private val deck = Deck()
    private val players = listOf(Player(), Player(true))

    companion object {
        const val STARTING_STACK_CARDS_NUMBER = 4
        const val PLAYER_DRAWING_CARDS_NUMBER = 6
        const val FULL_STACK_SIZE = 52
    }

    fun run() {
        println("Indigo Card Game")
        val playerStarting = isPlayerStarting()
        initGame()

        var roundIndex = if (playerStarting) 0 else 1

        while (true) {
            printCardStackStatus()
            val currentPlayer = players[roundIndex % 2]
            try {
                playerTurn(currentPlayer)
            } catch (e: GameOverException) {
                println("Game Over")
                return
            }

            roundIndex++
        }
    }

    private fun playerTurn(currentPlayer: Player) {
        if (cardStack.size == FULL_STACK_SIZE) throw GameOverException()

        if (currentPlayer.hasEmptyHand()) {
            currentPlayer.drawCards(deck.get(PLAYER_DRAWING_CARDS_NUMBER))
        }

        if (currentPlayer.isComputer) {
            computerPlayerTurn(currentPlayer)
        } else {
            humanPlayerTurn(currentPlayer)
        }
    }

    private fun humanPlayerTurn(currentPlayer: Player) {
        currentPlayer.printHand()
        while (true) {
            println("Choose a card to play (1-${currentPlayer.cardsSize()}):")
            val playerInput = readLine()!!
            if (playerInput == "exit") throw GameOverException()

            try {
                handlePlayerPlayedCard(currentPlayer.playCard(playerInput.toInt()))
                return
            } catch (e: Exception) {
                continue
            }
        }
    }

    private fun computerPlayerTurn(currentPlayer: Player) {
        val cardNumberToPlay = (1..currentPlayer.cardsSize()).shuffled().first()
        val card = currentPlayer.playCard(cardNumberToPlay)
        handlePlayerPlayedCard(card)
        println("Computer plays $card")
    }

    private fun handlePlayerPlayedCard(card: Card) {
        cardStack.add(card)
    }

    private fun initGame() {
        val initialCards = deck.get(STARTING_STACK_CARDS_NUMBER)
        cardStack.addAll(initialCards)
        println("Initial cards on the table: ${initialCards.joinToString(" ")}")

        players.forEach { it.drawCards(deck.get(PLAYER_DRAWING_CARDS_NUMBER)) }
    }

    private fun isPlayerStarting(): Boolean {
        while (true) {
            println("Play first?")
            val input = readLine()!!
            return when (input.lowercase()) {
                "yes" -> true
                "no" -> false
                else -> continue
            }
        }
    }

    private fun printCardStackStatus() {
        println("\n${cardStack.size} cards on the table, and the top card is ${cardStack.peek()}")
    }
}

// main

fun main() {
    Game().run()
}
