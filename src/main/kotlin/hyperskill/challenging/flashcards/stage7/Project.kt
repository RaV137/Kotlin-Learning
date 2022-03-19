package hyperskill.challenging.flashcards.stage7

import java.io.File
import kotlin.random.Random

data class Flashcard(val term: String, val definition: String, var mistakes: Int = 0) {
    fun checkAnswer(answer: String) = answer.lowercase() == definition.lowercase()

    fun saveToString(): String {
        return "$term$DELIMITER$definition$DELIMITER$mistakes"
    }

    companion object IOHandler {
        const val DELIMITER = " ###|||### "

        fun readFromString(str: String): Flashcard {
            return str.split(DELIMITER).let { Flashcard(it[0], it[1], it[2].toInt()) }
        }
    }

    fun incrementMistakes() {
        mistakes += 1
    }

    fun resetMistakes() {
        mistakes = 0
    }
}

object FlashcardsHandler {
    private val cards = mutableListOf<Flashcard>()

    fun addFlashcard() {
        UserIOHandler.printLine("The card:")
        val term = readTerm() ?: return
        UserIOHandler.printLine("The definition of the card:")
        val definition = readDefinition() ?: return
        cards.add(Flashcard(term, definition))
        UserIOHandler.printLine("The pair (\"$term\":\"$definition\") has been added.")
    }

    fun removeFlashcard() {
        UserIOHandler.printLine("Which card?")
        val input = UserIOHandler.readLn()
        if (cards.removeIf { it.term == input }) {
            UserIOHandler.printLine("The card has been removed.")
        } else {
            UserIOHandler.printLine("Can't remove \"$input\": there is no such card.")
        }
    }

    fun importFlashcards() {
        UserIOHandler.printLine("File name:")
        val filename = UserIOHandler.readLn()
        importFlashcards(filename)
    }

    fun importFlashcards(filename: String) {
        val file = File(filename)
        if (!file.exists()) {
            UserIOHandler.printLine("File not found.")
            return
        }

        val fileLines = file.readLines()
        val numberOfCards = fileLines[0].toInt()
        for (lineNumber in 1..numberOfCards) {
            val newCard = Flashcard.readFromString(fileLines[lineNumber])
            cards.removeIf { it.term == newCard.term }
            cards.add(newCard)
        }

        UserIOHandler.printLine("$numberOfCards cards have been loaded.")
    }

    fun exportFlashcards() {
        UserIOHandler.printLine("File name:")
        val filename = UserIOHandler.readLn()
        exportFlashcards(filename)
    }

    fun exportFlashcards(filename: String) {
        val file = File(filename)

        file.writeText(cards.size.toString())
        file.appendText("\n")

        for (card in cards) {
            file.appendText(card.saveToString())
            file.appendText("\n")
        }

        UserIOHandler.printLine("${cards.size} cards have been saved.")
    }

    fun answerFlashcards() {
        UserIOHandler.printLine("How many times to ask?")
        val numOfCards = UserIOHandler.readLn().toInt()
        for (cardNum in 1..numOfCards) {
            val idx = Random.nextInt(cards.size)
            answerFlashcard(idx)
        }
    }

    fun hardestCard() {
        val hardestCards = mutableListOf<Flashcard>()
        var maxMistakes = 0
        for (card in cards) {
            val cardMistakes = card.mistakes
            if (cardMistakes > maxMistakes) {
                hardestCards.clear()
                maxMistakes = cardMistakes
                hardestCards.add(card)
            } else if (cardMistakes == maxMistakes && maxMistakes != 0) {
                hardestCards.add(card)
            }
        }

        if (hardestCards.isEmpty()) {
            UserIOHandler.printLine("There are no cards with errors.")
        } else if (hardestCards.size == 1) {
            val card = hardestCards.single()
            UserIOHandler.printLine("The hardest card is \"${card.term}\". You have $maxMistakes errors answering it.")
        } else {
            val cardTermsString = hardestCards.map { it.term }.joinToString(", ") { "\"$it\"" }
            UserIOHandler.printLine("The hardest cards are $cardTermsString. You have $maxMistakes errors answering them.")
        }
    }

