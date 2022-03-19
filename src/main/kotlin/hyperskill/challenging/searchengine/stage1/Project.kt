package hyperskill.challenging.searchengine.stage1

class DataHolder {
    val data = mutableListOf<String>()

    fun add(str: String) {
        data.add(str)
    }

    fun add(strings: List<String>) {
        data.addAll(strings)
    }

    fun search(str: String) {
        data.indexOf(str).let { println(if (it >= 0) it + 1 else "Not found") }
    }
}

fun main() {
    val dataHolder = DataHolder()
    val input = readLine()!!.split(" ")
    dataHolder.add(input)

    val searched = readLine()!!

    dataHolder.search(searched)
}