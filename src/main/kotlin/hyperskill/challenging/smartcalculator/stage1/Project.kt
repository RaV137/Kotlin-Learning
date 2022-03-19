package hyperskill.challenging.smartcalculator.stage1

fun main() {
    val (a, b) = readLine()!!.split(" ").map { it.toInt() }
    println(a + b)
}