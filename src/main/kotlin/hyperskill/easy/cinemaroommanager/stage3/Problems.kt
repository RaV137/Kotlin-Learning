package hyperskill.easy.cinemaroommanager.stage3

fun main() {
    problem3()
}

fun problem1() {
    val units = readLine()!!.toInt()
    println(
        when (units) {
            0 -> "no army"
            in 1..4 -> "few"
            in 5..9 -> "several"
            in 10..19 -> "pack"
            in 20..49 -> "lots"
            in 50..99 -> "horde"
            in 100..249 -> "throng"
            in 250..499 -> "swarm"
            in 500..999 -> "zounds"
            else -> "legion"
        }
    )
}

fun problem2() {
    val a = 10

    when (a) {
//        in 1..20 -> print("Ok"); print("Ok")
        11, 12 -> print("Ok")
        a + a -> print("Ok")
        in 1..22 -> print("Ok")
//        "2" -> print("Ok")
//        > 0 -> print("Ok")
    }
}

// #######################

fun problem3() {
    val type = readLine()!!
    println(
        when (type) {
            "triangle" -> triangleArea()
            "rectangle" -> rectangleArea()
            "circle" -> circleArea()
            else -> "Invalid figure type."
        }
    )
}

const val THREE = 3
fun triangleArea(): Double {
    val (a, b, c) = List(THREE) { readLine()!!.toDouble() }
    return (a + b + c).let { kotlin.math.sqrt(it / 2 * (it / 2 - a) * (it / 2 - b) * (it / 2 - c)) }
}

fun rectangleArea() = List(2) { readLine()!!.toDouble() }.reduce { a, b -> a * b }

const val PI = 3.14
fun circleArea() = readLine()!!.toDouble().let { PI * it * it }

// #######################
