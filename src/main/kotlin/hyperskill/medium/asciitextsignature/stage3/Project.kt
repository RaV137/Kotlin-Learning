package hyperskill.medium.asciitextsignature.stage3

data class Letter(val firstLine: String, val secondLine: String, val thirdLine: String) {
    val width = firstLine.length
}

val letters = mapOf(
    'a' to Letter("____", "|__|", "|  |"),
    'b' to Letter("___ ", "|__]", "|__]"),
    'c' to Letter("____", "|   ", "|___"),
    'd' to Letter("___ ", "|  \\", "|__/"),
    'e' to Letter("____", "|___", "|___"),
    'f' to Letter("____", "|___", "|   "),
    'g' to Letter("____", "| __", "|__]"),
    'h' to Letter("_  _", "|__|", "|  |"),
    'i' to Letter("_", "|", "|"),
    'j' to Letter(" _", " |", "_|"),
    'k' to Letter("_  _", "|_/ ", "| \\_"),
    'l' to Letter("_   ", "|   ", "|___"),
    'm' to Letter("_  _", "|\\/|", "|  |"),
    'n' to Letter("_  _", "|\\ |", "| \\|"),
    'o' to Letter("____", "|  |", "|__|"),
    'p' to Letter("___ ", "|__]", "|   "),
    'q' to Letter("____", "|  |", "|_\\|"),
    'r' to Letter("____", "|__/", "|  \\"),
    's' to Letter("____", "[__ ", "___]"),
    't' to Letter("___", " | ", " | "),
    'u' to Letter("_  _", "|  |", "|__|"),
    'v' to Letter("_  _", "|  |", " \\/ "),
    'w' to Letter("_ _ _", "| | |", "|_|_|"),
    'x' to Letter("_  _", " \\/ ", "_/\\_"),
    'y' to Letter("_   _", " \\_/ ", "  |  "),
    'z' to Letter("___ ", "  / ", " /__"),
    ' ' to Letter("    ", "    ", "    ")
)

fun main() {
    val name = readName()
    val status = readStatus()
    printTag(name, status)
}

fun readName(): String {
    print("Enter the first and last name: ")
    return readLine()!!.lowercase()
}

fun readStatus(): String {
    print("Enter the person's status: ")
    return readLine()!!
}

fun printTag(name: String, status: String) {
    val nameLetters = name.map { letters[it]!! }
    val nameTotalLength = nameLetters.sumOf { it.width } + name.length - 1
    val totalWidth = if (nameTotalLength > status.length) nameTotalLength else status.length

    println("*".repeat(totalWidth + 6))
    printName(nameLetters, nameTotalLength, status.length)
    printStatus(status, totalWidth)
    println("*".repeat(totalWidth + 6))
}

fun printStatus(status: String, totalWidth: Int) {
    val statusSpacesLengthLeft = (totalWidth + 4 - status.length) / 2
    val statusSpacesLengthRight = totalWidth + 4 - status.length - statusSpacesLengthLeft
    println("*${" ".repeat(statusSpacesLengthLeft)}$status${" ".repeat(statusSpacesLengthRight)}*")
}


fun printName(nameLetters: List<Letter>, nameTotalLength: Int, statusLength: Int) {
    val nameFirstLine = nameLetters.joinToString(" ") { it.firstLine }
    val nameSecondLine = nameLetters.joinToString(" ") { it.secondLine }
    val nameThirdLine = nameLetters.joinToString(" ") { it.thirdLine }

    if (nameTotalLength > statusLength) {
        println("*  $nameFirstLine  *")
        println("*  $nameSecondLine  *")
        println("*  $nameThirdLine  *")
    } else {
        val nameSpacesLengthLeft = (statusLength + 4 - nameTotalLength) / 2
        val nameSpacesLengthRight = statusLength + 4 - nameTotalLength - nameSpacesLengthLeft
        println("*${" ".repeat(nameSpacesLengthLeft)}$nameFirstLine${" ".repeat(nameSpacesLengthRight)}*")
        println("*${" ".repeat(nameSpacesLengthLeft)}$nameSecondLine${" ".repeat(nameSpacesLengthRight)}*")
        println("*${" ".repeat(nameSpacesLengthLeft)}$nameThirdLine${" ".repeat(nameSpacesLengthRight)}*")
    }
}
