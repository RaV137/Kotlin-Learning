package hyperskill.hard.parkinglot.stage5

fun main() {
    problem6()
}

fun reverse(input: Int?): Int = input?.toString()?.reversed()?.toInt() ?: -1

fun problem1() {
    println(reverse(null))
}

// ###############

fun isNumber(input: String): Any = input.toIntOrNull() ?: input

fun problem2() {
    println(isNumber("abc"))
}

// ###############

fun getLength(input: String?): Int = input?.length ?: -1

fun problem3() {
    println(getLength(null))
}

// ###############

class House(_rooms: Int, _price: Int) {
    val coefficient: Double
    val rooms: Int
    val price: Int

    init {
        rooms = _rooms.coerceIn(1, 10)
        price = _price.coerceIn(0, 1_000_000)
        coefficient = when (rooms) {
            1 -> 1.0
            in 2..3 -> 1.2
            4 -> 1.25
            in 5..7 -> 1.4
            else -> 1.6
        }
    }

    fun totalPrice(): Int {
        return (price * coefficient).toInt()
    }
}

fun problem4() {
    val rooms = readLine()!!.toInt()
    val price = readLine()!!.toInt()
    val house = House(rooms, price)
    println(house.totalPrice())
}

// ###############

class Product(_price: Int, productType: String) {
    private val taxRate: Double
    private val price: Int

    init {
        price = _price.coerceIn(0, 1_000_000)
        taxRate = when (productType) {
            "headphones" -> 0.11
            "smartphone" -> 0.15
            "tv" -> 0.17
            else -> 0.19
        }
    }

    fun totalPrice(): Int = (price * (1 + taxRate)).toInt()
}

fun problem5() {
    val productType = readLine()!!
    val price = readLine()!!.toInt()
    val product = Product(price, productType)
    println(product.totalPrice())
}

// ###############

data class Client(val name: String, val age: Int, val balance: Int) {
    override fun equals(other: Any?): Boolean {
        if (other is Client) {
            return name == other.name && age == other.age
        }
        return false
    }
}

fun problem6() {
    val name1 = readLine()!!
    val age1 = readLine()!!.toInt()
    val balance1 = readLine()!!.toInt()
    val name2 = readLine()!!
    val age2 = readLine()!!.toInt()
    val balance2 = readLine()!!.toInt()

    val client1 = Client(name1, age1, balance1)
    val client2 = Client(name2, age2, balance2)

    println(client1 == client2)
}