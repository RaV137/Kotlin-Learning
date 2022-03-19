package hyperskill.easy.numberconverter.stage1

import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs

fun main() {
    problem28()
}

fun problem1() {
    val scanner = Scanner(System.`in`)
    val input = scanner.nextLine()
    val length = input.length
    for (i in 1..length) {
        print(input)
    }
}

fun problem2() {
    val scanner = Scanner(System.`in`)
    val input1 = scanner.nextLine()
    val input2 = scanner.nextLine()
    val num1 = BigDecimal(input1)
    val num2 = BigDecimal(input2)
    val sum = num1.add(num2)
    val percentage1 = num1.divide(sum, 3, RoundingMode.HALF_UP).multiply(BigDecimal(100))
    val percentage2 = BigDecimal(100).subtract(percentage1)
    print("%d%% %d%%".format(percentage1.toInt(), percentage2.toInt()))
}

fun problem3() {
    println(Int.SIZE_BITS)
    println(Int.MIN_VALUE)
    println(Int.MAX_VALUE)
}

fun problem4() {
    val input = readLine()
    val num = input?.toDouble()
    println(num?.toLong())
}

fun problem5() {
    val i = 101
    val d = 3.1415
    val s = "32"
//    var res1 = i.toBoolean()
    var res2 = i.toDouble()
    var res3 = d.toLong()
    var res4 = d.toString()
//    var res5 = s.toChar()
    var res6 = s.toInt()
}

fun problem6() {
    val input = readLine()!!
    println(input.toInt())
    println(input.toDouble())
    println(input.toBoolean())
}

fun problem7() {
    val b0: Byte = 2
    val s0: Short = 10
    val n0: Int = 5
    val l0: Long = 14
    val f0: Float = 11.4f

    val b: Byte = 5                 //1
//    val s: Short = 2 + b0           //2
    val n: Int = s0.toByte() + 2    //3
//    val l: Long = n0 + 4            //4
    val f: Float = l0.toFloat() + 1 //5
//    val d: Double = f0 / 1          //6
}

fun problem8() {
    val f: Float = (20.0 + 20.02f).toFloat() // 1
    val l: Long = 10 + 2L        // 2
//    val n: Int = 3L + 5          // 3
}

fun problem9() {
    val a: Byte = 100              //1
//    val b: Byte = 200              //2
//    val c: Byte = 100 + 100        //3
//    val d: Byte = a + a            //4
    val e: Byte = (a + a).toByte() //5
}

fun problem10() {
    val squirrelsNumber = readLine()!!.toInt()
    val nutsNumber = readLine()!!.toInt()
    val nutsPerSquirrel = nutsNumber / squirrelsNumber
    println(nutsPerSquirrel)
}

fun problem11() {
    val students1 = readLine()!!.toInt()
    val students2 = readLine()!!.toInt()
    val students3 = readLine()!!.toInt()
    val desks1 = students1 / 2 + students1 % 2
    val desks2 = students2 / 2 + students2 % 2
    val desks3 = students3 / 2 + students3 % 2
    println(desks1 + desks2 + desks3)
}

fun problem12() {
    val num1 = readLine()!!.toInt()
    val num2 = readLine()!!.toInt()
    val num3 = readLine()!!.toInt()
    println(num1 != num2 && num2 != num3 && num1 != num3)
}

fun problem13() {
    val num1 = readLine()!!.toInt()
    val num2 = readLine()!!.toInt()
    val num3 = readLine()!!.toInt()
    var positiveCount = 0
    if (num1 > 0) positiveCount++
    if (num2 > 0) positiveCount++
    if (num3 > 0) positiveCount++
    val onlyOnePositive = positiveCount == 1
    println(onlyOnePositive)
}

fun problem14() {
    val num1 = readLine()!!.toInt()
    val num2 = readLine()!!.toInt()
    val num3 = readLine()!!.toInt()
    val isBetween = num1 in num2..num3 || num1 in num3..num2
    println(isBetween)
}

fun problem15() {
    val num1 = readLine()!!.toInt()
    val num2 = readLine()!!.toInt()
    println("$num1 plus $num2 equals ${num1 + num2}")
}

fun problem16() {
    val input = readLine()!!
    val length = input.length
    println("$length repetitions of the word $input: ${input.repeat(length)}")
}

fun problem17() {
    val num1 = readLine()!!.toInt()
    val num2 = readLine()!!.toInt()
    println("$num1 $num2")
}

fun problem18() {
    val scanner = java.util.Scanner(System.`in`)
    val hours = scanner.next()
    val minutes = scanner.next()
    val seconds = scanner.next()
    val day = scanner.next()
    val month = scanner.next()
    val year = scanner.next()
    println("$hours:$minutes:$seconds $day/$month/$year")
}

const val TWO = 2
const val THREE = 3
const val FIVE = 5
const val SIX = 6

