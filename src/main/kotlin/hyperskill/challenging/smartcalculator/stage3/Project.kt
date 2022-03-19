package hyperskill.challenging.smartcalculator.stage3

fun main() {
    while (true) {
        val input = readLine()!!.trim()

        when {
            input == "/exit" -> {
                println("Bye!")
                return
            }
            input == "/help" -> println("The program calculates the sum of numbers")
            input.isEmpty() || input.isBlank() -> continue
            else -> doMath(input)
        }
    }
}

fun doMath(input: String) {
    if (' ' in input) {
        val numberList = input.split(" ").map { it.toInt() }
        println(numberList.sum())
    } else {
        println(input)
    }
}
