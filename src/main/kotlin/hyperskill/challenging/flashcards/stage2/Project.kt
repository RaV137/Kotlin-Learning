package hyperskill.challenging.flashcards.stage2

data class FlashCard(val term: String, val definition: String) {
    fun checkAnswer(answer: String) = answer.lowercase() == definition.lowercase()
}

fun main() {
    val card = readFlashCard()
    val answer = readLine()!!
    println(if (card.checkAnswer(answer)) "right" else "wrong")
}

fun readFlashCard(): FlashCard {
    val term = readLine()!!
    val definition = readLine()!!
    return FlashCard(term, definition)
}
