package hyperskill.easy.numberconverter.stage3

const val EXIT_INPUT = "/exit"
const val BACK_INPUT = "/back"

fun main() {
    do {
        print("Enter two numbers in format: {source base} {target base} (To quit type $EXIT_INPUT) ")
        val input = readLine()!!
        if (input == EXIT_INPUT) {
            return
        } else {
            val (sourceBase, targetBase) = input.split(" ").map { it.toInt() }
            convert(sourceBase, targetBase)
        }
    } while (true)
}

fun convert(sourceBase: Int, targetBase: Int) {
    do {
        print("Enter number in base $sourceBase to convert to base $targetBase (To go back type $BACK_INPUT) ")
        val input = readLine()!!
        if (input == BACK_INPUT) {
            return
        } else {
            val decimal = input.toBigInteger(radix = sourceBase)
            val converted = decimal.toString(targetBase)
            println("Conversion result: $converted")
        }
    } while (true)

}
