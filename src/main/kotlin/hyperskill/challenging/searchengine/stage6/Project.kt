package hyperskill.challenging.searchengine.stage6

import java.io.File

enum class SearchStrategy {
    ALL, ANY, NONE
}

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

    fun findAll(queryString: String, searchStrategy: SearchStrategy): Set<String> {
        val tmpSet = mutableSetOf<String>()
        val queryWordList = queryString.trim().lowercase().split(" ")

        if (searchStrategy == SearchStrategy.ALL) {
            val tmpMap = mutableMapOf<Int, Int>()
            queryWordList.forEach { query ->
                (invertedIndex[query] ?: emptySet()).forEach {
                    tmpMap.putIfAbsent(it, 0)
                    tmpMap[it] = tmpMap[it]!! + 1
                }
            }

            tmpMap.filter { it.value == queryWordList.size }.forEach {
                tmpSet.add(data[it.key])
            }

            return tmpSet
        } else {
            queryWordList.forEach { query ->
                (invertedIndex[query] ?: emptySet()).forEach {
                    tmpSet.add(data[it])
                }
            }

            return if (searchStrategy == SearchStrategy.ANY) {
                tmpSet
            } else {
                data.toMutableSet().apply { removeAll(tmpSet) }
            }
        }
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
    println("Select a matching strategy: ALL, ANY, NONE")
    val strategy = readLine()!!
    val searchStrategy = SearchStrategy.valueOf(strategy)

    println("\nEnter a name or position to search all suitable players")
    val query = readLine()!!

    val foundData = dataHolder.findAll(query, searchStrategy)
    if (foundData.isEmpty()) {
        println("No matching players found.")
    } else {
        println(foundData.joinToString("\n"))
    }
}
