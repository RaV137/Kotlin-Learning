package hyperskill.medium.coffeemachine.stage5

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
    menu()
}

fun menu() {
    while (true) {
        print("Write action (buy, fill, take): ")
        when (readLine()!!) {
            "remaining" -> printMachineState()
            "buy" -> buy()
            "fill" -> fill()
            "take" -> take()
            "exit" -> return
        }
    }
}

fun printMachineState() {
    println(
        """
        
        The coffee machine has:
        ${currentResources["water"]} of water
        ${currentResources["milk"]} of milk
        ${currentResources["coffee"]} of coffee beans
        ${currentResources["cups"]} of disposable cups
        $${currentResources["money"]} of money
        
    """.trimIndent()
    )
}

fun buy() {
    print("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ")
    val choice = readLine()!!
    if (choice == "back") return
    val coffeeOfChoice = coffeeChoice[choice.toInt()]!!
    try {
        doACoffeeUsingResource("water", coffeeOfChoice["water"]!!)
        doACoffeeUsingResource("milk", coffeeOfChoice["milk"]!!)
        doACoffeeUsingResource("coffee", coffeeOfChoice["coffee"]!!)
        doACoffeeUsingResource("cups")
        currentResources["money"] = currentResources["money"]!! + coffeeOfChoice["price"]!!
        println("I have enough resources, making you a coffee!\n")
    } catch (e: IllegalArgumentException) {
        return
    }
}

fun doACoffeeUsingResource(resource: String, requiredResources: Int = 1) {
    val currentResource = currentResources[resource]!!
    if (currentResource < requiredResources) {
        println("Sorry, not enough $resource\n")
        throw IllegalArgumentException()
    }
    currentResources[resource] = currentResource - requiredResources
}

fun fill() {
    print("\nWrite how many ml of water do you want to add: ")
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
    println("I gave you $${currentResources["money"]}\n")
    currentResources["money"] = 0
}
