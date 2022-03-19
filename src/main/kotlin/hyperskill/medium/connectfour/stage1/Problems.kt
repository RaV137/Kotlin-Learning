package hyperskill.medium.connectfour.stage1

fun main() {
    problem1()
}

fun problem1() {
    val report = readLine()!!
    val passedRegex = ". wrong answers?".toRegex()
    println(passedRegex.matches(report))
}