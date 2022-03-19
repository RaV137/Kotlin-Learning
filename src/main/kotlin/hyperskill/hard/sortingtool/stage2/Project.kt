package hyperskill.hard.sortingtool.stage2

import java.util.*

enum class InputDataType {
    LONG, LINE, WORD
}

object InputReader {
    private val scanner = Scanner(System.`in`)

    fun readNextLong() = scanner.nextLong()

    fun readNextWord(): String = scanner.next()

    fun readNextLine(): String = scanner.nextLine()

    fun hasNext() = scanner.hasNext()
}

class LongReader {
    private val numbers = mutableMapOf<Long, Int>()

    fun readAndPrintInfo() {
        while (InputReader.hasNext()) {
            val number = InputReader.readNextLong()
            numbers.putIfAbsent(number, 0)
            numbers[number] = numbers[number]!!.inc()
        }

        val (maxNumber, maxNumberOccurrences) = findMaxNumberEntry()
        val totalNumbers = findTotalNumbers()
        val percentage = maxNumberOccurrences * 100 / totalNumbers

        println("Total numbers: $totalNumbers.")
        println("The greatest number: $maxNumber ($maxNumberOccurrences time(s), $percentage%).")
    }

    private fun findMaxNumberEntry(): Pair<Long, Int> {
        var maxNumber = Long.MIN_VALUE
        var maxNumberOccurrences = 0
        for (entry in numbers.entries) {
            if (entry.key > maxNumber) {
                maxNumber = entry.key
                maxNumberOccurrences = entry.value
            }
        }
        return Pair(maxNumber, maxNumberOccurrences)
    }

    private fun findTotalNumbers() = numbers.values.sum()
}

class LineReader {
    private val lines = mutableMapOf<String, Int>()

    fun readAndPrintInfo() {
        while (InputReader.hasNext()) {
            val line = InputReader.readNextLine()
            lines.putIfAbsent(line, 0)
            lines[line] = lines[line]!!.inc()
        }

        val totalLines = findTotalLines()
        val (longestLine, longestLineOccurrences) = findLongestLineEntry()
        val percentage = longestLineOccurrences * 100 / totalLines

        println("Total lines: $totalLines")
        println("The longest line:")
        println(longestLine)
        println("($longestLineOccurrences time(s), $percentage%).")
    }

    private fun findLongestLineEntry(): Pair<String, Int> {
        val longestLine = lines.keys
            .sorted()
            .maxByOrNull { it.length }!!
        return Pair(longestLine, lines[longestLine]!!)
    }

    private fun findTotalLines(): Int = lines.values.sum()
}

class WordReader {
    private val words = mutableMapOf<String, Int>()

    fun readAndPrintInfo() {
        while (InputReader.hasNext()) {
            val word = InputReader.readNextWord()
            words.putIfAbsent(word, 0)
            words[word] = words[word]!!.inc()
        }

        val totalWords = findTotalWords()
        val (longestWord, longestWordOccurrences) = findLongestWordEntry()
        val percentage = longestWordOccurrences * 100 / totalWords

        println("Total lines: $totalWords")
        println("The longest word: $longestWord ($longestWordOccurrences time(s), $percentage%).")
    }

    private fun findLongestWordEntry(): Pair<String, Int> {
        val longestLine = words.keys
            .sorted()
            .maxByOrNull { it.length }!!
        return Pair(longestLine, words[longestLine]!!)
    }

    private fun findTotalWords(): Int = words.values.sum()
}

fun main(args: Array<String>) {
    when (readInputDataType(args)) {
        InputDataType.WORD -> WordReader().readAndPrintInfo()
        InputDataType.LINE -> LineReader().readAndPrintInfo()
        InputDataType.LONG -> LongReader().readAndPrintInfo()
    }
}

fun readInputDataType(args: Array<String>): InputDataType {
    if (args.size < 2 || args[0] != "-dataType") {
        return InputDataType.WORD
    }

    return when (args[1]) {
        "long" -> InputDataType.LONG
        "line" -> InputDataType.LINE
        else -> InputDataType.WORD
    }
}
