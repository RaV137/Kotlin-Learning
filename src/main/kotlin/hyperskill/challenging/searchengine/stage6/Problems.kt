package hyperskill.challenging.searchengine.stage6

fun main() {
    problem1()
}

fun problem1() {
    val size = readLine()!!.toInt()
    val list = IntArray(size)

    for (i in 0 until size) {
        list[i] = readLine()!!.toInt()
    }

    var triplesCount = 0
    for (i in 0 until size - 2) {
        val first = list[i]
        val second = list[i + 1]
        val third = list[i + 2]

        if (third == second + 1 && second == first + 1) {
            triplesCount++
        }
    }

    println(triplesCount)
}

fun problem2() {
    val size = readLine()!!.toInt()
    val list = List(size) { readLine()!!.toInt() }
    val number = readLine()!!.toInt()
    println(if (number in list) "YES" else "NO")
}

fun problem3() {
    val size = readLine()!!.toInt()
    val list = List(size) { readLine()!!.toInt() }
    val (p, m) = readLine()!!.split(" ").map { it.toInt() }
    println(if (p in list && m in list) "YES" else "NO")
}
