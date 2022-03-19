package hyperskill.challenging.searchengine.stage5

import java.io.File

class DataHolder(private val data: List<String>) {
    private val invertedIndex = mutableMapOf<String, MutableSet<Int>>()

    init {
        repeat(data.size) { idx ->
            val splittedLine = data[idx].split(" ")
            splittedLine.forEach {
                val str = it.trim().lowercase()
                invertedIndex.putIfAbsent(str, mutableSetOf())
                invertedIndex[str]!!.add(idx)
            }
        }
    }

    fun findAll(queryString: String): List<String> {
        val tmpList = mutableListOf<String>()
        val query = queryString.trim().lowercase()
        (invertedIndex[query] ?: emptySet()).forEach {
            tmpList.add(data[it])
        }
        return tmpList
    }

    fun printAll() {
        println("\n=== List of players ===")
        println(data.joinToString("\n"))
    }
}

data class Arguments(val inputFilename: String)

object ArgumentParser {
    private const val IMPORT_ARGUMENT = "--data"

    fun parseArguments(args: Array<String>): Arguments {
        try {
            val inputFilename = args[1]
            return Arguments(inputFilename)
        } catch (e: IndexOutOfBoundsException) {
            throw IllegalArgumentException(e)
        }
    }
}

fun main(args: Array<String>) {
    val input = readInputData(args)
    val dataHolder = DataHolder(input)

    while (true) {
        println(
            """
            
            === Menu ===
            1. Find a person
            2. Print all people
            0. Exit
            
        """.trimIndent()
        )

        when (readLine()!!.toInt()) {
            0 -> {
                println("Bye!")
                return
            }
            1 -> searchInData(dataHolder)
            2 -> dataHolder.printAll()
            else -> println("Incorrect option! Try again.")
        }
    }
}

fun readInputData(args: Array<String>): List<String> {
    val inputFilename = ArgumentParser.parseArguments(args).inputFilename
    val file = File(inputFilename)
    return file.readLines()
}

fun searchInData(dataHolder: DataHolder) {
    println("\nEnter a name or position to search all suitable players")
    val query = readLine()!!
    val foundData = dataHolder.findAll(query)
    if (foundData.isEmpty()) {
        println("No matching players found.")
    } else {
        println(foundData.joinToString("\n"))
    }
}