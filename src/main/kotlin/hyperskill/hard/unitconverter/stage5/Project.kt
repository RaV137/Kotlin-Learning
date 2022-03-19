package hyperskill.hard.unitconverter.stage5

enum class ConverterType {
    LENGTH, WEIGHT, TEMPERATURE
}

abstract class Converter(
    private val toBaseConversion: (Double) -> Double,
    private val fromBaseConversion: (Double) -> Double,
    private val unitNames: List<String>,
    private val singleName: String,
    private val fullName: String,
    val type: ConverterType
) {
    fun shouldUseThisConverter(unitName: String): Boolean = unitName.lowercase() in unitNames

    fun convertToBaseUnit(from: Double): Pair<Double, String> {
        val resultUnitName = if (from == 1.0) singleName else fullName
        val result = toBaseConversion(from)
        return Pair(result, resultUnitName)
    }

    fun convertFromBaseUnit(from: Double): Pair<Double, String> {
        val result = fromBaseConversion(from)
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
) : Converter(
    { value -> conversionRate * value },
    { value -> value / conversionRate },
    listOf(abbreviationName, singleName, fullName),
    singleName,
    fullName,
    ConverterType.LENGTH
)

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
) : Converter(
    { value -> conversionRate * value },
    { value -> value / conversionRate },
    listOf(abbreviationName, singleName, fullName),
    singleName,
    fullName,
    ConverterType.WEIGHT
)

class GramsConverter : WeightConverter(1.0, "g", "gram", "grams")
class KilogramsConverter : WeightConverter(1000.0, "kg", "kilogram", "kilograms")
class MilligramsConverter : WeightConverter(0.001, "mg", "milligram", "milligrams")
class PoundsConverter : WeightConverter(453.592, "lb", "pound", "pounds")
class OuncesConverter : WeightConverter(28.3495, "oz", "ounce", "ounces")

abstract class TemperatureConverter(
    toBaseConversion: (Double) -> Double,
    fromBaseConversion: (Double) -> Double,
    validNames: List<String>,
    singleName: String,
    fullName: String
) : Converter(
    toBaseConversion,
    fromBaseConversion,
    validNames,
    singleName,
    fullName,
    ConverterType.TEMPERATURE
)

class CelsiusConverter : TemperatureConverter(
    { it },
    { it },
    listOf("degree celsius", "degrees celsius", "celsius", "dc", "c"),
    "degree Celsius",
    "degrees Celsius"
)

class FahrenheitConverter : TemperatureConverter(
    { value -> (value - 32) * 5 / 9 },
    { value -> value * 9 / 5 + 32 },
    listOf("degree fahrenheit", "degrees fahrenheit", "fahrenheit", "df", "f"),
    "degree Fahrenheit",
    "degrees Fahrenheit"
)

class KelvinConverter : TemperatureConverter(
    { value -> value - 273.15 },
    { value -> value + 273.15 },
    listOf("kelvin", "kelvins", "k"),
    "kelvin",
    "kelvins"
)

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
    CelsiusConverter(),
    FahrenheitConverter(),
    KelvinConverter()
)

fun findConverterPerUnitName(unitName: String): Converter? = converters.find { it.shouldUseThisConverter(unitName) }

fun convert(conversionInput: ConversionInput) {
    val (baseValue, fromUnitName) = conversionInput.fromConverter!!.convertToBaseUnit(conversionInput.valueToConvert)
    val (result, toUnitName) = conversionInput.toConverter!!.convertFromBaseUnit(baseValue)
    println("${conversionInput.valueToConvert} $fromUnitName is $result $toUnitName")
}

fun checkValidConversion(fromConverter: Converter?, toConverter: Converter?): Boolean {
    val fromConverterNull = fromConverter == null
    val toConverterNull = toConverter == null

    return if (fromConverterNull || toConverterNull) {
        val fromStr = if (fromConverterNull) "???" else fromConverter!!.getDefaultName()
        val toStr = if (toConverterNull) "???" else toConverter!!.getDefaultName()
        println("Conversion from $fromStr to $toStr is impossible")
        false
    } else if (fromConverter!!.type != toConverter!!.type) {
        println("Conversion from ${fromConverter.getDefaultName()} to ${toConverter.getDefaultName()} is impossible")
        false
    } else {
        true
    }
}

fun checkValidValue(valueToConvert: Double, fromConverter: Converter): Boolean {
    if (valueToConvert < 0) {
        if (fromConverter.type == ConverterType.WEIGHT) {
            println("Weight shouldn't be negative")
            return false
        }

        if (fromConverter.type == ConverterType.LENGTH) {
            println("Length  shouldn't be negative")
            return false
        }
    }

    return true
}

data class ConversionInput(val valueToConvert: Double, val fromConverter: Converter?, val toConverter: Converter?)

fun readConversionInput(input: String): ConversionInput {
    val degreeMatcher = "degrees?".toRegex()
    val splittedInput = input.split(" ")
    var splittedInputIndex = 0
    val valueToConvert = splittedInput[splittedInputIndex++].toDouble()

    val firstPartOfFromUnitName = splittedInput[splittedInputIndex++]
    val fromUnitName = if (firstPartOfFromUnitName.lowercase().matches(degreeMatcher, )) {
        "$firstPartOfFromUnitName ${splittedInput[splittedInputIndex++]}"
    } else {
        firstPartOfFromUnitName
    }

    splittedInputIndex++

    val firstPartOfToUnitName = splittedInput[splittedInputIndex++]
    val toUnitName = if (firstPartOfToUnitName.lowercase().matches(degreeMatcher)) {
        "$firstPartOfToUnitName ${splittedInput[splittedInputIndex]}"
    } else {
        firstPartOfToUnitName
    }

    val fromConverter = findConverterPerUnitName(fromUnitName)
    val toConverter = findConverterPerUnitName(toUnitName)

    return ConversionInput(valueToConvert, fromConverter, toConverter)
}

fun main() {
    while (true) {
        println()
        print("Enter what you want to convert (or exit): ")
        val input = readLine()!!
        if (input == "exit") return

        val conversionInput: ConversionInput
        try {
            conversionInput = readConversionInput(input)
        } catch (e: Exception) {
            println("Parse error")
            continue
        }

        // checks
        if (!checkValidConversion(conversionInput.fromConverter, conversionInput.toConverter)) continue
        if (!checkValidValue(conversionInput.valueToConvert, conversionInput.fromConverter!!)) continue

        convert(conversionInput)
    }
}
