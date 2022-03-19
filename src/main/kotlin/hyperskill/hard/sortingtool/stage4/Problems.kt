package hyperskill.hard.sortingtool.stage4

fun main() {
    problem1()
}

fun problem1() {
    val studentsMarks = mutableMapOf<String, Int>()

    while (true) {
        val key = readLine()!!
        if (key == "stop") {
            println(studentsMarks)
            return
        }
        val value = readLine()!!.toInt()
        studentsMarks.putIfAbsent(key, value)
    }
}


