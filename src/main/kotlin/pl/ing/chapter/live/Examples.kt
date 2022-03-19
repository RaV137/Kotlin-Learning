package pl.ing.chapter.live

import pl.ing.chapter.prepared.isBlankOrEmpty

fun main() {
//    String a = "abc"
    var a = "abc"
    var b: String = "abc"

//    final var c = "abc"
    val c = "abc"

//    var d: String = null

    var d: String? = null
    var e: String = nullableFun() ?: "not null"
//    val f: String = nullableFun()!!

    println(e)
    println("$a = $b")
    println("${a.length} = ${b.length}")
    println(
        """
        asdad
            sadasdas
        $a
    """.trimIndent()
    )

    doSth()
    doSth("abc")
    doSth(arg = "def")
    doSth3(arg2 = "abc", arg1 = "def")

    doSthMultiArgs(arg1 = "abc", arg2 = 3)
    doSthMultiArgs(arg1 = "abc")
    doSthMultiArgs(arg2 = 1, arg1 = "abc")

    doSthMultiArgs("abc", arg2 = 2)
    doSthMultiArgs(arg1 = "abc", 2)

    val coords = getCoordinates()
    println(coords)

    val (xCord, yCord) = getCoordinates()
    println("x: $xCord, y: $yCord")

//    val (a1, b1, c1, d1) = readLine()!!.split(" ").map { it.toInt() }

    val number = 10
//    val nextEvenNumber = number % 2 == 0 ? number + 2 : number + 1
    val nextEvenNumber = if (number % 2 == 0) number + 2 else number + 1
    println("Next even number of $number = $nextEvenNumber")


    val string = "some string"
    val stringDesc = when (string.length) {
        0, 11, 14 -> "empty"
        in 1..5 -> "short"
        in 6..10 -> "medium"
        else -> "long"
    }
    println("string is $stringDesc")


    for (i in 1..3) println(i)
    println()
    for (i in 1 until 3) println(i)
    println()
    for (i in 3 downTo 1) println(i)
    println()
    for (i in 1..7 step 2) println(i)
    println()


    val anotherNumber = 26
    val evenHalf = (anotherNumber / 2).let { result -> if (result % 2 == 0) result else result + 1 }


    val point = java.awt.Point(10, 15)
    println("x: ${point.x}, y: ${point.y}")
    println(point)

    with(point) {
        println("x: $x, y: $y")
        println(this)
        move(-5, 10)
        println("x: $x, y: $y")
    }

    val list = listOf(1, 2, 3)
    println(
        """
        elements: ${list.joinToString(" - ")}
        elements: ${list.joinToString(" - ") { it.toString().repeat(2) }}
        size: ${list.size}
        last index: ${list.lastIndex}
        last element: ${list[list.lastIndex]}
        last element: ${list.last()}
        fist element: ${list[0]}
        fist element: ${list.first()}
        list w/o last element: ${list.dropLast(1)}
        single: $ {list.single()} // if only one element - return it, else throw exception
    """.trimIndent()
    )

    for (i in 0..list.lastIndex) print(list[i])
    println()
    for (elemnt in list) print(elemnt)
    println()
    repeat(list.size) { print(list[it]) }
    println()
    repeat(list.size, { print(list[it]) })
    println()


    val lambda: (Int) -> Unit = { idx -> print(list[idx]) }
    val lambda1 = { idx: Int -> print(list[idx]) }
    repeat(list.size, lambda)
    println()
    repeat(list.size, lambda1)
    println()


    val function = fun(idx: Int) {
        print(list[idx])
    }
    repeat(list.size, function)
    println()

    val str1 = ""
    val str2 = "abc"
    println(str1.isBlankOrEmpty())
    println(str2.isBlankOrEmpty())

    println(str2.isEmpty())
    println(str1.isEmpty())
}

fun String.isBlankOrEmpty() = isBlank() || isEmpty()

fun String.isEmpty() = false

fun nullableFun(): String? = null

fun doSth(arg: String = "sth") {
    println("Doing $arg")
}

fun doSth3(arg1: String = "abc", arg2: String = "def") {
    println("arg1: $arg1, arg2: $arg2")
}

fun doSthMultiArgs(arg1: String, arg2: Int = 1) {
    println(arg1.repeat(arg2))
}

fun getCoordinates(): Pair<Int, Int> {
    return 1 to 2
}

fun getAbc(): List<Int> {
    return listOf(1, 2, 3, 4)
}
