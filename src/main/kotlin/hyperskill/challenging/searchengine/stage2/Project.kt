package hyperskill.challenging.searchengine.stage2

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
}

fun main() {
    val dataHolder = DataHolder()
    val input = readInputData()
    dataHolder.add(input)
    searchInData(dataHolder)
}

fun readInputData(): List<String> {
    println("Enter the number of players")
    val numOfEntries = readLine()!!.toInt()
    return List(numOfEntries) { readLine()!! }
}

fun searchInData(dataHolder: DataHolder) {
    println("\nEnter the number of search queries:")
    val numOfQueries = readLine()!!.toInt()

    repeat(numOfQueries) {
        println("\nEnter data to search players")
        val query = readLine()!!
        val foundData = dataHolder.findAll(query)
        if (foundData.isEmpty()) {
            println("No matching players found.")
        } else {
            println("\nPlayers found:")
            println(foundData.joinToString("\n"))
        }
    }
}
