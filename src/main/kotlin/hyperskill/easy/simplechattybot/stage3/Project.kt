package hyperskill.easy.simplechattybot.stage3

fun main() {
    println("Hello! My name is Kevin.")
    println("I was created in 2022.")
    println("Please, remind me your name.")
    val name = readLine()!!
    println("What a great name you have, $name!")
    println("Let me guess your age.")
    println("Enter remainders of dividing your age by 3, 5 and 7.")
    val (rem3, rem5, rem7) = List(3) { readLine()!!.toInt() }
    val age = (rem3 * 70 + rem5 * 21 + rem7 * 15) % 105
    println("Your age is $age; that's a good time to start programming!")
}
