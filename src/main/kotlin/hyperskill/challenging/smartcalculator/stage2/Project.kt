package hyperskill.challenging.smartcalculator.stage2


fun main() {
    while (true) {
        val input = readLine()!!.trim()

        if (input == "/exit") {
            println("Bye!")
            return
        }

        if (input.isEmpty() || input.isBlank()) continue

        if (' ' in input) {
            val (a, b) = input.split(" ").map { it.toInt() }
            println(a + b)
        } else {
            println(input)
        }
    }
}