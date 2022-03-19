package hyperskill.hard.minesweeper.stage3

fun main() {
    problem3()
}

fun next(prev: Int): Int = prev * 1000 - 10

fun Int.nextValue(): Int = next(this)

fun problem1() {
    println(99.nextValue())
}

// ###############

class Fridge {
    fun open() = println(1)
    fun find(productName: String): Int {
        println(productName)
        return 4
    }
    fun close() = println(3)
}

fun Fridge.take(productName: String): Int {
    open()
    val product = find(productName)
    close()
    return product
}

fun problem2() {
    val fridge = Fridge()
    fridge.take("milk")
}

// ###############

fun Int.lastDigit(): Int {
    val tmp = if (this < 0) -this else this
    return tmp % 10
}

fun problem3() {
    println(4897353.lastDigit())
    println((-334).lastDigit())
}
