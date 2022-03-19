package hyperskill.medium.asciitextsignature.stage4

import java.io.File

const val BORDER = "8"

class Letter(val height: Int, val width: Int, val lines: List<String>)

val SPACE_LETTER_ROMAN = Letter(10, 10, List(10) { " ".repeat(10) })
val SPACE_LETTER_MEDIUM = Letter(3, 5, List(3) { " ".repeat(5) })

fun main() {
    val name = convertNameToLetters(readName(), readRomanLetters())
    val status = convertStatusToLetters(readStatus(), readMediumLetters())
    printCard(name, status)
}

fun readName(): String {
    print("Enter the first and last name: ")
    return readLine()!!
}

fun readStatus(): String {
    print("Enter the person's status: ")
    return readLine()!!
}

fun readMediumLetters(): Map<Char, Letter> {
    return readLetters("./src/main/resources/asciiProject/medium.txt")
}

fun readRomanLetters(): Map<Char, Letter> {
    return readLetters("./src/main/resources/asciiProject/roman.txt")
}

fun readLetters(filename: String): Map<Char, Letter> {
    val letters = mapOf<Char, Letter>().toMutableMap()
    val lines = File(filename).readLines()
    val (height, lettersCount) = lines[0].split(" ").map { it.toInt() }
    var index = 1
    repeat(lettersCount) {
        val (char, width) = lines[index++].split(" ")
        val letterLines = lines.subList(index, index + height)
        val letter = Letter(height, width.toInt(), letterLines)
        letters[char[0]] = letter
        index += height
    }
    return letters
}

fun convertNameToLetters(name: String, letters: Map<Char, Letter>): List<Letter> {
    return name.map { letters[it] ?: SPACE_LETTER_ROMAN }
}

fun convertStatusToLetters(status: String, letters: Map<Char, Letter>): List<Letter> {
    return status.map { letters[it] ?: SPACE_LETTER_MEDIUM }
}

fun printCard(name: List<Letter>, status: List<Letter>) {
    val nameLength = name.sumOf { it.width }
    val statusLength = status.sumOf { it.width }
    val nameLeftBorder: String
    val nameRightBorder: String
    val statusLeftBorder: String
    val statusRightBorder: String
    val totalWidth: Int
    if (nameLength > statusLength) {
        nameLeftBorder = "$BORDER$BORDER  "
        nameRightBorder = "  $BORDER$BORDER"
        val leftBorderLength = (nameLength - statusLength) / 2
        statusLeftBorder = "$BORDER$BORDER${" ".repeat(leftBorderLength + 2)}"
        statusRightBorder = "${" ".repeat((nameLength - statusLength) - leftBorderLength + 2)}$BORDER$BORDER"
        totalWidth = nameLength
    } else {
        val leftBorderLength = (statusLength - nameLength) / 2
        nameLeftBorder = "$BORDER$BORDER${" ".repeat(leftBorderLength + 2)}"
        nameRightBorder = "${" ".repeat((statusLength - nameLength) - leftBorderLength + 2)}$BORDER$BORDER"
        statusLeftBorder = "$BORDER$BORDER  "
        statusRightBorder = "  $BORDER$BORDER"
        totalWidth = statusLength
    }


    printHorizontalBorder(totalWidth + 8)
    printText(name, nameLeftBorder, nameRightBorder)
    printText(status, statusLeftBorder, statusRightBorder)
    printHorizontalBorder(totalWidth + 8)
}

fun printHorizontalBorder(width: Int) {
    println(BORDER.repeat(width))
}

fun printText(text: List<Letter>, leftBorder: String, rightBorder: String) {
    repeat(text[0].height) { i ->
        println("$leftBorder${text.joinToString("") { it.lines[i] }}$rightBorder")
    }
}
