package pl.ing.chapter.live

//import pl.ing.chapter.prepared.isBlank

open class Shape
class Triangle : Shape()

fun Shape.getName() = "Shape"
fun Triangle.getName() = "Triangle"

fun printClassName(s: Shape) {
    println(s.getName())
}

fun printTriangleClassName(t: Triangle) {
    println(t.getName())
}

/*
Z dokumentacji:
actual - denotes a platform-specific implementation in multiplatform projects.
expect - marks a declaration as platform-specific, expecting an implementation in platform modules.
inline - tells the compiler to inline a function and the lambdas passed to it at the call site.
infix - allows calling a function using infix notation.
*/

fun String.get(pos: Int): Char = 'x'

fun String.substring(startIndex: Int, endIndex: Int): String = "aaa"


infix fun String.splittedBy(delimiter: String): List<String> = this.split(delimiter)


fun Any?.toString(): String {
    return this?.toString() ?: "It's a NULL"
}

fun <T, R> T.concatAsString(b: R): String {
    return this.toString() + b.toString()
}

operator fun String.times(by: Int): String = this.repeat(by)

fun main() {
    printClassName(Triangle())
    printTriangleClassName(Triangle())

    val str = "abc"
    println(str.get(1))
    println(str.isBlank())
    println(str.substring(0, 1))


    val toSplit = "Путін іді начуй"
    println(toSplit splittedBy " ")


    var nullableVar: Int? = null
    println(nullableVar.toString())
    nullableVar = 15
    println(nullableVar.toString())

    println(5.concatAsString(10))
    println("5".concatAsString("10"))
    println(5.concatAsString("10"))


    val anotherStr = "aBc"
    println(anotherStr * 4)
}