package hyperskill.medium.asciitextsignature.stage2

fun main() {
    val name = readName()
    printTag(name)
}

fun readName(): String {
    return readLine()!!
}

fun printTag(name: String) {
    println("*".repeat(name.length + 4))
    println("* $name *")
    println("*".repeat(name.length + 4))
}