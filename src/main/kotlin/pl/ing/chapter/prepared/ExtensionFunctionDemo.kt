package pl.ing.chapter.prepared

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

// ################ Metoda zdeklarowana w klasie String

/**
 * Returns the character of this string at the specified [index].
 *
 * If the [index] is out of bounds of this string, throws an [IndexOutOfBoundsException] except in Kotlin/JS
 * where the behavior is unspecified.
 */
//public override fun get(index: Int): Char

fun String.get(pos: Int): Char = 'x'

// If a class has a member function, and an extension function is defined which has the same receiver type, the same name is applicable to given arguments, the member always wins.

// ################ Metoda rozszerzająca

/**
 * Returns `true` if this string is empty or consists solely of whitespace characters.
 *
 * @sample samples.text.Strings.stringIsBlank
 */
//public actual fun CharSequence.isBlank(): Boolean = length == 0 || indices.all { this[it].isWhitespace() }

//fun String.isBlank(): Boolean = this == "abc"
//fun CharSequence.isBlank(): Boolean = true


/**
 * Returns the substring of this string starting at the [startIndex] and ending right before the [endIndex].
 *
 * @param startIndex the start index (inclusive).
 * @param endIndex the end index (exclusive).
 */
//@kotlin.internal.InlineOnly
//public actual inline fun String.substring(startIndex: Int, endIndex: Int): String = (this as java.lang.String).substring(startIndex, endIndex)

fun String.substring(startIndex: Int, endIndex: Int): String = "aaa"

/*
Z dokumentacji:
actual - denotes a platform-specific implementation in multiplatform projects.
expect - marks a declaration as platform-specific, expecting an implementation in platform modules.
inline - tells the compiler to inline a function and the lambdas passed to it at the call site.
infix - allows calling a function using infix notation.
*/

// ################ Metoda rozszerzająca infix

infix fun String.splittedBy(delimiter: String): List<String> = this.split(delimiter)

// ################ Metoda rozszerzająca typ nullable

fun Any?.toString(): String {
    return this?.toString() ?: "It's a NULL"
}

// ################ Metoda rozszerzająca generyczna

fun <T, R> T.concatAsString(b: R): String {
    return this.toString() + b.toString()
}

// ################ Metoda rozszerzająca operator

operator fun String.times(by: Int): String = this.repeat(by)


fun main() {
    printClassName(Triangle())
    printTriangleClassName(Triangle())

    val str = "abc"
    println(str.get(1))
    println(str.isBlank())
    println(str.substring(1, 2))

    val toSplit = "Путін іді на чуй"
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