package hyperskill.easy.numberconverter.stage4

import java.math.BigDecimal
import java.math.RoundingMode

fun main() {
    problem5()
}

fun problem1() {
    val startingAmount = readLine()!!.toBigDecimal()
    val interestRate = readLine()!!.toBigDecimal()
    val years = readLine()!!.toInt()

    val finalAmount = startingAmount.multiply(
        BigDecimal.ONE.add(interestRate.divide(BigDecimal("100")))
            .pow(years)
    ).setScale(2, RoundingMode.CEILING)
    println("Amount of money in the account: $finalAmount")
}

fun problem2() {
    var number = BigDecimal("2.001")
    number += BigDecimal.ONE
    number = -number
    number.setScale(1, RoundingMode.HALF_DOWN)
    print(number)
}

fun problem3() {
    val (a, b, c) = List(3) { readLine()!!.toBigDecimal() }
    val avg = a.add(b).add(c).divide(BigDecimal(3), 0, RoundingMode.DOWN)
    println(avg)
}

fun problem4() {
    val list = mutableListOf(
        mutableListOf(
            mutableListOf(0, 0, 0),
            mutableListOf(0, 0, 0),
            mutableListOf(0, 0, 0),
        ),
        mutableListOf(
            mutableListOf(0, 0, 0),
            mutableListOf(0, 0, 0),
            mutableListOf(0, 0, 0),
        ),
        mutableListOf(
            mutableListOf(0, 0, 0),
            mutableListOf(0, 0, 0),
            mutableListOf(0, 0, 0),
        ),
    )
    print(list)
}

fun problem5() {
    val list = mutableListOf(
        mutableListOf("[0][0]", "[0][1]", "[0][2]"),
        mutableListOf("[1][0]", "[1][1]", "[1][2]"),
    )
    print(list)
}