fun problem19() {
    val num = readLine()!!.toInt()
    val numbers = arrayOf(TWO, THREE, FIVE, SIX)
    for (number in numbers) {
        if (num % number == 0) println("Divided by $number")
    }
}

fun problem20() {
    val scanner = java.util.Scanner(System.`in`)
    val x1 = scanner.next().toInt()
    val y1 = scanner.next().toInt()
    val x2 = scanner.next().toInt()
    val y2 = scanner.next().toInt()

    val takeEachOther = if (x1 == x2 || y1 == y2) {
        "YES"
    } else if (abs(x1 - y1) == abs(x2 - y2)) {
        "YES"
    } else if (x1 + y1 == x2 + y2) {
        "YES"
    } else {
        "NO"
    }
    println(takeEachOther)
}

fun problem21() {
    val scanner = java.util.Scanner(System.`in`)
    val x1 = scanner.next().toInt()
    val y1 = scanner.next().toInt()
    val x2 = scanner.next().toInt()
    val y2 = scanner.next().toInt()

    val takeEachOther = if (abs(x1 - x2) == 2 && abs(y1 - y2) == 1) {
        "YES"
    } else if (abs(x1 - x2) == 1 && abs(y1 - y2) == 2) {
        "YES"
    } else {
        "NO"
    }
    println(takeEachOther)
}

const val D = 2
const val C = 3
const val B = 4
const val A = 5

fun problem22() {
    val scanner = java.util.Scanner(System.`in`)
    val studentsNumber = scanner.nextInt()
    var d = 0
    var c = 0
    var b = 0
    var a = 0
    repeat(studentsNumber) {
        val grade = scanner.nextInt()
        if (grade == D) d++
        if (grade == C) c++
        if (grade == B) b++
        if (grade == A) a++
    }
    println("$d $c $b $a")
}

const val FOUR = 4

fun problem23() {
    val scanner = java.util.Scanner(System.`in`)
    val numbers = scanner.nextInt()
    var max = Int.MIN_VALUE
    repeat(numbers) {
        val number = scanner.nextInt()
        if (number % FOUR == 0 && number > max) {
            max = number
        }
    }
    println(max)
}

fun problem24() {
    val scanner = java.util.Scanner(System.`in`)
    val numbers = scanner.nextInt()
    var positiveNumbers = 0
    repeat(numbers) {
        val number = scanner.nextInt()
        if (number > 0) positiveNumbers++
    }
    println(positiveNumbers)
}

const val LARGER = 1
const val SMALLER = -1
const val PERFECT = 0

fun problem25() {
    val scanner = java.util.Scanner(System.`in`)
    val components = scanner.nextInt()
    var perfect = 0
    var larger = 0
    var smaller = 0
    repeat(components) {
        val component = scanner.nextInt()
        if (component == LARGER) larger++
        if (component == SMALLER) smaller++
        if (component == PERFECT) perfect++
    }
    println("$perfect $larger $smaller")
}

fun problem26() {
    var n = readLine()!!.toInt()
    print(n)
    while (n != 1) {
        print(" ")
        n = if (n % 2 == 0) {
            n / 2
        } else {
            n * 3 + 1
        }
        print("$n")
    }
}

fun problem27() {
    var max = Int.MIN_VALUE
    do {
        val number = readLine()!!.toInt()
        if (number > max) max = number
    } while (number != 0)
    println(max)
}

const val BOX_SIZE = 3

fun problem28() {
    val box1 = arrayListOf<Int>()
    val box2 = arrayListOf<Int>()

    val scanner = java.util.Scanner(System.`in`)
    repeat(BOX_SIZE) {
        box1.add(scanner.nextInt())
    }
    repeat(BOX_SIZE) {
        box2.add(scanner.nextInt())
    }

    val diff = populateBoxDiff(box1, box2)
    printBoxDiff(diff)
}

fun populateBoxDiff(box1: ArrayList<Int>, box2: ArrayList<Int>): ArrayList<Int> {
    box1.sort()
    box2.sort()

    val diff = arrayListOf<Int>()
    for (i in 0 until BOX_SIZE) {
        val x1 = box1[i]
        val x2 = box2[i]
        if (x1 == x2) {
            diff.add(0)
        } else if (x1 < x2) {
            diff.add(-1)
        } else {
            diff.add(1)
        }
    }
    return diff
}

fun printBoxDiff(diff: ArrayList<Int>) {
    if (0 in diff && -1 !in diff && 1 !in diff) {
        println("Box 1 = Box 2")
    } else if (-1 in diff && 1 in diff) {
        println("Incomparable")
    } else if (-1 in diff && 1 !in diff) {
        println("Box 1 < Box 2")
    } else if (1 in diff && -1 !in diff) {
        println("Box 1 > Box 2")
    }
}
