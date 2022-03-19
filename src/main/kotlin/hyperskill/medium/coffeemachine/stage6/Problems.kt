package hyperskill.medium.coffeemachine.stage6

import kotlin.math.hypot

fun main() {
    problem13()
}

// ######################

class OperatingSystem {
    var name: String = "Windows 11"
}

class DualBoot {
    var primaryOs: OperatingSystem = OperatingSystem()
    var secondaryOs: OperatingSystem = OperatingSystem()
}

// ######################

class Point3D {
    var x: Int = 0
    var y: Int = 0
    var z: Int = 0
}

fun createPoint(x: Int, y: Int, z: Int): Point3D {
    val point = Point3D()
    point.x = x
    point.y = y
    point.z = z
    return point
}

// ######################

fun problem1() {
    println(f(readLine()!!.toDouble()))
}

fun f(x: Double): Double {
    return when {
        x <= 0 -> f1(x)
        x >= 1 -> f2(x)
        else -> f3(x)
    }
}

// implement your functions here
fun f1(x: Double): Double = x * x + 1

fun f2(x: Double): Double = x * x - 1

fun f3(x: Double): Double = 1 / (x * x)

// ######################

fun problem2() {
    val name = readLine()!!
    if (name == "HIDDEN") {
        greeting()
    } else {
        greeting(name)
    }
}

fun greeting(name: String = "secret user") = println("Hello, $name!")

// ######################

fun problem3() {
    println(concatenate("Secret", "Super", "String"))
}

fun concatenate(s1: String, s2: String, s3: String, separator: String = " "): String = "$s1$separator$s2$separator$s3"

// ######################

fun problem4() {
    println(carPrice())
}

fun carPrice(old: Int = 5, kilometers: Int = 100_000, maximumSpeed: Int = 120, automatic: Boolean = false) {
    val initialPrice = 20_000
    val agePrice = -old * 2_000
    val maxSpeedDiff = kotlin.math.abs(maximumSpeed - 120)
    val maxSpeedPrice = (if (maximumSpeed >= 120) maxSpeedDiff else -maxSpeedDiff) * 100
    val automaticPrice = if (automatic) 1_500 else 0
    val kilometersPrice = kilometers / 10_000 * -200
    println(initialPrice + agePrice + maxSpeedPrice + automaticPrice + kilometersPrice)
}

// ######################

fun problem5() {
    println(perimeter(0.0, 0.0, 12.0, 0.0, 0.0, 5.0))
}

fun perimeter(x1: Double, y1: Double, x2: Double, y2: Double, x3: Double, y3: Double, x4: Double = 0.0, y4: Double = 0.0): Double {
    return if (x4 == 0.0 && y4 == 0.0) {
        hypot(x2 - x1, y2 - y1) + hypot(x3 - x2, y3 - y2) + hypot(x3 - x1, y3 - y1)
    } else {
        hypot(x2 - x1, y2 - y1) + hypot(x3 - x2, y3 - y2) + hypot(x4 - x3, y4 - y3) + hypot(x1 - x4, y1 - y4)
    }
}

// ######################

fun problem6() {
    val parameter = readLine()!!
    val value = readLine()!!.toInt()
    println(
        when (parameter) {
            "amount" -> computeCompoundInterest(amount = value)
            "percent" -> computeCompoundInterest(percent = value)
            "years" -> computeCompoundInterest(years = value)
            else -> computeCompoundInterest()
        }
    )
}

fun computeCompoundInterest(amount: Int = 1_000, percent: Int = 5, years: Int = 10): Int {
    return (amount * Math.pow(1 + percent / 100.0, years.toDouble())).toInt()
}

// ######################

fun problem7() {
    var number = readLine()!!.toInt()
    var sum = 0
    repeat(3) {
        sum += number % 10
        number /= 10
    }
    println(sum)
}

// ######################

class City1(val name: String) {
    var population: Int = 0
        set(value) {
            field = if (value < 0) {
                0
            } else if (value > 50_000_000) {
                50_000_000
            } else {
                value
            }
        }
}

// ######################

class City(val name: String, val defaultDegrees: Int) {
    var degrees: Int = defaultDegrees
        set(value) {
            field = if (value > 57 || value < -92) defaultDegrees else value
        }
}

fun problem8() {
    val first = readLine()!!.toInt()
    val second = readLine()!!.toInt()
    val third = readLine()!!.toInt()
    val firstCity = City("Dubai", 30)
    firstCity.degrees = first
    val secondCity = City("Moscow", 5)
    secondCity.degrees = second
    val thirdCity = City("Hanoi", 20)
    thirdCity.degrees = third

    val citiesList = listOf(firstCity, secondCity, thirdCity)
    val coldestCity = citiesList.sortedBy { it.degrees }[0]
    val coldestCityName = if (citiesList.filter { it.degrees == coldestCity.degrees }.size > 1) {
        "neither"
    } else {
        coldestCity.name
    }
    println(coldestCityName)
}

