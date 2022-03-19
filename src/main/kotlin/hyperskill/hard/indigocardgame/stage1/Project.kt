package hyperskill.hard.indigocardgame.stage1

enum class Suit(val value: String) {
    SPADE("♠"),
    HEART("♠"),
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

fun main() {
    Figure.values().joinToString(" ") { it.display }.let(::println)
    println()
    Suit.values().joinToString(" ") { it.value }.let(::println)
    println()
    val cards = createAllCards()
    cards.joinToString(" ") { it.toString() }.let(::println)
}

fun createAllCards(): List<Card> {
    val cards = mutableListOf<Card>()
    for (suit in Suit.values()) {
        for (figure in Figure.values()) {
            cards.add(Card(suit, figure))
        }
    }
    return cards
}
