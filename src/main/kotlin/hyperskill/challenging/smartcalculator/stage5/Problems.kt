package hyperskill.challenging.smartcalculator.stage5

fun main() {
    problem5()
}

fun problem1() {
    val text = readLine()!!
    val replacedText = text.replace("[aA]+".toRegex(), "a")
    println(replacedText)
}

fun problem2() {
    val text = readLine()!!
    val regexColors = "#[0-9a-fA-F]{6}\\b".toRegex()
    val results = regexColors.findAll(text)
    results.forEach { println(it.value) }
}

fun problem3() {
    val string = readLine()!!
    val n = readLine()!!.toInt()
    string.split("\\s+".toRegex(), n).forEach(::println)
}

fun problem4() {
    val text = readLine()!!
    val basicChordsRegex = "[AEDGC]m? ".toRegex()
    val onlyLyrics = text.replace(basicChordsRegex, "")
    println(onlyLyrics)
}

fun problem5() {
    println(prepareInput(readLine()!!).joinToString(", "))
}

fun prepareInput(input: String): List<String> {
    val newValue = input.replace("\\s+".toRegex(), "")
        .replace("\\++".toRegex(), " + ")
        .replace("(?<=\\w)-(?=\\w)".toRegex(), " - ")
        .replace("*", " * ")
        .replace("/", " / ")
        .replace("^", " ^ ")
        .replace("(", " ( ")
        .replace(")", " ) ")

    val minusResults = "-{2,}".toRegex().findAll(newValue)
    var finalValue = newValue

    for (result in minusResults) {
        val replacement = if (result.value.length % 2 == 0) " + " else " - "
        finalValue = finalValue.replaceFirst(result.value, replacement)
    }

    return finalValue.trim().split("\\s+".toRegex())
}

fun problem6() {
    val operatorRegex = """(?<=[\(\+\*\/\^])-\d+|(?<!(\)|\w))-\d+|\w+|([\+\-\*\/\^])|([\)\(])""".toRegex()
    val input = readLine()!!
    println(operatorRegex.findAll(input).map { it.value }.joinToString(", "))
}
