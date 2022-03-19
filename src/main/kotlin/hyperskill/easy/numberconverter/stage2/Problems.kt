package hyperskill.easy.numberconverter.stage2

import kotlin.math.abs

fun main() {
    problem31()
}

// ############################

fun isVowel(letter: Char): Boolean {
    val vowels = arrayOf('a', 'e', 'i', 'o', 'u')
    return letter.lowercaseChar() in vowels
}

fun problem1() {
    val letter = readLine()!!.first()

    println(isVowel(letter))
}

// ############################

fun divide(a: Long, b: Long) = a / b.toDouble()

fun problem2() {
    val a = readLine()!!.toLong()
    val b = readLine()!!.toLong()
    println(divide(a, b))
}

// ############################

fun problem3() {
    repeat(4) {
        val char = readLine()!!.first()
        println(char.isDigit())
    }
}

fun problem4() {
    val char = readLine()!!.first()
    println(char.isDigit() && char != '0' || char.isUpperCase())
}

fun problem5() {
    val num = readLine()!!.toInt().toChar()
    val char = readLine()!!.first()
    println(num == char)
}

fun problem6() {
    val char1 = readLine()!!.first()
    val char2 = readLine()!!.first()
    val char3 = readLine()!!.first()
    println(char2 == char1 + 1 && char3 == char2 + 1)
}

fun problem7() {
    val num = readLine()!!
    println(if (abs(num.toInt()) > 9) num[num.lastIndex - 1] else 0)
}

fun problem8() {
    val scanner = java.util.Scanner(System.`in`)
    val name = scanner.next()
    val surname = scanner.next()
    val age = scanner.next()
    println("${name.first()}. $surname, $age years old")
}

fun problem9() {
    val str = readLine()!!
    val idx = readLine()!!.toInt()
    println("Symbol # $idx of the string \"$str\" is '${str[idx - 1]}'")
}

fun problem10() {
    val name = readLine()!!
    val surname = readLine()!!
    println("${name.first()}. $surname")
}

fun problem11() {
    val str = readLine()!!
    val newStr = str.last() + str.substring(1, str.lastIndex) + str.first()
    println(newStr)
}

fun problem12() {
    println("1: " + "content".substring(1, 1))
    println("2: " + "content".substring(0, 0))
    println("3: " + "content".substringBefore("c"))
//    println("4: " + "content".substring(-1,-1))
    println("5: " + "content".substring(0, 1))
    println("6: " + "content".substringAfter("t"))
}

fun problem13() {
    val range1 = readLine()!!.toInt()..readLine()!!.toInt()
    val range2 = readLine()!!.toInt()..readLine()!!.toInt()
    val num = readLine()!!.toInt()
    println(num in range1 && num in range2)
}

fun problem14() {
    val num = readLine()!!.toInt()
    val range = readLine()!!.toInt()..readLine()!!.toInt()
    println(num in range)
}

const val MIN_AGE = 18
const val MAX_AGE = 59

fun problem15() {
    println(readLine()!!.toInt() in MIN_AGE..MAX_AGE)
}

fun problem16() {
    val n = readLine()!!.toInt()
    var longestSequenceLength = 0
    var currentSequenceLength = 0
    var previousElement = 0
    repeat(n) {
        val element = readLine()!!.toInt()
        if (element >= previousElement) {
            currentSequenceLength++
        } else {
            if (currentSequenceLength > longestSequenceLength) {
                longestSequenceLength = currentSequenceLength
            }
            currentSequenceLength = 1
        }
        previousElement = element
    }
    if (currentSequenceLength > longestSequenceLength) {
        longestSequenceLength = currentSequenceLength
    }
    println(longestSequenceLength)
}

fun problem17() {
    val n = readLine()!!.toInt()
    var previous = Int.MIN_VALUE
    repeat(n) {
        val element = readLine()!!.toInt()
        if (element <= previous) {
            println("NO")
            return
        }
        previous = element
    }
    println("YES")
}

fun problem18() {
    val numbers = readLine()!!.split(' ').map { it.toInt() }.toMutableList()

    val tmp = numbers.first()
    numbers[0] = numbers.last()
    numbers[numbers.lastIndex] = tmp

    println(numbers.joinToString(separator = " "))
}

fun problem19() {
    val numbers = MutableList(100) { idx ->
        when (idx) {
            0 -> 1
            9 -> 10
            99 -> 100
            else -> 0
        }
    }

    println(numbers.joinToString())
}

fun problem20() {
    val characters = mutableListOf('a', 'z', 'e', 'd')
    println(characters.joinToString())
}

