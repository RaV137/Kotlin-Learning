package hyperskill.challenging.smartcalculator.stage8

fun main() {
    problem7()
}

fun problem1() {
    println(solution1(setOf(8, 11, 12, 13), setOf(8, 12)))
}

fun solution1(numbers1: Set<Int>, numbers2: Set<Int>): Int {
    val newSet = numbers1.toMutableSet()
    newSet.removeAll(numbers2)
    return newSet.size
}


fun problem2() {
    println(solution2(mutableListOf(1, 2, 3, 4, 1)).joinToString())
}

fun solution2(ints: MutableList<Int>): Set<Int> {
    return ints.toSet()
}


fun problem3() {
    println(solution3(setOf("Hello", "it's", "you", "me"), "you").joinToString())
}

fun solution3(elements: Set<String>, element: String): MutableSet<String> {
    return elements.toMutableSet().apply { remove(element) }
}


fun problem4() {
    println(solution4(setOf("A", "B", "C"), mutableListOf("A", "B", "B", "D", "C")).joinToString())
}

fun solution4(setSource: Set<String>, listSource: MutableList<String>): MutableSet<String> {
    return setSource.toMutableSet().apply { addAll(listSource) }
}


fun problem5() {
    println(solution5(setOf(10, 11, 14, 16, 2, 1), setOf(2, 1)).joinToString())
}

fun solution5(first: Set<Int>, second: Set<Int>): Set<Int> {
    return first.toMutableSet().filter { it % second.size == 0 }.toSet()
}


fun problem6() {
    println(solution6(mutableSetOf(5, 6, 7), 0).joinToString())
}

fun solution6(elements: MutableSet<Int>, element: Int): MutableSet<Int> {
    return if (element in elements) mutableSetOf() else elements
}


class Box<T>(private val furniture: T, private val volume: Int) {
    fun getBoxVolume() = volume
    fun getFurnitureFromBox() = furniture
}

class QuizBox<T>(_item: T) {
    var isChanged = false
    var item: T = _item
        get() {
            println("You asked for the item")
            return field
        }
        set(value) {
            field = value
            println("You changed the item")
            isChanged = true
        }
}

class MyStack<T>(data: List<T>) {
    private val items = data.toMutableList()

    fun push(data: T) {
        items.add(data)
    }

    fun pop(): T {
        return items.removeAt(items.lastIndex)
    }
}


fun problem7() {
    println(ListUtils.info(listOf("a", "b", "c")))
    println(ListUtils.info(listOf<String>()))
}

class ListUtils {
    companion object Info {
        fun <T> info(list: List<T>): String {
            return "[" + list.joinToString() + "]"
        }
    }
}

fun <T : Comparable<T>> getBigger(a: T, b: T): T {
    return maxOf(a, b)
}

fun <T> countItem(list: List<T>, element: T): Int {
    return list.count { it == element }
}

class SmartKitchen<T>
        where T : Cooker, T : Logger {
    fun finishCooking(obj: T) {
        obj.log()
    }
}

// don't change it!
interface Logger {
    fun log()
}

interface Cooker {
    fun cook()
}

fun problem8() {
    val box = Boxx(listOf("lion", "rose"))
    println(box.getSomethingFromBox().size)
}

class BiggerBox {
    class InnerBox<T>(var items: List<T>) {
        fun getSomethingFromBox(): List<T> {
            return items
        }
    }
}

typealias Boxx<T> = BiggerBox.InnerBox<T>