// ######################

class ChristmasTree(var color: String) {
    fun putTreeTopper(color: String) {
        val topper = TreeTopper(color)
        topper.sparkle()
    }

    inner class TreeTopper(var color: String) {
        fun sparkle() = println("The sparkling ${this.color} tree topper looks stunning on the ${this@ChristmasTree.color} Christmas tree!")
    }
}

fun problem9() {
    val tree = ChristmasTree("red")
    tree.putTreeTopper("green")
}

// ######################

class Intern(val weeklyWorkload: Int) {
    val baseWorkload = 20

    class Salary {
        val basePay = 50
        val extraHoursPay = 2.8
    }

    val weeklySalary: Double = Salary().basePay + (weeklyWorkload - baseWorkload) * Salary().extraHoursPay
}

fun problem10() {
    val intern1 = Intern(20)
    val intern2 = Intern(40)

    println(intern1.weeklySalary)
    println(intern2.weeklySalary)
}

// ######################

class Employee(val clothesSize: Int) {
    class Uniform {
        val uniformType = "suit"
        val uniformColor = "blue"
    }

    fun printUniformInfo() {
        with(Uniform()) {
            println("The employee wears a $uniformColor $uniformType in size $clothesSize")
        }
    }
}

fun problem11() {
    val emp = Employee(36)
    emp.printUniformInfo()
}

// ######################

class Hero {
    val baseStrength = 1000

    class Equipment {
        val weapon = "trident"
        val weaponStrength = 300
    }
}

class Villain {
    val baseStrength = 500

    class Equipment {
        val weapon = "bomb"
        val weaponStrength = 700
    }
}

fun problem12() {
    val heroEquipment = Hero.Equipment()
    val heroTotalStrength = Hero().baseStrength + heroEquipment.weaponStrength

    val villainEquipment = Villain.Equipment()
    val villainTotalStrength = Villain().baseStrength + villainEquipment.weaponStrength

    // the following lines should remain as they are
    println("The hero uses ${heroEquipment.weapon}. The total strength is $heroTotalStrength.")
    println("The villain uses ${villainEquipment.weapon}. The total strength is $villainTotalStrength.")
}

// ######################

class Task(val name: String)

object Manager {
    var solvedTask = 0

    fun solveTask(task: Task) {
        println("Task ${task.name} solved!")
        solvedTask++
    }
}

// ######################

class Block(val color: String) {
    object BlockProperties {
        var length = 6
        var width = 4

        fun blocksInBox(boxLength: Int, boxWidth: Int): Int {
            val blocksInBoxLength = boxLength / length
            val blocksInBoxWidth = boxWidth / width
            return blocksInBoxLength * blocksInBoxWidth
        }
    }
}

// ######################

class Player(val id: Int, val name: String) {
    companion object {
        var role = "playable character"
    }

    fun getInfo() = "$id, $name, $role"
}

fun getPlayerInfo(player: Player) = player.getInfo()

// ######################

class Player1(val id: Int, val name: String, val hp: Int) {
    companion object {
        var id = 0
        private const val defaultHp = 100

        fun create(name: String): Player1 = Player1(id++, name, defaultHp)
    }
}

// ######################

enum class Rainbow {
    RED, ORANGE, YELLOW, GREEN, BLUE, INDIGO, VIOLET
}

fun problem13() {
    val color = readLine()!!
    val rainbowColor = Rainbow.valueOf(color.uppercase())
    println(rainbowColor.ordinal + 1)

}

// ######################

class IceCreamOrder {
    val price: Int

    constructor (popsicles: Int) {
        this.price = 7 * popsicles
    }

    constructor(scoops: Int, toppings: Int) {
        this.price = 5 * scoops + 2 * toppings
    }
}

// ######################

class EspressoMachine {
    val costPerServing: Float

    constructor(coffeeCapsulesCount: Int, totalCost: Float) {
        costPerServing = totalCost / coffeeCapsulesCount
    }

    constructor(coffeeBeansWeight: Float, totalCost: Float) {
        costPerServing = totalCost / (coffeeBeansWeight / 10)
    }
}

// ######################

class Fabric(var color: String) {
    var pattern: String = "none"
    var patternColor: String = "none"

    init {
        println("$color fabric is created")
    }

    constructor(color: String, pattern: String, patternColor: String) : this(color) {
        this.pattern = pattern
        this.patternColor = patternColor

        println("$patternColor $pattern pattern is printed on the fabric")
    }
}
