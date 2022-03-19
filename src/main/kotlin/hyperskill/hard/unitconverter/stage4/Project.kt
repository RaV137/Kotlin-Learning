package hyperskill.hard.unitconverter.stage4

abstract class Converter(
    private val conversionRate: Double,
    abbreviationName: String,
    private val singleName: String,
    private val fullName: String
) {
    private val unitNames = listOf(abbreviationName, singleName, fullName)

    fun shouldUseThisConverter(unitName: String): Boolean = unitName.lowercase() in unitNames

    fun convertToBaseUnit(from: Double): Pair<Double, String> {
        val resultUnitName = if (from == 1.0) singleName else fullName
        val result = from * conversionRate
        return Pair(result, resultUnitName)
    }

    fun convertFromBaseUnit(from: Double): Pair<Double, String> {
        val result = from / conversionRate
        val resultUnitName = if (result == 1.0) singleName else fullName
        return Pair(result, resultUnitName)
    }

    fun getDefaultName() = fullName
}

abstract class LengthConverter(
    conversionRate: Double,
    abbreviationName: String,
    singleName: String,
    fullName: String
) : Converter(conversionRate, abbreviationName, singleName, fullName)

class MetersConverter : LengthConverter(1.0, "m", "meter", "meters")
class KilometersConverter : LengthConverter(1000.0, "km", "kilometer", "kilometers")
class CentimetersConverter : LengthConverter(0.01, "cm", "centimeter", "centimeters")
class MillimetersConverter : LengthConverter(0.001, "mm", "millimeter", "millimeters")
class MilesConverter : LengthConverter(1609.35, "mi", "mile", "miles")
class YardsConverter : LengthConverter(0.9144, "yd", "yard", "yards")
class FeetConverter : LengthConverter(0.3048, "ft", "foot", "feet")
class InchesConverter : LengthConverter(0.0254, "in", "inch", "inches")

abstract class WeightConverter(
    conversionRate: Double,
    abbreviationName: String,
    singleName: String,
    fullName: String
) : Converter(conversionRate, abbreviationName, singleName, fullName)

class GramsConverter : WeightConverter(1.0, "g", "gram", "grams")
class KilogramsConverter : WeightConverter(1000.0, "kg", "kilogram", "kilograms")
class MilligramsConverter : WeightConverter(0.001, "mg", "milligram", "milligrams")
class PoundsConverter : WeightConverter(453.592, "lb", "pound", "pounds")
class OuncesConverter : WeightConverter(28.3495, "oz", "ounce", "ounces")

val converters = listOf(
    KilometersConverter(),
    MetersConverter(),
    CentimetersConverter(),
    MillimetersConverter(),
    MilesConverter(),
    YardsConverter(),
    FeetConverter(),
    InchesConverter(),
    GramsConverter(),
    KilogramsConverter(),
    MilligramsConverter(),
    PoundsConverter(),
    OuncesConverter(),
)

fun findConverterPerUnitName(unitName: String): Converter? = converters.find { it.shouldUseThisConverter(unitName) }

fun convert(value: Double, fromConverter: Converter, toConverter: Converter) {
    val (baseValue, fromUnitName) = fromConverter.convertToBaseUnit(value)
    val (result, toUnitName) = toConverter.convertFromBaseUnit(baseValue)
    println("$value $fromUnitName is $result $toUnitName")
}

fun checkConverters(fromConverter: Converter?, toConverter: Converter?): Boolean {
    val fromConverterNull = fromConverter == null
    val toConverterNull = toConverter == null

    return if (fromConverterNull || toConverterNull) {
        val fromStr = if (fromConverterNull) "???" else fromConverter?.getDefaultName()
        val toStr = if (toConverterNull) "???" else toConverter?.getDefaultName()
        println("Conversion from $fromStr to $toStr is impossible")
        false
    } else if (fromConverter is WeightConverter && toConverter is LengthConverter
        || fromConverter is LengthConverter && toConverter is WeightConverter
    ) {
        println("Conversion from ${fromConverter.getDefaultName()} to ${toConverter.getDefaultName()} is impossible")
        false
    } else {
        true
    }
}

fun main() {
    while (true) {
        println()
        print("Enter what you want to convert (or exit): ")
        val input = readLine()!!
        if (input == "exit") return

        val splittedInput = input.split(" ")
        val value = splittedInput[0].toDouble()
        val fromUnitName = splittedInput[1]
        val toUnitName = splittedInput[3]

        val fromConverter = findConverterPerUnitName(fromUnitName)
        val toConverter = findConverterPerUnitName(toUnitName)

        if (!checkConverters(fromConverter, toConverter)) continue

        convert(value, fromConverter!!, toConverter!!)
    }
}
