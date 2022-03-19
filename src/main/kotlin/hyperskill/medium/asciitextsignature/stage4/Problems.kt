package hyperskill.medium.asciitextsignature.stage4

import java.io.File

fun main() {
    problem10()
}

fun identity(num: Int) = num
fun half(num: Int) = num / 2
fun zero(num: Int) = 0

fun generate(functionName: String): (Int) -> Int {
    return when (functionName) {
        "identity" -> ::identity
        "half" -> ::half
        else -> ::zero
    }
}

fun problem1() {
    println(generate("half")(7))
}

// ###################

fun problem2() {
    val lambda: (Long, Long) -> Long = { left, right -> (left..right).reduce { x, y -> x * y } }

    println(lambda(1, 5))
}

fun problem3() {
    val originalPredicate: (Char) -> Boolean = { true }
    val notPredicate: (Char) -> Boolean = { !originalPredicate(it) }
}

fun problem4() {
    fun compose(g: (Int) -> Int, h: (Int) -> Int): (Int) -> Int {
        return { g(h(it)) }
    }
}

fun problem5() {
    val fileName = "./src/main/resources/new.txt"
    val linesLength = File(fileName).length()
    val lines = File(fileName).readLines().size
    print("$linesLength $lines")
}

fun problem6() {
    val fileName = "./src/main/resources/words_sequence.txt"
    println(File(fileName).readLines().maxOf { it.length })
}

fun problem7() {
    val fileName = "./src/main/resources/words_with_numbers.txt"
    var linesWithNumbers = 0
    File(fileName).forEachLine {
        for (char in it) {
            if (char.isDigit()) {
                linesWithNumbers++
                break
            }
        }
    }
    println(linesWithNumbers)
}

fun problem8() {
    val fileName = "./src/main/resources/text.txt"
    println(File(fileName).readText().split(" ").count())
}

// ###################

class Employee(val id: Int, val name: String, val lastName: String, val telNum: String, val email: String) {
    fun printData() {
        println("Employee $id")
        println("full name: $name $lastName")
        println("tel. num: $telNum")
        println("email: $email")
    }
}

fun problem9() {
    val count = readLine()!!.toInt()
    val emp = ::Employee
    createEmployees(emp, count)
}

fun createEmployees(employeeCreator: (Int, String, String, String, String) -> Employee, count: Int) {
    for (i in 1..count) {
        val (name, lastName, telNum, email) = readLine()!!.split(' ').toList()
        val newEmployee = employeeCreator(i, name, lastName, telNum, email)
        newEmployee.printData()
    }
}

// ###################

fun multiplyByTwo(number: Int): Int {
    return number * 2
}

fun addTen(number: Int): Int {
    return number + 10
}

fun changeNumber(changeFunction: (Int) -> Int, number: Int) {
    val newNumber = changeFunction(number)
    print("$newNumber ")
}

fun problem10() {
    val numbers = readLine()!!.split(' ').map { it.toInt() }.toList()
    var numberFun: (Int) -> Int
    for (number in numbers) {
        numberFun = if (number % 2 == 0) ::addTen else ::multiplyByTwo
        changeNumber(numberFun, number)
    }
}


