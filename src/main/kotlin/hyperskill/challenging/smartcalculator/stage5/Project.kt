package hyperskill.challenging.smartcalculator.stage5

enum class Operator(val action: (Int, Int) -> Int) {
    PLUS({ a, b -> a + b }),
    MINUS({ a, b -> a - b })
}

fun main() {
    while (true) {
        val input = readLine()!!.trim()
        if (input == "/exit") {
            println("Bye!")
            return
        }

        when {
            input.isEmpty() || input.isBlank() -> continue
            input.startsWith("/") -> determineCommand(input)
            else -> doMath(input)
        }
    }
}

fun determineCommand(input: String) {
    when (input) {
        "/help" -> printHelp()
        else -> println("Unknown command")
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
    try {
        if (' ' in input) {
            doComplexMath(input)
        } else {
            println(input.toInt())
        }
    } catch (e: Exception) {
        println("Invalid expression")
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
    if (str.matches(".*\\d.*".toRegex())) throw RuntimeException("Invalid operator")

    return if ('+' in str) {
        Operator.PLUS
    } else {
        if (str.length % 2 == 0) Operator.PLUS
        else Operator.MINUS
    }
}
