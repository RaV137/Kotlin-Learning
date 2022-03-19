package hyperskill.hard.sortingtool.stage1

import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)

    val numbers = mutableMapOf<Int, Int>()
    while (scanner.hasNext()) {
        val number = scanner.nextInt()
        numbers.putIfAbsent(number, 0)
        numbers[number] = numbers[number]!! + 1
    }

    var maxNumber = Int.MIN_VALUE
    var maxNumberOccurrences = 0
    var totalNumbers = 0
    for (entry in numbers.entries) {
        if (entry.key > maxNumber) {
            maxNumber = entry.key
            maxNumberOccurrences = entry.value
        }

        totalNumbers += entry.value
    }

    println("Total numbers: $totalNumbers.")
    println("The greatest number: $maxNumber ($maxNumberOccurrences time(s)).")
}
