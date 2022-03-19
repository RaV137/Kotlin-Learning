package hyperskill.hard.sortingtool.stage6

import java.io.File
import java.io.FileInputStream
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

// argument parsing

data class Arguments(
    val operation: Operation,
    val sortingType: SortingType,
    val inputFile: String,
    val outputFile: String
)

object ArgumentParser {
    private const val sortingTypeArg = "-sortingType"
    private const val dataTypeArg = "-dataType"
    private const val inputFileArg = "-inputFile"
    private const val outputFileArg = "-outputFile"

    private val validArguments = listOf(sortingTypeArg, dataTypeArg, inputFileArg, outputFileArg)
    private val defaultOperation = Operation.READ_WORDS
    private val defaultSortingType = SortingType.NATURAL

    fun parseArguments(args: Array<String>): Arguments {
        var operation = defaultOperation
        var sortingType = defaultSortingType
        var inputFile = ""
        var outputFile = ""

        val indexOfSortingTypeArg = args.indexOf(sortingTypeArg)
        if (indexOfSortingTypeArg >= 0) {
            try {
                sortingType = parseSortingType(args[indexOfSortingTypeArg + 1])
            } catch (e: Exception) {
                throw ArgumentParsingException("No sorting type defined!")
            }
        }

        val indexOfDataTypeArg = args.indexOf(dataTypeArg)
        if (indexOfDataTypeArg >= 0) {
            try {
                operation = parseOperation(args[indexOfDataTypeArg + 1])
            } catch (e: Exception) {
                throw ArgumentParsingException("No data type defined!")
            }
        }

        val indexOfInputFile = args.indexOf(inputFileArg)
        if (indexOfInputFile >= 0) {
            inputFile = args[indexOfInputFile + 1]
        }

        val indexOfOutputFile = args.indexOf(outputFileArg)
        if (indexOfOutputFile >= 0) {
            outputFile = args[indexOfOutputFile + 1]
        }

        for (arg in args) {
            if (arg.startsWith("-") && arg !in validArguments) {
                println("\"$arg\" is not a valid parameter. It will be skipped.")
            }
        }
        return Arguments(operation, sortingType, inputFile, outputFile)
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

// exceptions

class ArgumentParsingException(message: String) : RuntimeException(message)
class InvalidElementException(message: String) : RuntimeException(message)

// IO

abstract class InputReader {
    abstract fun retrieveScanner(): Scanner

    fun readNextLong(): Long {
        var nextValue = ""
        try {
            nextValue = retrieveScanner().next()
            return nextValue.toLong()
        } catch (e: NumberFormatException) {
            throw InvalidElementException("\"$nextValue\" is not a long. It will be skipped.")
        }
    }

    fun readNextWord(): String = retrieveScanner().next()

    fun readNextLine(): String = retrieveScanner().nextLine()

    fun hasNext() = retrieveScanner().hasNext()

    fun close() {
        retrieveScanner().close()
    }
}

class CLIInputReader : InputReader() {
    private val scanner = Scanner(System.`in`)

    override fun retrieveScanner(): Scanner = scanner
}

class FileInputReader(inputFile: String) : InputReader() {
    private val scanner = Scanner(FileInputStream(File(inputFile)))

    override fun retrieveScanner(): Scanner = scanner
}

interface OutputWriter {
    fun printLine(str: String)
}

class CLIOutputWriter : OutputWriter {
    override fun printLine(str: String) {
        println(str)
    }
}

class FileOutputWriter(outputFile: String) : OutputWriter {
    private val file = File(outputFile)

    override fun printLine(str: String) {
        if (file.exists()) {
            file.appendText(str)
        } else {
            file.writeText(str)
        }
    }
}

// readers

abstract class Reader<T : Comparable<T>>(
    val inputReader: InputReader,
    val outputWriter: OutputWriter,
    private val sortingType: SortingType) {
    val elements = mutableMapOf<T, Int>()

    abstract fun readInput(): T

    fun run() {
        while (inputReader.hasNext()) {
            try {
                val element = readInput()
                elements.putIfAbsent(element, 0)
                elements[element] = elements[element]!!.inc()
            } catch (e: InvalidElementException) {
                println(e.message)
                continue
            }
        }
        inputReader.close()

        val totalNumbers = findTotalElements()
        outputWriter.printLine("Total numbers: $totalNumbers.")

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
        outputWriter.printLine("Sorted data: ${sortedData.joinToString(" ")}")
    }

    private fun printSortedByCount() {
        val totalNumbers = findTotalElements()
        for ((element, count) in elements) {
            val percentage = (count * 100 / totalNumbers.toDouble()).roundToInt()
            outputWriter.printLine("$element: $count time(s), $percentage%")
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

class LongReader(inputReader: InputReader, outputWriter: OutputWriter, sortingType: SortingType)
    : Reader<Long>(inputReader, outputWriter, sortingType) {
    override fun readInput() = inputReader.readNextLong()
}

class LineReader(inputReader: InputReader, outputWriter: OutputWriter, sortingType: SortingType)
    : Reader<String>(inputReader, outputWriter, sortingType) {
    override fun readInput() = inputReader.readNextLine()

    override fun printSortedNatural() {
        outputWriter.printLine("Sorted data:")
        for (element in elements.keys) {
            println(element)
        }
    }
}

class WordReader(inputReader: InputReader, outputWriter: OutputWriter, sortingType: SortingType)
    : Reader<String>(inputReader, outputWriter, sortingType) {
    override fun readInput() = inputReader.readNextWord()
}

// main

fun main(args: Array<String>) {
    try {
        val arguments = ArgumentParser.parseArguments(args)
        val inputReader = createInputReader(arguments.inputFile)
        val outputWriter = createOutputReader(arguments.outputFile)
        when (arguments.operation) {
            Operation.READ_WORDS -> WordReader(inputReader, outputWriter, arguments.sortingType).run()
            Operation.READ_LINES -> LineReader(inputReader, outputWriter, arguments.sortingType).run()
            Operation.READ_LONGS -> LongReader(inputReader, outputWriter, arguments.sortingType).run()
        }
    } catch (e: ArgumentParsingException) {
        println(e.message)
    }
}

fun createInputReader(inputFile: String): InputReader {
    return if (inputFile == "") CLIInputReader() else FileInputReader(inputFile)
}

fun createOutputReader(outputFile: String): OutputWriter {
    return if (outputFile == "") CLIOutputWriter() else FileOutputWriter(outputFile)
}
