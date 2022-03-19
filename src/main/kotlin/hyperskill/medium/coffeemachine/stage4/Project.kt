package hyperskill.medium.coffeemachine.stage4

val currentResources = mutableMapOf(
    "water" to 400,
    "milk" to 540,
    "coffee" to 120,
    "cups" to 9,
    "money" to 550
)

val coffeeChoice = mutableMapOf(
    1 to mutableMapOf( // espresso
        "water" to 250,
        "milk" to 0,
        "coffee" to 16,
        "price" to 4
    ),
    2 to mutableMapOf( // latte
        "water" to 350,
        "milk" to 75,
        "coffee" to 20,
        "price" to 7
    ),
    3 to mutableMapOf( // cappuccino
        "water" to 200,
        "milk" to 100,
        "coffee" to 12,
        "price" to 6
    ),
)


fun main() {
    printMachineState()
    action()
    printMachineState()
}

fun printMachineState() {
    println(
        """
        
        The coffee machine has:
        ${currentResources["water"]} of water
        ${currentResources["milk"]} of milk
        ${currentResources["coffee"]} of coffee beans
        ${currentResources["cups"]} of disposable cups
        ${currentResources["money"]} of money
        
    """.trimIndent()
    )
}

fun action() {
    print("Write action (buy, fill, take): ")
    when (readLine()!!) {
        "buy" -> buy()
        "fill" -> fill()
        "take" -> take()
    }
}

fun buy() {
    print("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino: ")
    val choice = readLine()!!.toInt()
    val coffeeOfChoice = coffeeChoice[choice]!!
    currentResources["water"] = currentResources["water"]!! - coffeeOfChoice["water"]!!
    currentResources["milk"] = currentResources["milk"]!! - coffeeOfChoice["milk"]!!
    currentResources["coffee"] = currentResources["coffee"]!! - coffeeOfChoice["coffee"]!!
    currentResources["money"] = currentResources["money"]!! + coffeeOfChoice["price"]!!
    currentResources["cups"] = currentResources["cups"]!! - 1
}

fun fill() {
    print("Write how many ml of water do you want to add: ")
    val addedWater = readLine()!!.toInt()
    print("Write how many ml of milk do you want to add: ")
    val addedMilk = readLine()!!.toInt()
    print("Write how many grams of coffee beans do you want to add: ")
    val addedCoffee = readLine()!!.toInt()
    print("Write how many disposable cups of coffee do you want to add: ")
    val addedCups = readLine()!!.toInt()

    currentResources["water"] = currentResources["water"]!! + addedWater
    currentResources["milk"] = currentResources["milk"]!! + addedMilk
    currentResources["coffee"] = currentResources["coffee"]!! + addedCoffee
    currentResources["cups"] = currentResources["cups"]!! + addedCups
}

fun take() {
    println("I gave you $${currentResources["money"]}")
    currentResources["money"] = 0
}
