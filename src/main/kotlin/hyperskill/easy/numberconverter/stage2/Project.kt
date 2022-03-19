package hyperskill.easy.numberconverter.stage2

const val EXIT_INPUT = "/exit"
const val FROM_INPUT = "/from"
const val TO_INPUT = "/to"

fun main() {
    do {
        print("Do you want to convert /from decimal or /to decimal? (To quit type /exit) ")
        val input = readLine()!!
        when (input) {
            FROM_INPUT -> fromDecimal()
            TO_INPUT -> toDecimal()
        }
    } while (input != EXIT_INPUT)
}

fun fromDecimal() {
    print("Enter a number in decimal system: ")
    val decimal = readLine()!!.toInt()
    print("Enter the target base: ")
    val result = when (readLine()!!.toInt()) {
        2 -> Integer.toBinaryString(decimal)
        8 -> Integer.toOctalString(decimal)
        16 -> Integer.toHexString(decimal)
        else -> "-1"
    }
    println("Conversion result: $result")
}

fun toDecimal() {
    print("Enter source number: ")
    val number = readLine()!!
    print("Enter source base: ")
    val base = readLine()!!.toInt()
    val decimal = number.toLong(radix = base).toString()
    println("Conversion to decimal result: $decimal")
}
