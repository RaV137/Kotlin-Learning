package hyperskill.challenging.flashcards.stage4

data class FlashCard(val term: String, val definition: String) {
    fun checkAnswer(answer: String) = answer.lowercase() == definition.lowercase()
}

val cards = mutableListOf<FlashCard>()

fun main() {
    println("Input the number of cards:")
    val numOfCards = readLine()!!.toInt()
    for (num in 1..numOfCards) {
        cards.add(readFlashCard(num))
    }
    answerFlashcards()
}

fun readFlashCard(num: Int): FlashCard {
    println("Card #$num:")
    val term = readTerm()
    println("The definition for card #$num:")
    val definition = readDefinition()
    return FlashCard(term, definition)
}

fun readTerm(): String {
    var term: String
    readTermLoop@ while (true) {
        term = readLine()!!
        for (card in cards) {
            if (card.term == term) {
                println("The term \"$term\" already exists. Try again:")
                continue@readTermLoop
            }
        }
        break
    }
    return term
}

fun readDefinition(): String {
    var definition: String
    readDefinitionLoop@ while (true) {
        definition = readLine()!!
        for (card in cards) {
            if (card.definition == definition) {
                println("The definition \"$definition\" already exists. Try again:")
                continue@readDefinitionLoop
            }
        }
        break
    }
    return definition
}

fun answerFlashcards() {
    answersLoop@for (card in cards) {
        println("Print the definition of \"${card.term}\":")
        val answer = readLine()!!

        if (card.checkAnswer(answer)) println("Correct!")
        else {
            for (c in cards) {
                if (c != card) {
                    if (c.checkAnswer(answer)) {
                        println("Wrong. The right answer is \"${card.definition}\", but your definition is correct for \"${c.term}\"")
                        continue@answersLoop
                    }
                }
            }
            println("Wrong. The right answer is \"${card.definition}\".")
        }
    }
}
