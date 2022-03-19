package hyperskill.easy.simpletictactoe.stage5

import kotlin.math.*
import kotlin.random.Random

fun main() {
    problem7()
}

fun problem1() {
    val currPopulation = readLine()!!.toDouble()
    println(currPopulation.pow(1 / 3.0).roundToInt())
}

fun problem2() {
    val (a, b, c) = List(3) { readLine()!!.toDouble() }
    val delta = b * b - 4 * a * c
    val x1 = (-b - sqrt(delta)) / (2 * a)
    val x2 = (-b + sqrt(delta)) / (2 * a)
    print(if (x1 > x2) "$x2 $x1" else "$x1 $x2")
}

fun problem3() {
    val (u1, u2) = readLine()!!.split(" ").map { it.toDouble() }
    val (v1, v2) = readLine()!!.split(" ").map { it.toDouble() }
    val uv = u1 * v1 + u2 * v2
    val absUV = sqrt(u1 * u1 + u2 * u2) * sqrt(v1 * v1 + v2 * v2)
    val acos = acos(uv / absUV)
    val degree = (acos / PI * 180).roundToInt()
    println(degree)
}

fun problem4() {
    val input = readLine()!!.toDouble()
    println((input * 10).toInt() % 10)
}

// ############################

fun generatePredictablePassword(seed: Int): String {
    var randomPassword = ""
    val gen = Random(seed)
    repeat(10) {
        randomPassword += gen.nextInt(33, 127).toChar()
    }
    return randomPassword
}

fun problem5() {
    println(generatePredictablePassword(42))
}

// ############################

fun createDiceGameRandomizer(n: Int): Int {
    var seed = 1
    while (true) {
        val gen = Random(seed)
        val userGenerated = List(n) { gen.nextInt(1, 7) }.sum()
        val casinoGenerated = List(n) { gen.nextInt(1, 7) }.sum()

        if (userGenerated < casinoGenerated) {
            return seed
        }
        seed++
    }
}

fun problem6() {
    println(createDiceGameRandomizer(3))
}

// ############################

fun generateTemperature(seed: Int): String {
    val generator = Random(seed)
    return List(7) { generator.nextInt(20, 31) }.joinToString(" ")
}

fun problem7() {
    println(generateTemperature(42))
}
