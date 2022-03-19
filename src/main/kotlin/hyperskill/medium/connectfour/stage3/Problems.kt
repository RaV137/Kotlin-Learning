package hyperskill.medium.connectfour.stage3

fun main() {
    problem5()
}

fun problem1() {
    val vehicle = Vehicle("Dixi")
    val body = vehicle.Body("red")
    body.printColor()
}

class Vehicle(val name: String) {
    inner class Body(val color: String) {
        fun printColor() {
            println("The ${this@Vehicle.name} vehicle has a $color body.")
        }
    }
}

// ####################

fun solution(numbers: List<Int>) {
    println(numbers.filter { it % 2 == 0 }.joinToString(" "))
}

fun problem2() {
    solution(listOf(8, 11, 13, 2))
}

// ####################

fun solution(products: List<String>, product: String) {
    for (i in products.indices) {
        if (products[i] == product) print("$i ")
    }
}

fun problem3() {
    solution(listOf("Mustard", "Cheese", "Eggs", "Cola", "Eggs"), "Eggs")
}

// ####################

fun solution(numbers: List<Int>, number: Int): MutableList<Int> {
    return numbers.toMutableList().apply { add(number) }
}

fun problem4() {
    println(solution(listOf(8, 11, 13, 14), 1).joinToString(" "))
}

// ####################

fun solution(strings: MutableList<String>, str: String): MutableList<String> {
    for (i in strings.indices) {
        if (strings[i] == str) {
            strings[i] = "Banana"
        }
    }
    return strings
}

fun problem5() {
    println(solution(mutableListOf("Sometimes", "you", "have", "to", "shake", "up", "your", "life"), "shake").joinToString(" "))
}
