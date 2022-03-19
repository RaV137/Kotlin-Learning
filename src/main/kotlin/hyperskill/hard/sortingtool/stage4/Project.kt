package hyperskill.hard.sortingtool.stage4

import java.util.*
import kotlin.math.roundToInt

enum class Operation {
    READ_WORDS,
    READ_LINES,
    READ_LONGS,
}

enum class SortingType {
    COUNT, NATURAL
}

object ArgumentParser {
    private const val sortingTypeArg = "-sortingType"
    private const val dataTypeArg = "-dataType"

    private val defaultOperation = Operation.READ_WORDS
    private val defaultSortingType = SortingType.NATURAL

    fun parseArguments(args: Array<String>): Pair<Operation, SortingType> {
        var operation = defaultOperation
        var sortingType = defaultSortingType

        if (args.size == 2) {
            if (args[0] == sortingTypeArg) {
                sortingType = parseSortingType(args[1])
            } else if (args[0] == dataTypeArg) {
                operation = parseOperation(args[1])
            }
        } else if (args[0] == sortingTypeArg && args[2] == dataTypeArg) {
            sortingType = parseSortingType(args[1])
            operation = parseOperation(args[3])
        } else if (args[0] == dataTypeArg && args[2] == sortingTypeArg) {
            operation = parseOperation(args[1])
            sortingType = parseSortingType(args[3])
        }
        return operation to sortingType
    }

    private fun parseOperation(op: String): Operation {
        return when (op) {
            "long" -> Operation.READ_LONGS
            "line" -> Operation.READ_LINES
            else -> Operation.READ_WORDS
        }
    }

    private fun parseSortingType(st: String): SortingType {
        return if (st == "byCount") SortingType.COUNT else SortingType.NATURAL
    }
}

object InputReader {
    private val scanner = Scanner(System.`in`)

    fun readNextInt() = scanner.nextInt()

    fun readNextLong() = scanner.nextLong()

    fun readNextWord(): String = scanner.next()

    fun readNextLine(): String = scanner.nextLine()

    fun hasNext() = scanner.hasNext()
}

abstract class Reader<T : Comparable<T>>(private val sortingType: SortingType) {
    val elements = mutableMapOf<T, Int>()

    abstract fun readInput(): T

    open fun printSortedNatural() {
        val sortedData = mutableListOf<T>()
        for ((element, count) in elements) {
            repeat(count) {
                sortedData.add(element)
            }
        }
        println("Sorted data: ${sortedData.joinToString(" ")}")
    }

    fun run() {
        while (InputReader.hasNext()) {
            val element = readInput()
            elements.putIfAbsent(element, 0)
            elements[element] = elements[element]!!.inc()
        }

        val totalNumbers = findTotalElements()
        println("Total numbers: $totalNumbers.")

        if (sortingType == SortingType.COUNT) {
            sortByCount()
            printSortedByCount()
        } else {
            sortNatural()
            printSortedNatural()
        }
    }

    private fun printSortedByCount() {
        val totalNumbers = findTotalElements()
        for ((element, count) in elements) {
            val percentage = (count * 100 / totalNumbers.toDouble()).roundToInt()
            println("$element: $count time(s), $percentage%")
        }
    }

    private fun sortNatural() {
        val sorted = elements.toSortedMap()
        elements.clear()
        elements.putAll(sorted)
    }

    private fun sortByCount() {
        val sorted = elements.toList()
            .sortedWith(compareBy<Pair<T, Int>> { (_, value) -> value }
                .thenBy { (key, _) -> key })
            .toMap()
        elements.clear()
        elements.putAll(sorted)
    }

    private fun findTotalElements(): Int = elements.values.sum()
}

class LongReader(sortingType: SortingType) : Reader<Long>(sortingType) {
    override fun readInput() = InputReader.readNextLong()
}

class LineReader(sortingType: SortingType) : Reader<String>(sortingType) {
    override fun readInput() = InputReader.readNextLine()

    override fun printSortedNatural() {
        println("Sorted data:")
        for (element in elements.keys) {
            println(element)
        }
    }
}

class WordReader(sortingType: SortingType) : Reader<String>(sortingType) {
    override fun readInput() = InputReader.readNextWord()
}

fun main(args: Array<String>) {
    val (operation, sortingType) = ArgumentParser.parseArguments(args)
    when (operation) {
        Operation.READ_WORDS -> WordReader(sortingType).run()
        Operation.READ_LINES -> LineReader(sortingType).run()
        Operation.READ_LONGS -> LongReader(sortingType).run()
    }
}