    fun resetMistakes() {
        cards.forEach { it.resetMistakes() }
        UserIOHandler.printLine("Card statistics have been reset.")
    }

    private fun answerFlashcard(idx: Int) {
        val card = cards[idx]
        UserIOHandler.printLine("Print the definition of \"${card.term}\":")
        val answer = UserIOHandler.readLn()
        if (card.checkAnswer(answer)) {
            UserIOHandler.printLine("Correct!")
        } else {
            card.incrementMistakes()
            for (c in cards) {
                if (c != card) {
                    if (c.checkAnswer(answer)) {
                        UserIOHandler.printLine("Wrong. The right answer is \"${card.definition}\", but your definition is correct for \"${c.term}\"")
                        return
                    }
                }
            }
            UserIOHandler.printLine("Wrong. The right answer is \"${card.definition}\".")
        }
    }

    private fun readTerm(): String? {
        val term = UserIOHandler.readLn()
        for (card in cards) {
            if (card.term == term) {
                UserIOHandler.printLine("The card \"$term\" already exists.")
                return null
            }
        }
        return term
    }

    private fun readDefinition(): String? {
        val definition = UserIOHandler.readLn()
        for (card in cards) {
            if (card.definition == definition) {
                UserIOHandler.printLine("The definition \"$definition\" already exists.")
                return null
            }
        }
        return definition
    }
}

object UserIOHandler {
    private val ioLog = mutableListOf<String>()

    fun printLine(line: String) {
        ioLog.add(line)
        println(line)
    }

    fun readLn(): String {
        return readLine()!!.apply { ioLog.add(this) }
    }

    fun saveLog() {
        printLine("File name:")
        val filename = readLn()
        val file = File(filename)
        file.writeText(ioLog.joinToString("\n"))
        printLine("The log has been saved.")
    }
}

data class Arguments(val inputFilename: String?, val outputFilename: String?)

object ArgumentParser {
    private const val IMPORT_ARGUMENT = "-import"
    private const val EXPORT_ARGUMENT = "-export"

    fun parseArguments(args: Array<String>): Arguments {
        val indexOfImportArgument = args.indexOf(IMPORT_ARGUMENT)
        val inputFilename = if (indexOfImportArgument >= 0) args[indexOfImportArgument + 1] else null
        val indexOfExportArgument = args.indexOf(EXPORT_ARGUMENT)
        val outputFilename = if (indexOfExportArgument >= 0) args[indexOfExportArgument + 1] else null
        return Arguments(inputFilename, outputFilename)
    }
}

fun main(args: Array<String>) {
    val arguments = ArgumentParser.parseArguments(args)
    if (arguments.inputFilename != null) {
        FlashcardsHandler.importFlashcards(arguments.inputFilename)
    }

    while (true) {
        UserIOHandler.printLine("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):")
        val input = UserIOHandler.readLn()
        if (input == "exit") {
            UserIOHandler.printLine("Bye bye!")
            break
        }

        when (input) {
            "add" -> FlashcardsHandler.addFlashcard()
            "remove" -> FlashcardsHandler.removeFlashcard()
            "import" -> FlashcardsHandler.importFlashcards()
            "export" -> FlashcardsHandler.exportFlashcards()
            "ask" -> FlashcardsHandler.answerFlashcards()
            "log" -> UserIOHandler.saveLog()
            "hardest card" -> FlashcardsHandler.hardestCard()
            "reset stats" -> FlashcardsHandler.resetMistakes()
        }
    }
    if (arguments.outputFilename != null) {
        FlashcardsHandler.exportFlashcards(arguments.outputFilename)
    }
}
