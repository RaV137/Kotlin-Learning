package hyperskill.easy.cinemaroommanager.stage5

import java.io.IOException

fun main() {
    problem17()
}

fun problem1() = List(3) { readLine()!!.toDouble() }
    .let { (a, b, c) -> (c - b) / a }
    .let(::println)

const val PI = 3.1415
fun problem2() = readLine()!!.toInt()
    .let { r -> PI * r * r }
    .let(::println)

fun problem3() = List(2) { readLine()!!.toDouble() }
    .let { (a, b) -> b - a }
    .let(::println)

fun problem4() {
    val a = readLine()!!.toInt()
    val b = readLine()!!.toInt()
    if (b == 0) {
        println("Division by zero, please fix the second argument!")
    } else {
        println(a / b)
    }
}

// ########################

fun problem5() {
    println(parseCardNumber(readLine()!!))
}

const val CARD_PATTERN = "^\\d{4} \\d{4} \\d{4} \\d{4}$"
fun parseCardNumber(cardNumber: String): Long {
    if (cardNumber.matches(Regex(CARD_PATTERN))) {
        return cardNumber.replace(" ", "").toLong()
    } else {
        throw RuntimeException()
    }
}

// ########################

fun problem6() {
    val index = readLine()!!.toInt()
    val word = readLine()!!
    if (index !in 0..word.lastIndex) {
        println("There isn't such an element in the given string, please fix the index!")
    } else {
        println(word[index])
    }
}

// ########################

fun intDivision(x: String, y: String): Int {
    try {
        return x.toInt() / y.toInt()
    } catch (e: ArithmeticException) {
        println("Exception: division by zero!")
    } catch (e: NumberFormatException) {
        println("Read values are not integers.")
    }
    return 0
}

fun problem7() {
    val x = readLine()!!
    val y = readLine()!!
    println(intDivision(x, y))
}

// ########################

fun printFifthCharacter(input: String): String {
    return try {
        "The fifth character of the entered word is ${input[4]}"
    } catch (e: StringIndexOutOfBoundsException) {
        "The input word is too short!"
    }
}

fun problem8() {
    println(printFifthCharacter(readLine()!!))
}

// ########################

fun convertStringToDouble(input: String): Double {
    return try {
        input.toDouble()
    } catch (e: Exception) {
        0.0
    }
}

fun problem9() {
    println(convertStringToDouble(readLine()!!))
}

// ########################

fun pepTalk(name: String): String {
    val array = name.split(" ").toTypedArray()
    val firstName = array[0]
    val secondName = array[1]
    return "Don't lose the towel, traveler $firstName $secondName!"
}

fun problem10() {
    val name = readLine()!!
    val advice = try {
        pepTalk(name)
    } catch (e: Exception) {
        "Don't lose the towel, nameless one."
    } finally {
        println("Good luck!")
    }
    print(advice)
}

// ########################

fun calculateBrakingDistance(v1: String, a: String): Int {
    return try {
        v1.toInt().let { -(it * it) / (2 * a.toInt()) }
    } catch (e: NumberFormatException) {
        println(e.message)
        -1
    } catch (e: ArithmeticException) {
        println("The car does not slow down!")
        -1
    }
}

fun problem11() {
    println(calculateBrakingDistance(readLine()!!, readLine()!!))
}

// ########################

fun suspiciousFunction(param: Int) {
    when (param) {
        0 -> throw Exception("Some exceptions?")
        1 -> throw ArithmeticException("Division by zero")
        2 -> throw Exception("An exception occurred here")
        3 -> throw IOException()
    }
}

fun handleException(data: Int) {
    try {
        suspiciousFunction(data)
    } catch (e: IOException) {
        println("The IOException occurred")
    } catch (e: Exception) {
        println(e.message)
    } finally {
        println("Handling completed successfully!")
    }
}

fun problem12() {
    println(handleException(readLine()!!.toInt()))
}

// ########################

fun problem13() {
    for (i in 1..3) {
        if (i == 1)
            continue
        print(1)
        loop@ for (j in 1..2) {
            for (k in 1..2) {
                if (i == 2 || j == 3) break@loop
                print(2)
            }
            if (j == 1) return
            print(3)
        }
    }
}

fun problem14() = readLine()!!.find { it.isDigit() }.let(::println)

fun problem15() {
    val input = readLine()!!.lowercase()
    for (char in 'a'..'z') {
        if (char in input) continue
        print(char)
    }
}

fun problem16() {
    val charCount = ('a'..'z').associateWith { 0 }.toMutableMap()
    val input = readLine()!!
    for (char in input) {
        charCount[char] = charCount[char]!! + 1
    }
    charCount.filterValues { it == 1 }.count().let(::println)
}

fun problem17() {
    val availableRows = (1..5).toMutableList()
    val availableColumns = (1..5).toMutableList()
    repeat(3) {
        val (row, column) = readLine()!!.split(" ").map { it.toInt() }
        availableRows.remove(row)
        availableColumns.remove(column)
    }
    println(availableRows.joinToString(" "))
    println(availableColumns.joinToString(" "))

}
