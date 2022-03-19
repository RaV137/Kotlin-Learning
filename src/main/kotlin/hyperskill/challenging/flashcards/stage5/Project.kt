package hyperskill.challenging.flashcards.stage5

import java.io.File
import kotlin.random.Random

data class Flashcard(val term: String, val definition: String) {
    fun checkAnswer(answer: String) = answer.lowercase() == definition.lowercase()

    fun saveToString(): String {
        return "$term$DELIMITER$definition"
    }

    companion object IOHandler {
        const val DELIMITER = " ###|||### "

        fun readFromString(str: String): Flashcard {
            return str.split(DELIMITER).let { Flashcard(it[0], it[1]) }
        }
    }
}

val cards = mutableListOf<Flashcard>()

fun main() {
    while (true) {
        println("Input the action (add, remove, import, export, ask, exit):")
        val input = readLine()!!
        if (input == "exit") {
            println("Bye bye!")
            return
        }

        when (input) {
            "add" -> addFlashcard()
            "remove" -> removeFlashcard()
            "import" -> importFlashcards()
            "export" -> exportFlashcards()
            "ask" -> answerFlashcards()
        }
    }
}

fun addFlashcard() {
    println("The card:")
    val term = readTerm() ?: return
    println("The definition of the card:")
    val definition = readDefinition() ?: return
    cards.add(Flashcard(term, definition))
    println("The pair (\"$term\":\"$definition\") has been added.")
}

fun readTerm(): String? {
    val term = readLine()!!
    for (card in cards) {
        if (card.term == term) {
            println("The card \"$term\" already exists.")
            return null
        }
    }
    return term
}

fun readDefinition(): String? {
    val definition = readLine()!!
    for (card in cards) {
        if (card.definition == definition) {
            println("The definition \"$definition\" already exists.")
            return null
        }
    }
    return definition
}

fun removeFlashcard() {
    println("Which card?")
    val input = readLine()!!
    if (cards.removeIf { it.term == input }) {
        println("The card has been removed.")
    } else {
        println("Can't remove \"$input\": there is no such card.")
    }
}

fun importFlashcards() {
    println("File name:")
    val filename = readLine()!!
    val file = File(filename)
    if (!file.exists()) {
        println("File not found.")
        return
    }

    val fileLines = file.readLines()
    val numberOfCards = fileLines[0].toInt()
    for (lineNumber in 1..numberOfCards) {
        val newCard = Flashcard.readFromString(fileLines[lineNumber])
        cards.removeIf { it.term == newCard.term }
        cards.add(newCard)
    }

    println("$numberOfCards cards have been loaded.")
}

fun exportFlashcards() {
    println("File name:")
    val filename = readLine()!!
    val file = File(filename)

    file.writeText(cards.size.toString())
    file.appendText("\n")

    for (card in cards) {
        file.appendText(card.saveToString())
        file.appendText("\n")
    }

    println("${cards.size} cards have been saved.")
}

fun answerFlashcards() {
    println("How many times to ask?")
    val numOfCards = readLine()!!.toInt()
    for (cardNum in 1..numOfCards) {
        val idx = Random.nextInt(cards.size)
        answerFlashcard(idx)
    }
}

fun answerFlashcard(idx: Int) {
    val card = cards[idx]
    println("Print the definition of \"${card.term}\":")
    val answer = readLine()!!
    if (card.checkAnswer(answer)) {
        println("Correct!")
    } else {
        for (c in cards) {
            if (c != card) {
                if (c.checkAnswer(answer)) {
                    println("Wrong. The right answer is \"${card.definition}\", but your definition is correct for \"${c.term}\"")
                    return
                }
            }
        }
        println("Wrong. The right answer is \"${card.definition}\".")
    }
}
