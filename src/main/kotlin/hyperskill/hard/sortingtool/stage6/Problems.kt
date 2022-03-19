package hyperskill.hard.sortingtool.stage6

fun main() {
    problem1()
}

fun problem1() {
    println(Cat().name)
}

class Cat {
    val name: String by lazy {
        println("I prefer to ignore it")
        callName()
    }

    fun callName(): String {
        println("Input the cat name")
        return readLine()!!
    }
}
