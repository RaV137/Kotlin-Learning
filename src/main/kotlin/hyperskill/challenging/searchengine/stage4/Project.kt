package hyperskill.challenging.searchengine.stage4

import java.io.File

class DataHolder {
    val data = mutableListOf<String>()

    fun add(str: String) {
        data.add(str)
    }

    fun add(strings: List<String>) {
        data.addAll(strings)
    }

    fun findAll(queryString: String): List<String> {
        val tmpList = mutableListOf<String>()
        val query = queryString.trim().lowercase()
        val queryRegex = ".*$query.*".toRegex()
        data.forEach {
            if (it.trim().lowercase().matches(queryRegex))
                tmpList.add(it)
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
    val dataHolder = DataHolder()
    val input = readInputData(args)
    dataHolder.add(input)

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