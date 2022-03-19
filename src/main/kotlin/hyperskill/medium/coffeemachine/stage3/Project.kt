package hyperskill.medium.coffeemachine.stage3

const val DEFAULT_WATER = 200
const val DEFAULT_MILK = 50
const val DEFAULT_COFFEE = 15

fun main() {
    val (currWater, currMilk, currCoffee) = readCurrResources()
    print("Write how many cups of coffee you will need: ")
    val wantedCupsOfCoffee = readLine()!!.toInt()
    val maxPossibleCups = howManyCupsCanMachineDo(currWater, currMilk, currCoffee)
    printOutputToUser(wantedCupsOfCoffee, maxPossibleCups)
}

fun readCurrResources(): List<Int> {
    print("Write how many ml of water the coffee machine has: ")
    val currWater = readLine()!!.toInt()
    print("Write how many ml of milk the coffee machine has: ")
    val currMilk = readLine()!!.toInt()
    print("Write how many grams of coffee beans the coffee machine has: ")
    val currCoffee = readLine()!!.toInt()
    return listOf(currWater, currMilk, currCoffee)
}

fun howManyCupsCanMachineDo(water: Int, milk: Int, coffee: Int): Int {
    val cupsOfWater = water / DEFAULT_WATER
    val cupsOfMilk = milk / DEFAULT_MILK
    val cupsOfCoffee = coffee / DEFAULT_COFFEE
    return minOf(cupsOfCoffee, cupsOfMilk, cupsOfWater)
}

fun printOutputToUser(wantedCupsOfCoffee: Int, maxPossibleCups: Int) {
    println(
        if (wantedCupsOfCoffee == maxPossibleCups) "Yes, I can make that amount of coffee"
        else if (maxPossibleCups < wantedCupsOfCoffee) "No, I can make only $maxPossibleCups cups of coffee"
        else "Yes, I can make that amount of coffee (and even ${maxPossibleCups - wantedCupsOfCoffee} more than that)"
    )
}
