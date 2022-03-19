package hyperskill.medium.phonebook.stage2

import java.io.File
import kotlin.math.floor
import kotlin.math.sqrt

class SortingTimeTooLongException(val timeTaken: Long) : RuntimeException()

fun main() {
    val phoneBookFilename =
        "C:\\Users\\TC99GJ\\Projects\\kotlin_learining_project\\src\\main\\resources\\phoneBook\\stage1\\directory.txt"
    val queriesFilename =
        "C:\\Users\\TC99GJ\\Projects\\kotlin_learining_project\\src\\main\\resources\\phoneBook\\stage1\\find.txt"

    val phonebookLines = loadPhoneBook(phoneBookFilename)
    val queryLines = loadLinesFromFile(queriesFilename)

    println("Start searching (linear search)...")
    val (linearFoundCount, linearSearchTimeTaken) = linearSearch(phonebookLines, queryLines)
    println("Found $linearFoundCount / ${queryLines.size} entries. Time taken: ${getTimeTakenInfo(linearSearchTimeTaken)}")

    println()

    println("Start searching (bubble sort + jump search)...")
    try {
        val (sortedPhoneBook, sortingTimeTaken) = sortPhoneBook(phonebookLines, linearSearchTimeTaken)
        val (jumpFoundCount, jumpSearchTimeTaken) = jumpSearch(sortedPhoneBook, queryLines)
        val totalTimeTaken = sortingTimeTaken + jumpSearchTimeTaken
        println("Found $jumpFoundCount / ${queryLines.size} entries. Time taken: ${getTimeTakenInfo(totalTimeTaken)}")
        println("Sorting time: ${getTimeTakenInfo(sortingTimeTaken)}")
        println("Searching time: ${getTimeTakenInfo(jumpSearchTimeTaken)}")
    } catch (e: SortingTimeTooLongException) {
        val (againLinearFoundCount, againLinearSearchTimeTaken) = linearSearch(phonebookLines, queryLines)
        printInfo(againLinearFoundCount, queryLines.size, againLinearSearchTimeTaken + e.timeTaken)
        println("Sorting time: ${getTimeTakenInfo(e.timeTaken)} - STOPPED, moved to linear search")
        println("Searching time: ${getTimeTakenInfo(againLinearSearchTimeTaken)}")
    }
}

fun loadPhoneBook(phoneBookFilename: String): List<String> {
    return loadLinesFromFile(phoneBookFilename)
        .map { line ->
            val splitted = line.split(" ".toRegex(), 2)
            splitted[1] + " " + splitted[0]
        }
}

fun loadLinesFromFile(filename: String): List<String> {
    val file = File(filename)
    return file.readLines()
}

fun linearSearch(phoneBook: List<String>, queries: List<String>): Pair<Int, Long> {
    val linearSearchStartMillis = System.currentTimeMillis()

    var foundCount = 0
    for (query in queries) {
        if (phoneBook.find { it.contains(query) } != null) {
            foundCount++
        }
    }

    val linearSearchStopMillis = System.currentTimeMillis()
    val linearSearchTimeTaken = linearSearchStopMillis - linearSearchStartMillis

    return foundCount to linearSearchTimeTaken
}

fun printInfo(foundSize: Int, querySize: Int, timeTaken: Long) {
    println("Found $foundSize / $querySize entries. Time taken: ${getTimeTakenInfo(timeTaken)}")
}

fun getTimeTakenInfo(millis: Long): String = String.format("%1\$tM min. %1\$tS sec. %1\$tL ms.", millis)

fun sortPhoneBook(phoneBook: List<String>, linearSearchTimeTaken: Long): Pair<List<String>, Long> {
    val maxSortingTime = linearSearchTimeTaken * 10
    val sortedPhoneBook = phoneBook.toMutableList()
    val sortingStartMillis = System.currentTimeMillis()

    val phoneBookSize = sortedPhoneBook.size
    var swapsCount = 0
    for (i in 0 until phoneBookSize) {
        for (j in 1 until phoneBookSize) {
            val previous = sortedPhoneBook[j - 1]
            val curr = sortedPhoneBook[j]

            if (curr < previous) {
                sortedPhoneBook[j - 1] = curr
                sortedPhoneBook[j] = previous
                swapsCount++
            }
            val midSortingMillis = System.currentTimeMillis()
            val sortingMillis = midSortingMillis - sortingStartMillis
            if (sortingMillis > maxSortingTime) {
                throw SortingTimeTooLongException(sortingMillis)
            }
        }
        if (swapsCount == 0) {
            break // already sorted
        }
    }

    val sortingStopMillis = System.currentTimeMillis()
    val sortingTimeTaken = sortingStopMillis - sortingStartMillis
    return sortedPhoneBook to sortingTimeTaken
}

fun jumpSearch(sortedPhoneBook: List<String>, queries: List<String>): Pair<Int, Long> {
    val jumpSearchStartMillis = System.currentTimeMillis()

    val stepSize = floor(sqrt(sortedPhoneBook.size.toDouble())).toInt()
    val phoneBookSize = sortedPhoneBook.size

    var foundCount = 0
    queriesLoop@for (query in queries) {
        var i = 0
        while (i < phoneBookSize) {
            if (sortedPhoneBook[i].contains(query)) {
                foundCount++
                continue@queriesLoop
            } else if (sortedPhoneBook[i] > query) {
                var idx = i - 1
                while (idx > i - stepSize && idx >= 0) {
                    if (sortedPhoneBook[idx].contains(query)){
                        foundCount++
                        continue@queriesLoop
                    }
                    idx--
                }
            }

            i += stepSize
        }

        var idx = sortedPhoneBook.lastIndex
        while (idx > i - stepSize) {
            if (sortedPhoneBook[idx].contains(query)) {
                foundCount++
                continue
            }
            idx--
        }
    }

    val jumpSearchStopMillis = System.currentTimeMillis()
    val jumpSearchTimeTaken = jumpSearchStopMillis - jumpSearchStartMillis
    return foundCount to jumpSearchTimeTaken
}
