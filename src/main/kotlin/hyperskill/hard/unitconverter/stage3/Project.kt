package hyperskill.hard.unitconverter.stage3

abstract class ToMetersConverter(
    private val conversionRate: Double,
    abbreviationName: String,
    private val singleName: String,
    private val fullName: String
) {
    private val unitNames = listOf(abbreviationName, singleName, fullName)

    fun shouldUseThisConverter(unitName: String): Boolean = unitName.lowercase() in unitNames

    fun convert(units: Double) {
        val result = units * conversionRate
        val unitName = if (units == 1.0) singleName else fullName
        val resultUnitName = if (result == 1.0) "meter" else "meters"
        println("$units $unitName is $result $resultUnitName")
    }
}

class MetersToMetersConverter : ToMetersConverter(1.0, "m", "meter", "meters")
class KilosToMetersConverter : ToMetersConverter(1000.0, "km", "kilometer", "kilometers")
class CentimetersToMetersConverter : ToMetersConverter(0.01, "cm", "centimeter", "centimeters")
class MillimetersToMetersConverter : ToMetersConverter(0.001, "mm", "millimeter", "millimeters")
class MilesToMetersConverter : ToMetersConverter(1609.35, "mi", "mile", "miles")
class YardsToMetersConverter : ToMetersConverter(0.9144, "yd", "yard", "yards")
class FeetToMetersConverter : ToMetersConverter(0.3048, "ft", "foot", "feet")
class InchesToMetersConverter : ToMetersConverter(0.0254, "in", "inch", "inches")

val converters = listOf(
    KilosToMetersConverter(),
    MetersToMetersConverter(),
    CentimetersToMetersConverter(),
    MillimetersToMetersConverter(),
    MilesToMetersConverter(),
    YardsToMetersConverter(),
    FeetToMetersConverter(),
    InchesToMetersConverter(),
)

fun main() {
    print("Enter a number and a measure of length: ")
    val (strValue, unitName) = readLine()!!.split(" ")
    val converter = converters.find { it.shouldUseThisConverter(unitName) }
    if (converter == null) {
        println("Wrong input. Unknown unit $unitName")
    } else {
        converter.convert(strValue.toDouble())
    }
}
