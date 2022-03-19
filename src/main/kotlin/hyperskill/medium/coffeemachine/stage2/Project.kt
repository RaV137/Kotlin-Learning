package hyperskill.medium.coffeemachine.stage2

const val DEFAULT_WATER = 200
const val DEFAULT_MILK = 50
const val DEFAULT_COFFEE = 15

fun main() {
    print("Write how many cups of coffee you will need: ")
    val cupsOfCoffee = readLine()!!.toInt()
    println("""
        For $cupsOfCoffee cups of coffee you will need:
        ${cupsOfCoffee * DEFAULT_WATER} ml of water
        ${cupsOfCoffee * DEFAULT_MILK} ml of milk
        ${cupsOfCoffee * DEFAULT_COFFEE} g of coffee beans
    """.trimIndent())
}
