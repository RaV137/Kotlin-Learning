package hyperskill.challenging.smartcalculator.stage4

enum class Operator(val action: (Int, Int) -> Int) {
    PLUS({ a, b -> a + b }),
    MINUS({ a, b -> a - b })
}

fun main() {
    while (true) {
        val input = readLine()!!.trim()

        when {
            input == "/exit" -> {
                println("Bye!")
                return
            }
            input == "/help" -> printHelp()
            input.isEmpty() || input.isBlank() -> continue
            else -> doMath(input)
        }
    }
}

fun printHelp() {
    println(
        """
This is calculator, that reads user's input, parses it and computes result.
        """.trimIndent()
    )
}

fun doMath(input: String) {
    if (' ' in input) {
        doComplexMath(input)
    } else {
        println(input)
    }
}

fun doComplexMath(input: String) {
    val numberList = input.replace("\\s+".toRegex(), " ").split(" ")

    var finalResult = numberList[0].toInt()
    var idx = 1
    while (idx < numberList.size) {
        val operator = determineOperator(numberList[idx])
        val anotherNumber = numberList[++idx].toInt()
        finalResult = operator.action(finalResult, anotherNumber)
        idx++
    }

    println(finalResult)
}

fun determineOperator(str: String): Operator {
    return if ('+' in str) {
        Operator.PLUS
    } else {
        if (str.length % 2 == 0) Operator.PLUS
        else Operator.MINUS
    }
}
