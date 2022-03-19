package hyperskill.easy.simplechattybot.stage5

fun main() {
    greet("Kevin", 2022)
    remindName()
    guessAge()
    count()
    quiz()
    end()
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

fun guessAge() {
    println("Let me guess your age.")
    println("Enter remainders of dividing your age by 3, 5 and 7.")
    val (rem3, rem5, rem7) = List(3) { readLine()!!.toInt() }
    val age = (rem3 * 70 + rem5 * 21 + rem7 * 15) % 105
    println("Your age is $age; that's a good time to start programming!")
}

fun count() {
    println("Now I will prove to you that I can count to any number you want.")
    val number = readLine()!!.toInt()
    for (i in 0..number) {
        println("$i!")
    }
}

fun quiz() {
    println("Let's test your programming knowledge.")
    val correctAnswer = 2
    println("What is the meaning of life?")
    println("1. Love")
    println("2. 42")
    println("3. Friends")
    println("4. Money")
    while (true) {
        val answer = readLine()!!.toInt()
        if (answer != correctAnswer) {
            println("Please, try again.")
        } else {
            break
        }
    }
}

fun end() {
    println("Congratulations, have a nice day!")
}
