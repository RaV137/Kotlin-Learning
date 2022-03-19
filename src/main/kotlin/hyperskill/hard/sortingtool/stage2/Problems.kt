package hyperskill.hard.sortingtool.stage2

fun main(args: Array<String>) {
//    problem2()
    problem3(args)
}

fun problem1() {
    val numbers = readLine()!!.split(' ').map { it.toInt() }.toIntArray()
    val tmp = numbers.first()
    numbers[0] = numbers.last()
    numbers[numbers.lastIndex] = tmp
    println(numbers.joinToString(separator = " "))
}

fun problem2() {
    val numbers = IntArray(100)
    numbers[0] = 1
    numbers[9] = 10
    numbers[99] = 100
    println(numbers.joinToString())
}

fun problem3(args: Array<String>) {
    if (args.size != 3) {
        println("Invalid number of program arguments")
    } else {
        repeat(3) { argNum ->
            val arg = args[argNum]
            println("Argument ${argNum + 1}: $arg. It has ${arg.length} characters")
        }
    }
}
