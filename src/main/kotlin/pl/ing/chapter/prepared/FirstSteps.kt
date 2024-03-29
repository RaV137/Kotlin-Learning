package pl.ing.chapter.prepared

const val d = "abc"

fun main() {
//    String a = "abc"
    var a = "abc"
    var b: String = "abc"
//    final var c = "abc"
    val c = "abc"

//    val e: String = null
    val e: String? = null
    val f: String = nullableFunction() ?: "abc"
    val g: String = nullableFunction()!!

    println(f)
    println("$a $b")
    println(
        """
        $c
        $d cośtam $g
        $e
    """.trimIndent()
    )

    doSth()
    doSth("abc")
    doSth(arg = "abc")
    doSthMultiArgs("abc")
    doSthMultiArgs(arg1 = "abc")
    doSthMultiArgs("abc", 2)
    doSthMultiArgs("abc", arg2 = 2)
    doSthMultiArgs(arg1 = "abc", 2)
    doSthMultiArgs(arg1 = "abc", arg2 = 2)

    val coordinates = getCoordinates()
    println(coordinates)
    val (xCord, yCord) = getCoordinates()
    println("x: $xCord, y: $yCord")

    val number = 10
//    val nextEven = number % 2 == 0 ? number + 2 : number + 1
    val nextEven = if (number % 2 == 0) number + 2 else number + 1
    println("Next even number of $number = $nextEven")


    val string = "some string"
    val stringDescription = when (string.length) {
        0 -> "empty"
        in 1..5 -> "short"
        in 6..10 -> "medium"
        else -> "long"
    }
    println("string is $stringDescription")


    for (i in 1..3) println(i)
    println()
    for (i in 1 until 3) println(i)
    println()
    for (i in 3 downTo 1) println(i)
    println()
    for (i in 1..5 step 2) println(i)

    val anotherNumber = 26
    val evenHalf = (anotherNumber / 2).let { if (it % 2 == 0) it else it + 1 }
    println("Even half of another number = $evenHalf")

    // TODO: apply

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
    for (element in list) print(element)
    println()
    repeat(list.size) { print(list[it]) }
    println()
    repeat(list.size, { print(list[it]) })
    println()
    println()

    val lambda: (Int) -> Unit = {idx ->  print(list[idx])}
    val lambda1 = {idx: Int ->  print(list[idx])}
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
}

fun nullableFunction(): String? {
    return "empty"
}

fun doSth(arg: String = "sth") {
    println("Doing: $arg")
}

fun doSthMultiArgs(arg1: String, arg2: Int = 1) {
    println(arg1.repeat(arg2))
}

fun getCoordinates(): Pair<Int, Int> {
    return 1 to 2
}

fun String.isBlankOrEmpty() = isBlank() || isEmpty()

fun String.isEmpty() = true
