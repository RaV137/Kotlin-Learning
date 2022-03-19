package hyperskill.easy.simplechattybot.stage4

fun main() {
    greet("Kevin", 2022)
    remindName()
    println("Let me guess your age.")
    println("Enter remainders of dividing your age by 3, 5 and 7.")
    val (rem3, rem5, rem7) = List(3) { readLine()!!.toInt() }
    val age = (rem3 * 70 + rem5 * 21 + rem7 * 15) % 105
    println("Your age is $age; that's a good time to start programming!")
    println("Now I will prove to you that I can count to any number you want.")
    val number = readLine()!!.toInt()
    for (i in 0..number) {
        println("$i!")
    }
    println("Congratulations, have a nice day!")
}

fun greet(name: String, year: Int) {
    println("Hello! My name is $name.")
    println("I was created in $year.")
}

fun remindName() {
    println("Please, remind me your name.")
    val name = readLine()!!
    println("What a great name you have, $name!")
}
