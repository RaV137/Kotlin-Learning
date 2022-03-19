package hyperskill.hard.sortingtool.stage5

import java.lang.Exception
import java.util.*
import kotlin.math.roundToInt

// enums

enum class Operation {
    READ_WORDS,
    READ_LINES,
    READ_LONGS,
}

enum class SortingType {
    COUNT, NATURAL
}

// objects

object ArgumentParser {
    private const val sortingTypeArg = "-sortingType"
    private const val dataTypeArg = "-dataType"

    private val defaultOperation = Operation.READ_WORDS
    private val defaultSortingType = SortingType.NATURAL

    fun parseArguments(args: Array<String>): Pair<Operation, SortingType> {
        var operation = defaultOperation
        var sortingType = defaultSortingType

        val indexOfSortingTypeArg = args.indexOf(sortingTypeArg)
        val indexOfDataTypeArg = args.indexOf(dataTypeArg)

        if (indexOfSortingTypeArg >= 0) {
            try {
                sortingType = parseSortingType(args[indexOfSortingTypeArg + 1])
            } catch (e: Exception) {
                throw ArgumentParsingException("No sorting type defined!")
            }
        }

        if (indexOfDataTypeArg >= 0) {
            try {
                operation = parseOperation(args[indexOfDataTypeArg + 1])
            } catch (e: Exception) {
                throw ArgumentParsingException("No data type defined!")
            }
        }

        for (arg in args) {
            if (arg.startsWith("-") && arg != sortingTypeArg && arg != dataTypeArg) {
                println("\"$arg\" is not a valid parameter. It will be skipped.")
            }
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

    fun readNextLong(): Long {
        var nextValue = ""
        try {
            nextValue = scanner.next()
            return nextValue.toLong()
        } catch (e: NumberFormatException) {
            throw InvalidElementException("\"$nextValue\" is not a long. It will be skipped.")
        }
    }

    fun readNextWord(): String = scanner.next()

    fun readNextLine(): String = scanner.nextLine()

    fun hasNext() = scanner.hasNext()
}

// exceptions

class ArgumentParsingException(message: String) : RuntimeException(message)
class InvalidElementException(message: String) : RuntimeException(message)

// classes

abstract class Reader<T : Comparable<T>>(private val sortingType: SortingType) {
    val elements = mutableMapOf<T, Int>()

    abstract fun readInput(): T

    fun run() {
        while (InputReader.hasNext()) {
            try {
                val element = readInput()
                elements.putIfAbsent(element, 0)
                elements[element] = elements[element]!!.inc()
            } catch (e: InvalidElementException) {
                println(e.message)
                continue
            }
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

    open fun printSortedNatural() {
        val sortedData = mutableListOf<T>()
        for ((element, count) in elements) {
            repeat(count) {
                sortedData.add(element)
            }
        }
        println("Sorted data: ${sortedData.joinToString(" ")}")
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

// main

fun main(args: Array<String>) {
    try {
        val (operation, sortingType) = ArgumentParser.parseArguments(args)
        when (operation) {
            Operation.READ_WORDS -> WordReader(sortingType).run()
            Operation.READ_LINES -> LineReader(sortingType).run()
            Operation.READ_LONGS -> LongReader(sortingType).run()
        }
    } catch (e: ArgumentParsingException) {
        println(e.message)
    }

}
