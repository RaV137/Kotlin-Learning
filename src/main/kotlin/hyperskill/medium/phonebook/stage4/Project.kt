package hyperskill.medium.phonebook.stage4

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

    val linearSearchTimeTaken = performLinearSearch(phonebookLines, queryLines)
    println()
    performBubbleSortAndJumpSearch(phonebookLines, queryLines, linearSearchTimeTaken)
    println()
    performQuickSortAndBinarySearch(phonebookLines, queryLines)
    println()
    performHashTableSearch(phonebookLines, queryLines)
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

fun performLinearSearch(phonebookLines: List<String>, queryLines: List<String>): Long {
    println("Start searching (linear search)...")
    val (linearFoundCount, linearSearchTimeTaken) = linearSearch(phonebookLines, queryLines)
    println("Found $linearFoundCount / ${queryLines.size} entries. Time taken: ${getTimeTakenInfo(linearSearchTimeTaken)}")
    return linearSearchTimeTaken
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

fun performBubbleSortAndJumpSearch(
    phonebookLines: List<String>,
    queryLines: List<String>,
    linearSearchTimeTaken: Long
) {
    println("Start searching (bubble sort + jump search)...")
    try {
        val (sortedPhoneBook, sortingTimeTaken) = bubbleSort(phonebookLines, linearSearchTimeTaken)
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

fun bubbleSort(phoneBook: List<String>, linearSearchTimeTaken: Long): Pair<List<String>, Long> {
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
    queriesLoop@ for (query in queries) {
        var i = 0
        while (i < phoneBookSize) {
            if (sortedPhoneBook[i].contains(query)) {
                foundCount++
                continue@queriesLoop
            } else if (sortedPhoneBook[i] > query) {
                var idx = i - 1
                while (idx > i - stepSize && idx >= 0) {
                    if (sortedPhoneBook[idx].contains(query)) {
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
                continue@queriesLoop
            }
            idx--
        }
    }

    val jumpSearchStopMillis = System.currentTimeMillis()
    val jumpSearchTimeTaken = jumpSearchStopMillis - jumpSearchStartMillis
    return foundCount to jumpSearchTimeTaken
}

fun performQuickSortAndBinarySearch(phonebookLines: List<String>, queryLines: List<String>) {
    println("Start searching (quick sort + binary search)...")
    val (sortedPhoneBook, sortingTimeTaken) = quickSort(phonebookLines)
    val (binaryFoundCount, binarySearchTimeTaken) = binarySearch(sortedPhoneBook, queryLines)
    val totalTimeTaken = sortingTimeTaken + binarySearchTimeTaken
    println("Found $binaryFoundCount / ${queryLines.size} entries. Time taken: ${getTimeTakenInfo(totalTimeTaken)}")
    println("Sorting time: ${getTimeTakenInfo(sortingTimeTaken)}")
    println("Searching time: ${getTimeTakenInfo(binarySearchTimeTaken)}")
}

fun quickSort(phoneBook: List<String>): Pair<List<String>, Long> {
    val sortingStartMillis = System.currentTimeMillis()

    val sortedPhoneBook = quickSortRecursive(phoneBook)

    val sortingStopMillis = System.currentTimeMillis()
    val sortingTimeTaken = sortingStopMillis - sortingStartMillis
    return sortedPhoneBook to sortingTimeTaken
}

fun quickSortRecursive(phoneBook: List<String>): List<String> {
    if (phoneBook.size < 2) {
        return phoneBook
    }

    val pivot = findMedian(phoneBook)
    val equal = phoneBook.filter { it == pivot }
    val lessItems = phoneBook.filter { it < pivot }
    val greaterItems = phoneBook.filter { it > pivot }

    return quickSortRecursive(lessItems) + equal + quickSortRecursive(greaterItems)
}

fun findMedian(phoneBook: List<String>): String {
    val first = phoneBook.first()
    val last = phoneBook.last()
    val middle = phoneBook[phoneBook.size / 2]

    return if (first in last..middle || first in middle..last) {
        first
    } else if (last in first..middle || last in middle..first) {
        last
    } else {
        middle
    }
}

fun binarySearch(sortedPhoneBook: List<String>, queries: List<String>): Pair<Int, Long> {
    val binarySearchStartMillis = System.currentTimeMillis()
    var foundCount = 0

    queriesLoop@ for (query in queries) {
        var left = 0
        var right = sortedPhoneBook.lastIndex

        while (left <= right) {
            val middle = (left + right) / 2
            val middleElement = sortedPhoneBook[middle]
            if (middleElement.contains(query)) {
                foundCount++
                continue@queriesLoop
            }

            if (middleElement > query) {
                right = middle - 1
            } else {
                left = middle + 1
            }
        }
    }

    val binarySearchStopMillis = System.currentTimeMillis()
    val binarySearchTimeTaken = binarySearchStopMillis - binarySearchStartMillis
    return foundCount to binarySearchTimeTaken
}

fun performHashTableSearch(phonebookLines: List<String>, queryLines: List<String>) {
    println("Start searching (hash table)...")
    val (hashedPhoneBook, hashingTimeTaken) = hashTablePreparation(phonebookLines)
    val (binaryFoundCount, hashSearchTimeTaken) = hashTableSearch(hashedPhoneBook, queryLines)
    val totalTimeTaken = hashingTimeTaken + hashSearchTimeTaken
    println("Found $binaryFoundCount / ${queryLines.size} entries. Time taken: ${getTimeTakenInfo(totalTimeTaken)}")
    println("Creating time: ${getTimeTakenInfo(hashingTimeTaken)}")
    println("Searching time: ${getTimeTakenInfo(hashSearchTimeTaken)}")
}

fun hashTablePreparation(phoneBook: List<String>): Pair<Map<Int, String>, Long> {
    val hashingStartMillis = System.currentTimeMillis()

    val hashedPhoneBook = HashMap<Int, String>(phoneBook.size)

    for (line in phoneBook) {
        val splitted = line.split(" ")
        val name = if (splitted.size == 2) splitted[0] else splitted[0] + " " + splitted[1]
        hashedPhoneBook[hashFunction(name)] = line
    }

    val hashingStopMillis = System.currentTimeMillis()
    val hashingTimeTaken = hashingStopMillis - hashingStartMillis
    return hashedPhoneBook to hashingTimeTaken
}

fun hashFunction(string: String): Int {
    return string.hashCode()
}

fun hashTableSearch(phoneBook: Map<Int, String>, queryLines: List<String>): Pair<Int, Long> {
    val hashingSearchStartMillis = System.currentTimeMillis()
    var foundCount = 0

    for (query in queryLines) {
        if (phoneBook.containsKey(hashFunction(query))) {
            foundCount++
        }
    }

    val hashingSearchStopMillis = System.currentTimeMillis()
    val hashingSearchTimeTaken = hashingSearchStopMillis - hashingSearchStartMillis
    return foundCount to hashingSearchTimeTaken
}