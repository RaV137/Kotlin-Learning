package hyperskill.challenging.flashcards.stage3

data class FlashCard(val term: String, val definition: String) {
    fun checkAnswer(answer: String) = answer.lowercase() == definition.lowercase()
}

fun main() {
    println("Input the number of cards:")
    val numOfCards = readLine()!!.toInt()
    val cards = mutableListOf<FlashCard>()
    for (num in 1..numOfCards) {
        cards.add(readFlashCard(num))
    }
    answerFlashcards(cards)
}

fun readFlashCard(num: Int): FlashCard {
    println("Card #$num:")
    val term = readLine()!!
    println("The definition for card #$num:")
    val definition = readLine()!!
    return FlashCard(term, definition)
}

fun answerFlashcards(cards: List<FlashCard>) {
    for (card in cards) {
        println("Print the definition of \"${card.term}\":")
        val answer = readLine()!!
        println(
            if (card.checkAnswer(answer)) "Correct!"
            else "Wrong. The right answer is \"${card.definition}\"."
        )
    }
}