fun problem21() {
    val firstList = readLine()!!.split(' ').toMutableList()
    val secondList = readLine()!!.split(' ').toMutableList()
    println((firstList + secondList).joinToString())
}

fun problem22() {
    val backFromTheWall = readLine()!!.split(", ").map { it }.toMutableList()
    val returnedWatchman = readLine()!!
    // do not touch the lines above
    // write your code here

    backFromTheWall += returnedWatchman
    println(backFromTheWall.joinToString())
}

fun problem23() {
    val numbers = readLine()!!.split(" ").map { it.toInt() }.toMutableList()
    numbers.add(0, numbers.sum())
    numbers.removeAt(numbers.lastIndex - 1)
    println(numbers.joinToString(" "))
}

fun problem24() {
    val n = readLine()!!.toInt()
    val numbers = MutableList(0) { 0 }
    repeat(n) {
        numbers.add(readLine()!!.toInt())
    }
    val m = readLine()!!.toInt()
    println(if (m in numbers) "YES" else "NO")
}

fun problem25() {
    val n = readLine()!!.toInt()
    val numbers = MutableList(n) { readLine()!!.toInt() }
    val shift = readLine()!!.toInt() % n

    val shiftedNumbers = MutableList(n) { 0 }
    for (idx in numbers.indices) {
        val shiftedIdx = (idx + shift) % n
        shiftedNumbers[shiftedIdx] = numbers[idx]
    }
    println(shiftedNumbers.joinToString(" "))
}

fun problem26() {
    val n = readLine()!!.toInt()
    val numbers = MutableList(n) { readLine()!!.toInt() }
    val (p, m) = readLine()!!.split(" ").map { it.toInt() }

    for (idx in numbers.indices) {
        if (idx == numbers.lastIndex) break

        val element = numbers[idx]
        val next = numbers[idx + 1]

        if (element == p && next == m || element == m && next == p) {
            println("NO")
            return
        }
    }
    println("YES")
}

fun problem27() {
    val n = readLine()!!.toInt()
    val numbers = MutableList(n - 1) { readLine()!!.toInt() }
    val last = readLine()!!.toInt()
    numbers.add(0, last)
    println(numbers.joinToString(" "))
}

fun problem28() {
    val str = readLine()!!
    val reversed = str.reversed()
    println(if (str == reversed) "yes" else "no")
}

fun problem29() {
    val str = readLine()!!

    if (str.length == 1) {
        println(true)
        return
    }

    for (idx in 1..str.lastIndex) {
        val prev = str[idx - 1]
        val curr = str[idx]
        if (curr != prev + 1) {
            println(false)
            return
        }
    }
    println(true)
}

fun problem30() {
    val splitted = readLine()!!.split(" ")
    val str = splitted[0]
    val n = splitted[1].toInt()
    if (n > str.length) {
        println(str)
    } else {
        val output = str.substring(n) + str.substring(0, n)
        println(output)
    }
}

// ############################

const val UPPER_MIN = 65
const val UPPER_MAX = 90
const val LOWER_MIN = 97
const val LOWER_MAX = 122
const val DIGIT_MIN = 48
const val DIGIT_MAX = 57

fun problem31() {
    val (A, B, C, N) = readLine()!!.split(" ").map { it.toInt() }
    val upper = generateSymbols(UPPER_MIN, UPPER_MAX, A)
    val lower = generateSymbols(LOWER_MIN, LOWER_MAX, B)
    val digits = generateSymbols(DIGIT_MIN, DIGIT_MAX, C)
    val abc = A + B + C
    val fill = if (abc < N) {
        generateSymbols(LOWER_MIN, LOWER_MAX, N - abc)
    } else {
        emptyList()
    }
    val symbols = upper + lower + digits + fill
    val password = shuffleSymbols(symbols)
    println(password.joinToString(""))
}

fun generateSymbols(min: Int, max: Int, n: Int): List<Char> {
    val symbols = MutableList(0) { ' ' }
    repeat(n) {
        symbols.add((min..max).random().toChar())
    }
    return symbols
}

fun shuffleSymbols(password: List<Char>): List<Char> {
    val newList = password.toMutableList()
    do {
        newList.shuffle()
    } while (checkDuplicateNeighbours(newList))
    return newList
}

fun checkDuplicateNeighbours(symbols: List<Char>): Boolean {
    for (idx in 1..symbols.lastIndex) {
        val prev = symbols[idx - 1]
        val curr = symbols[idx]
        if (prev == curr) {
            return true
        }
    }
    return false
}
