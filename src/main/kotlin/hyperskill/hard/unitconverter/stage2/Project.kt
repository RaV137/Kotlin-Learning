package hyperskill.hard.unitconverter.stage2

interface Converter {
    fun shouldUseThisConverter(unitName: String): Boolean

    fun convert(units: Int)
}

class KilosToMetersConverter : Converter {
    companion object {
        private const val ABBREVIATION_NAME = "km"
        private const val SINGULAR_NAME = "kilometer"
        private const val FULL_NAME = "kilometers"
        const val conversionRate: Int = 1000
        val unitNames = listOf(ABBREVIATION_NAME, SINGULAR_NAME, FULL_NAME)
    }

    override fun convert(units: Int) {
        val result = units * conversionRate
        val unitName = if (units == 1) SINGULAR_NAME else FULL_NAME
        println("$units $unitName is $result meters")
    }

    override fun shouldUseThisConverter(unitName: String): Boolean = unitName.lowercase() in unitNames
}

class InvalidConverter : Converter {
    override fun shouldUseThisConverter(unitName: String): Boolean = false
    override fun convert(units: Int) = println("Wrong input")
}

val converters = listOf<Converter>(KilosToMetersConverter())

fun main() {
    print("Enter a number and a measure: ")
    val (strValue, unitName) = readLine()!!.split(" ")
    val converter = converters
        .filter { it.shouldUseThisConverter(unitName) }
        .getOrElse(0) { InvalidConverter() }
    converter.convert(strValue.toInt())
}
