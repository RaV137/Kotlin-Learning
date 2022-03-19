package hyperskill.medium.coffeemachine.stage6

// Nie jestem zadowolony z tego rozwiązania - działa, ale jest brzydkie
// TODO: dla przyszłego mnie - po przejściu dalszych tematów dot. Kotlina na platformie Hyperskill, poprawić ten kod

enum class Coffee(val id: Int, val water: Int, val milk: Int, val coffee: Int, val price: Int) {
    ESPRESSO(id = 1, water = 250, milk = 0, coffee = 16, price = 4),
    LATTE(id = 2, water = 350, milk = 75, coffee = 20, price = 7),
    CAPPUCCINO(id = 3, water = 200, milk = 100, coffee = 12, price = 6);

    companion object {
        fun findById(id: Int): Coffee = values().find { it.id == id } ?: throw IllegalArgumentException()
    }
}

enum class CoffeeMachineState {
    CHOOSING_ACTION, CHOOSING_COFFEE, ADDING_WATER, ADDING_MILK, ADDING_COFFEE, ADDING_CUPS
}

class CoffeeMachine {
    private var water = 400
    private var milk = 540
    private var coffee = 120
    private var cups = 9
    private var money = 550
    private var state = CoffeeMachineState.CHOOSING_ACTION

    fun handleInput(input: String): String {
        return when (state) {
            CoffeeMachineState.CHOOSING_ACTION -> handleChoosingInput(input)
            CoffeeMachineState.CHOOSING_COFFEE -> chooseCoffee(input)
            CoffeeMachineState.ADDING_WATER -> addWater(input)
            CoffeeMachineState.ADDING_MILK -> addMilk(input)
            CoffeeMachineState.ADDING_COFFEE -> addCoffee(input)
            CoffeeMachineState.ADDING_CUPS -> addCups(input)
        }
    }

    private fun handleChoosingInput(input: String): String {
        return when (input) {
            "remaining" -> printMachineState()
            "buy" -> buy()
            "fill" -> fill()
            "take" -> take()
            else -> "" // TODO
        }
    }

    private fun printMachineState(): String {
        return """
        
        The coffee machine has:
        $water of water
        $milk of milk
        $coffee of coffee beans
        $cups of disposable cups
        $$money of money
        
        Write action (buy, fill, take, remaining, exit): 
    """.trimIndent()
    }

    private fun buy(): String {
        state = CoffeeMachineState.CHOOSING_COFFEE
        return "\nWhat do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: "
    }

    private fun fill(): String {
        state = CoffeeMachineState.ADDING_WATER
        return "\nWrite how many ml of water do you want to add: "
    }

    private fun take(): String {
        val takenMoney = money
        money = 0
        return "I gave you $$takenMoney\n\nWrite action (buy, fill, take, remaining, exit): "
    // FIXME: znaleźć sposób, by nie doklejać tej linii (write action ...) niemal wszędzie
    }

    private fun chooseCoffee(coffeeType: String): String {
        state = CoffeeMachineState.CHOOSING_ACTION

        if (coffeeType == "back") return "\nWrite action (buy, fill, take, remaining, exit): "

        val coffeeToMake = Coffee.findById(coffeeType.toInt())
        try {
            checkRequiredResources(coffeeToMake)
        } catch (e: IllegalArgumentException) {
            return e.message!! + "\n\nWrite action (buy, fill, take, remaining, exit): "
        }

        water -= coffeeToMake.water
        milk -= coffeeToMake.milk
        coffee -= coffeeToMake.coffee
        money += coffeeToMake.price
        cups--

        return "I have enough resources, making you a coffee!\n\nWrite action (buy, fill, take, remaining, exit): "
    }

    private fun checkRequiredResources(coffeeToMake: Coffee) {
        when {
            coffeeToMake.water > water -> throw IllegalArgumentException("Sorry, not enough water!")
            coffeeToMake.milk > milk -> throw IllegalArgumentException("Sorry, not enough milk!")
            coffeeToMake.coffee > coffee -> throw IllegalArgumentException("Sorry, not enough coffee!")
            cups < 1 -> throw IllegalArgumentException("Sorry, not enough cups!")
        }
    }

    private fun addWater(addedWater: String): String {
        state = CoffeeMachineState.ADDING_MILK
        water += addedWater.toInt()
        return "Write how many ml of milk do you want to add: "
    }

    private fun addMilk(addedMilk: String): String {
        state = CoffeeMachineState.ADDING_COFFEE
        milk += addedMilk.toInt()
        return "Write how many grams of coffee beans do you want to add: "
    }

    private fun addCoffee(addedCoffee: String): String {
        state = CoffeeMachineState.ADDING_CUPS
        coffee += addedCoffee.toInt()
        return "Write how many disposable cups of coffee do you want to add: "
    }

    private fun addCups(addedCups: String): String {
        state = CoffeeMachineState.CHOOSING_ACTION
        cups += addedCups.toInt()
        return "\nWrite action (buy, fill, take, remaining, exit): "
    }
}

fun main() {
    val coffeeMachine = CoffeeMachine()
    print("Write action (buy, fill, take, remaining, exit): ")
    while (true) {
        val input = readLine()!!
        if (input == "exit") {
            return
        }

        print(coffeeMachine.handleInput(input))
    }
}
