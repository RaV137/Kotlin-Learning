package hyperskill.easy.numberconverter.stage1

fun main() {
    print("Enter number in decimal system: ")
    val decimal = readLine()!!.toInt()
    print("Enter target base: ")
    val result = when (readLine()!!.toInt()) {
        2 -> Integer.toBinaryString(decimal)
        8 -> Integer.toOctalString(decimal)
        16 -> Integer.toHexString(decimal)
        else -> -1
    }
    println("Conversion result: $result")
}
