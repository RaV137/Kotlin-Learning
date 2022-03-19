package hyperskill.medium.phonebook.stage1

import java.io.File

fun main() {
    val phoneBookFilename = "C:\\Users\\TC99GJ\\Projects\\kotlin_learining_project\\src\\main\\resources\\phoneBook\\stage1\\directory.txt"
    val queriesFilename = "C:\\Users\\TC99GJ\\Projects\\kotlin_learining_project\\src\\main\\resources\\phoneBook\\stage1\\find.txt"

    val phonebookLines = loadLinesFromFile(phoneBookFilename)
    val queryLines = loadLinesFromFile(queriesFilename)

    val startMillis = System.currentTimeMillis()
    println("Start searching...")
    val foundCount = search(phonebookLines, queryLines)
    val stopMillis = System.currentTimeMillis()
    val timeTaken = stopMillis - startMillis
    print("Found $foundCount / ${queryLines.size} entries. Time taken: ")
    print(String.format("%1\$tM min. %1\$tS sec. %1\$tL ms.", timeTaken))
}

fun loadLinesFromFile(filename: String): List<String> {
    val file = File(filename)
    return file.readLines()
}

fun search(phoneBook: List<String>, queries: List<String>): Int {
    var foundCount = 0
    for (query in queries) {
        if (phoneBook.find { it.contains(query) } != null) {
            foundCount++
        }
    }
    return foundCount
}
