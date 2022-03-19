package hyperskill.challenging.smartcalculator.stage7

import java.util.*

class InvalidAssignmentException : RuntimeException("Invalid assignment")
class InvalidIdentifierException : RuntimeException("Invalid identifier")
class UnknownVariableException : RuntimeException("Unknown variable")
class InvalidExpressionException : RuntimeException("Invalid expression")
class InvalidOperatorException : RuntimeException("Invalid operator")

enum class Operator(val calculation: (Int, Int) -> Int) {
    PLUS({ a, b -> a + b }),
    MINUS({ a, b -> a - b }),
    MULTIPLY({ a, b -> a * b }),
    DIVIDE({ a, b -> a / b }),
    POWER({ a, b ->
        var result = 1
        repeat(b) { result *= a }
        result
    }),
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

fun resolveOperator(str: String): Operator {
    return if ('+' in str) {
        Operator.PLUS
    } else if ('-' in str) {
        if (str.length % 2 == 0) Operator.PLUS
        else Operator.MINUS
    } else if ("*" == str) {
        Operator.MULTIPLY
    } else if ("/" == str) {
        Operator.DIVIDE
    } else if ("^" == str) {
        Operator.POWER
    } else {
        throw InvalidOperatorException()
    }
}

fun resolveExpression(input: String): Int {
    try {
        val elements = prepareInput(input)
        val postfixList = translateToPostfix(elements)
        return calculateFromPostfix(postfixList)
    } catch (e: Exception) {
        throw InvalidExpressionException()
    }
}

fun prepareInput(input: String): List<String> {
    val newValue = input.replace("\\s+".toRegex(), "")
        .replace("\\++".toRegex(), " + ")
        .replace("(?<=[\\w()])-(?=[\\w()])".toRegex(), " - ")
        .replace("*", " * ")
        .replace("/", " / ")
        .replace("^", " ^ ")
        .replace("(", " ( ")
        .replace(")", " ) ")

    val minusResults = "-{2,}".toRegex().findAll(newValue)
    var finalValue = newValue

    for (result in minusResults) {
        val replacement = if (result.value.length % 2 == 0) " + " else " - "
        finalValue = finalValue.replaceFirst(result.value, replacement)
    }

    return finalValue.trim().split("\\s+".toRegex())
}

fun translateToPostfix(elements: List<String>): List<String> {
    val postfixStack = Stack<String>()
    val resultList = mutableListOf<String>()
    val numberVarRegex = "-?\\w+".toRegex()

    for (element in elements) {
        // Add operands (numbers and variables) to the result (postfix notation) as they arrive
        if (element.matches(numberVarRegex)) {
            resultList.add(element)
            continue
        }

        // If the stack is empty or contains a left parenthesis on top, push the incoming operator on the stack.
        if (postfixStack.isEmpty() || postfixStack.peek() == "(") {
            postfixStack.push(element)
            continue
        }

        // If the incoming element is a left parenthesis, push it on the stack.
        if (element == "(") {
            postfixStack.push(element)
            continue
        }

        // If the incoming element is a right parenthesis, pop the stack and add operators to the result until
        // you see a left parenthesis. Discard the pair of parentheses.
        if (element == ")") {
            while (postfixStack.peek() != "(") {
                resultList.add(postfixStack.pop())
            }
            postfixStack.pop() // Delete "(" from stack
            continue
        }

        val newOperatorPrecedence = operatorPrecedence(element)
        var topOperatorPrecedence = operatorPrecedence(postfixStack.peek())
        // If the incoming operator has higher precedence than the top of the stack, push it on the stack.
        if (newOperatorPrecedence > topOperatorPrecedence) {
            postfixStack.push(element)
            continue
        } else {
            // If the incoming operator has lower or equal precedence than or to the top of the stack,
            // pop the stack and add operators to the result until you see an operator that has a smaller precedence
            // or a left parenthesis on the top of the stack; then add the incoming operator to the stack.
            while (newOperatorPrecedence <= topOperatorPrecedence && topOperatorPrecedence != 10) {
                resultList.add(postfixStack.pop())
                if (postfixStack.isEmpty()) break
                topOperatorPrecedence = operatorPrecedence(postfixStack.peek())
            }
            postfixStack.push(element)
            continue
        }
    }

    // At the end of the expression, pop the stack and add all operators to the result.
    while (!postfixStack.isEmpty()) {
        resultList.add(postfixStack.pop())
    }

    return resultList
}

fun calculateFromPostfix(postfixList: List<String>): Int {
    val numberStack = Stack<Int>()

    for (element in postfixList) {
        try {
            numberStack.push(resolveLiteral(element))
        } catch (e: Exception) {
            // InvalidIdentifierException, UnknownVariableException
            val operator = resolveOperator(element)
            val a = numberStack.pop()
            val b = numberStack.pop()
            numberStack.push(operator.calculation(b, a))
        }
    }

    return numberStack.pop()
}

fun operatorPrecedence(operator: String): Int {
    val precedenceMap = mapOf(
        '(' to 10,
        '^' to 3,
        '*' to 2,
        '/' to 2,
        '+' to 1,
        '-' to 1
    )

    return precedenceMap[operator.first()] ?: throw InvalidOperatorException()
}
