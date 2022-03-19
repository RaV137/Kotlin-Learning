package hyperskill.hard.sortingtool.stage3

import java.util.*

enum class Operation {
    READ_WORDS,
    READ_LINES,
    READ_LONGS,
    SORT_INTEGERS
}

object InputReader {
    private val scanner = Scanner(System.`in`)

    fun readNextInt() = scanner.nextInt()

    fun readNextLong() = scanner.nextLong()

    fun readNextWord(): String = scanner.next()

    fun readNextLine(): String = scanner.nextLine()

    fun hasNext() = scanner.hasNext()
}

class LongReader {
    private val numbers = mutableMapOf<Long, Int>()

    fun run() {
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

    fun run() {
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

    fun run() {
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

class IntegerSorter {
    private val numbers = mutableListOf<Int>()

    fun run() {
        while (InputReader.hasNext()) {
            numbers.add(InputReader.readNextInt())
        }

        println("Total numbers: ${numbers.size}.")
        println("Sorted data: ${numbers.sorted().joinToString(" ")}")
    }
}

fun main(args: Array<String>) {
    when (readInputDataType(args)) {
        Operation.READ_WORDS -> WordReader().run()
        Operation.READ_LINES -> LineReader().run()
        Operation.READ_LONGS -> LongReader().run()
        Operation.SORT_INTEGERS -> IntegerSorter().run()
    }
}

fun readInputDataType(args: Array<String>): Operation {
    if ("-sortIntegers" in args) {
        return Operation.SORT_INTEGERS
    }

    if (args.size < 2 || args[0] != "-dataType") {
        return Operation.READ_WORDS
    }

    return when (args[1]) {
        "long" -> Operation.READ_LONGS
        "line" -> Operation.READ_LINES
        else -> Operation.READ_WORDS
    }
}
