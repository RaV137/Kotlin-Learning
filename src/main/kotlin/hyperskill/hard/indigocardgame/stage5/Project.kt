package hyperskill.hard.indigocardgame.stage5

import java.util.*

// enums

enum class Suit(val value: String) {
    SPADE("♠"),
    HEART("♥"),
    DIAMOND("♦"),
    CLUB("♣")
}

enum class Figure(val display: String, val value: Int, val points: Int = 0) {
    ACE("A", 1, 1),
    TWO("2", 2),
    THREE("3", 3),
    FOUR("4", 4),
    FIVE("5", 5),
    SIX("6", 6),
    SEVEN("7", 7),
    EIGHT("8", 8),
    NINE("9", 9),
    TEN("10", 10, 1),
    JACK("J", 11, 1),
    QUEEN("Q", 12, 1),
    KING("K", 13, 1)
}

enum class PlayerType(val displayName: String) {
    HUMAN("Player"),
    COMPUTER("Computer")
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

open class Player(val playerType: PlayerType = PlayerType.HUMAN) {
    var score = 0
    var cardsGathered = 0

    protected val hand = mutableListOf<Card>()

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
    fun isComputer() = playerType == PlayerType.COMPUTER
}

class ComputerPlayer : Player(PlayerType.COMPUTER) {
    fun playCard(topCard: Card?): Card {
        hand.joinToString(" ") { it.toString() }.let(::println)

        return if (hand.size == 1) {
            playCard(1)
        } else if (topCard == null) {
            playCard(chooseCardFromHand())
        } else {
            val candidatesIdx = findCandidates(topCard)
            when (candidatesIdx.size) {
                0 -> playCard(chooseCardFromHand())
                1 -> playCard(candidatesIdx.single() + 1)
                else -> playCard(chooseCardFromCandidates(candidatesIdx))
            }
        }
    }

    private fun findCandidates(topCard: Card): IntArray {
        val candidatesIndexes = mutableListOf<Int>()
        repeat(hand.size) { idx ->
            val currCard = hand[idx]
            if (currCard.figure == topCard.figure || currCard.suit == topCard.suit) {
                candidatesIndexes.add(idx)
            }
        }
        return candidatesIndexes.toIntArray()
    }

    private fun chooseCardFromHand() = chooseCardFromIndexes((0 until hand.size).toList().toIntArray()) + 1

    private fun chooseCardFromCandidates(candidatesIdx: IntArray) = chooseCardFromIndexes(candidatesIdx) + 1

    private fun chooseCardFromIndexes(indexes: IntArray): Int {
        val suitsWithIndexes = mutableMapOf<Suit, MutableList<Int>>()
        val figuresWithIndexes = mutableMapOf<Figure, MutableList<Int>>()
        for (idx in indexes) {
            val currCard = hand[idx]
            suitsWithIndexes.putIfAbsent(currCard.suit, mutableListOf())
            suitsWithIndexes[currCard.suit]!!.add(idx)

            figuresWithIndexes.putIfAbsent(currCard.figure, mutableListOf())
            figuresWithIndexes[currCard.figure]!!.add(idx)
        }

        val suitsDuplicates = suitsWithIndexes.values.filter { it.size > 1 }.flatten()
        return if (suitsDuplicates.size >= 2) {
            suitsDuplicates.shuffled().first()
        } else {
            val figuresDuplicates = figuresWithIndexes.values.filter { it.size > 1 }.flatten()
            if (figuresDuplicates.size >= 2) {
                figuresDuplicates.shuffled().first()
            } else {
                indexes.shuffle()
                indexes.first()
            }
        }
    }
}

class Game {
    private val cardStack = Stack<Card>()
    private val deck = Deck()
    private val players = listOf(Player(), ComputerPlayer())
    private lateinit var lastGathered: PlayerType

    companion object {
        const val STARTING_STACK_CARDS_NUMBER = 4
        const val PLAYER_DRAWING_CARDS_NUMBER = 6

        const val MOST_GATHERED_CARDS_BONUS_POINTS = 3
    }

    fun run() {
        println("Indigo Card Game")
        val playerStarting = isPlayerStarting()
        lastGathered = if (playerStarting) PlayerType.HUMAN else PlayerType.COMPUTER
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
        if (currentPlayer.hasEmptyHand()) {
            try {
                currentPlayer.drawCards(deck.get(PLAYER_DRAWING_CARDS_NUMBER))
            } catch (e: NotEnoughCardsException) {
                handleNoMoreCardsGameEnd()
                throw GameOverException()
            }
        }

        if (currentPlayer.isComputer()) {
            computerPlayerTurn(currentPlayer as ComputerPlayer)
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
                val card = currentPlayer.playCard(playerInput.toInt())
                handlePlayerPlayedCard(currentPlayer, card)
                return
            } catch (e: Exception) {
                continue
            }
        }
    }

    private fun computerPlayerTurn(currentPlayer: ComputerPlayer) {
        val topCard = try {
            cardStack.peek()
        } catch (e: EmptyStackException) {
            null
        }
        val card = currentPlayer.playCard(topCard)
        println("Computer plays $card")
        handlePlayerPlayedCard(currentPlayer, card)
    }

    private fun handlePlayerPlayedCard(player: Player, card: Card) {
        if (cardStack.isNotEmpty()) {
            val cardOnTop = cardStack.peek()
            cardStack.add(card)
            if (cardOnTop.figure == card.figure || cardOnTop.suit == card.suit) {
                println("${player.playerType.displayName} wins cards")
                handleWonCards(player)
                printScores()
            }
        } else {
            cardStack.add(card)
        }
    }

    private fun handleWonCards(player: Player) {
        var sumOfPoints = 0
        val cardsGathered = cardStack.size
        while (cardStack.isNotEmpty()) {
            sumOfPoints += cardStack.pop().figure.points
        }
        player.score += sumOfPoints
        player.cardsGathered += cardsGathered
        lastGathered = player.playerType
    }

    private fun handleNoMoreCardsGameEnd() {
        val playerHuman = players[0]
        val playerComputer = players[1]
        val lastGatheredPlayer = if (lastGathered == PlayerType.HUMAN) playerHuman else playerComputer
        handleWonCards(lastGatheredPlayer)

        if (playerHuman.cardsGathered > playerComputer.cardsGathered) {
            playerHuman.score += MOST_GATHERED_CARDS_BONUS_POINTS
        } else if (playerComputer.cardsGathered > playerHuman.cardsGathered) {
            playerComputer.score += MOST_GATHERED_CARDS_BONUS_POINTS
        } else {
            lastGatheredPlayer.score += MOST_GATHERED_CARDS_BONUS_POINTS
        }
        printScores()
    }

    private fun printScores() {
        val player = players[0]
        val computer = players[1]
        println("Score: Player ${player.score} - Computer ${computer.score}")
        println("Cards: Player ${player.cardsGathered} - Computer ${computer.cardsGathered}")
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
        if (cardStack.isEmpty()) {
            println("\nNo cards on the table")
        } else {
            println("\n${cardStack.size} cards on the table, and the top card is ${cardStack.peek()}")
        }
    }
}

// main

fun main() {
    Game().run()
}
