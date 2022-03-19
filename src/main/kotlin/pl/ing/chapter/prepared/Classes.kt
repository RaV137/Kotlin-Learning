package pl.ing.chapter.prepared

import java.util.concurrent.atomic.AtomicInteger

class Pet(val name: String)

class Animal(_name: String) {
    private val name = _name

    fun printName() {
        println("Animal name: $name")
    }
}

object Singleton {
    private const val value = 10

    fun printValue() {
        println("Singleton value: $value")
    }
}

data class Employee(val name: String, val salary: Int)

class CustomStringPair(val left: String, val right: String) {
    companion object {
        const val DELIMITER = "mapped to"
    }

    override fun toString(): String {
        return "$left $DELIMITER $right"
    }
}

class ConstructorExample(_value: Int) {
    val value = _value

    constructor(strValue: String) : this(strValue.toInt())
}

class OneElementStringList(element: String) {
    val list: List<String>

    init {
        list = listOf(element)
    }
}

class Rectangle(val width: Int, val height: Int) {
    val area: Int
        get() {
            println("Getting area from Rectangle")
            return width * height
        }

    var name = "default"
        set(_name) {
            println("Setting name of Rectangle")
            field = _name
        }
}

class LazyClass {
    val numberOfInitializations: AtomicInteger = AtomicInteger()
    val lazyProperty: String by lazy {
        numberOfInitializations.incrementAndGet()
        println("Setting heavy property or sth")
        "heavy"
    }

    lateinit var lateProperty: String
}

open class CannotInherit(val someValue: String) {
    fun fun1() {
        println("CannotInherit#fun1()")
    }

    open fun fun2() {
        println("CannotInherit#fun2()")
    }
}

class TryingToInherit(str: String) : CannotInherit(str) {
    override fun fun2() {
        println("TryingToInherit#fun2()")
    }

//    override fun fun1() {
//        println("TryingToInherit#fun1()")
//    }
}

fun main() {
    val pet = Pet("Szarik")
    println(pet.name)

    val animal = Animal("Burek")
    animal.printName()

    val animal2 = Animal("Stefan").apply { printName() }
    animal2.printName()

    Singleton.printValue()

    val employee = Employee(name = "Wacław", salary = 15_000)
    println(pet)
    println(employee)
//    employee.name = "aa"

    val pair = CustomStringPair("left val", "right val")
    println(pair)

    val obj = ConstructorExample(10)
    println(obj.value)

    val obj1 = ConstructorExample("150")
    println(obj1.value)

    val list = OneElementStringList("abc")
    println(list.list)

    val rect = Rectangle(10, 5)
    println(rect.area)
    rect.name = "Cool rectangle"
    println(rect.name)

    val lazyObj = LazyClass()
    println(lazyObj.lazyProperty)
    println(lazyObj.lazyProperty)
    println("Number of invocations: ${lazyObj.numberOfInitializations}")

    try {
        println(lazyObj.lateProperty)
    } catch (e: UninitializedPropertyAccessException) {
        println("Cannot get lateProperty because it was not initialised")
    }
    lazyObj.lateProperty = "abc"
    println(lazyObj.lateProperty)

    val inherited = TryingToInherit("abc")
    inherited.fun1()
    inherited.fun2()
}
