package hyperskill.challenging.smartcalculator.stage6

class InvalidAssignmentException : RuntimeException("Invalid assignment")
class InvalidIdentifierException : RuntimeException("Invalid identifier")
class UnknownVariableException : RuntimeException("Unknown variable")
class InvalidExpressionException : RuntimeException("Invalid expression")
class InvalidOperatorException : RuntimeException("Invalid operator")

enum class Operator(val action: (Int, Int) -> Int) {
    PLUS({ a, b -> a + b }),
    MINUS({ a, b -> a - b })
}

val variables = mutableMapOf<String, Int>()

val variableRegex = "[a-zA-Z]+".toRegex()

fun main() {
    while (true) {
        val input = readLine()!!.trim()
        if (input == "/exit") {
            println("Bye!")
            return
        }

        try {
            when {
                input.isEmpty() || input.isBlank() -> continue
                input.startsWith("/") -> determineCommand(input)
                input.contains("=") -> registerVariable(input)
                else -> doMath(input)
            }
        } catch (e: Exception) {
            println(e.message)
        }
    }
}

fun registerVariable(input: String) {
    val (variable, expression) = input.split("\\s*=\\s*".toRegex(), 2)

    if (!variable.matches(variableRegex))
        throw InvalidIdentifierException()

    val value = try {
        resolveExpression(expression)
    } catch (e: InvalidExpressionException) {
        throw InvalidAssignmentException()
    } catch (e: InvalidIdentifierException) {
        throw InvalidAssignmentException()
    } catch (e: InvalidOperatorException) {
        throw InvalidAssignmentException()
    }

    variables[variable] = value
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
    println(resolveExpression(input))
}

fun resolveExpression(input: String): Int {
    return if (' ' in input) {
        doComplexMath(input)
    } else {
        resolveLiteral(input)
    }
}

fun doComplexMath(input: String): Int {
    val numberList = input.replace("\\s+".toRegex(), " ").split(" ")

    try {
        var finalResult = resolveLiteral(numberList[0])
        var idx = 1
        while (idx < numberList.size) {
            val operator = determineOperator(numberList[idx])
            val anotherNumber = resolveLiteral(numberList[++idx])
            finalResult = operator.action(finalResult, anotherNumber)
            idx++
        }

        return finalResult
    } catch (e: NumberFormatException) {
        throw InvalidExpressionException()
    }
}

fun resolveLiteral(input: String): Int {
    return try {
        input.toInt()
    } catch (e: NumberFormatException) {
        resolveVariable(input)
    }
}

fun resolveVariable(input: String): Int {
    if (!input.matches(variableRegex)) throw InvalidIdentifierException()

    return variables[input] ?: throw UnknownVariableException()
}

fun determineOperator(str: String): Operator {
    return if ('+' in str) {
        Operator.PLUS
    } else if ('-' in str) {
        if (str.length % 2 == 0) Operator.PLUS
        else Operator.MINUS
    } else {
        throw InvalidOperatorException()
    }
}
