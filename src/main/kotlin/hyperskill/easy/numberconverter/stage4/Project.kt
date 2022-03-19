package hyperskill.easy.numberconverter.stage4

import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode

const val EXIT_INPUT = "/exit"
const val BACK_INPUT = "/back"

const val RESULT_SCALE = 5
const val DECIMAL_SCALE = 20

fun main() {
    do {
        print("Enter two numbers in format: {source base} {target base} (To quit type $EXIT_INPUT) ")
        val input = readLine()!!
        if (input == EXIT_INPUT) {
            return
        } else {
            val (sourceBase, targetBase) = input.split(" ").map { it.toInt() }
            convert(sourceBase, targetBase)
        }
    } while (true)
}

fun convert(sourceBase: Int, targetBase: Int) {
    do {
        print("Enter number in base $sourceBase to convert to base $targetBase (To go back type $BACK_INPUT) ")
        val input = readLine()!!
        if (input == BACK_INPUT) {
            return
        } else {
            val decimal = if (sourceBase == 10) input else toDecimal(input, sourceBase)
            val converted = fromDecimal(decimal, targetBase)
            println("Conversion result: $converted")
        }
    } while (true)
}

fun toDecimal(number: String, base: Int): String {
    return if ("." in number) {
        val (int, frac) = number.split(".")
        val intDec = int.toBigInteger(radix = base)
        val fracDec = toDecimalFractional(frac, base)
        "$intDec.$fracDec"
    } else {
        number.toBigInteger(radix = base).toString()
    }
}

fun fromDecimal(decimal: String, base: Int): String {
    return if ("." in decimal) {
        return convertFromDecimal(decimal, base)
    } else {
        decimal.toBigInteger().toString(base)
    }
}

fun toDecimalFractional(frac: String, base: Int): String {
    if (frac.toBigInteger(base) == BigInteger.ZERO) {
        return "00000";
    }
    var result = BigDecimal.ZERO
    val baseMultiplier = BigDecimal(base)
    for (i in frac.indices) {
        val symbol = frac[i].toString().toBigInteger(radix = base).toBigDecimal().setScale(DECIMAL_SCALE, RoundingMode.HALF_UP)
        val divider = baseMultiplier.pow(i + 1).setScale(DECIMAL_SCALE, RoundingMode.HALF_UP)
        result += symbol.divide(divider, DECIMAL_SCALE, RoundingMode.HALF_UP)
    }
    return result.toString().split(".")[1]
}

fun convertFromDecimal(decimal: String, targetBase: Int): String {
    val number = decimal.toBigDecimal()
    val base = BigDecimal(targetBase)
    val multiplier = base.pow(RESULT_SCALE) // no of decimals
    var dividend = number.multiply(multiplier).setScale(RESULT_SCALE, RoundingMode.HALF_UP)

    val result = MutableList(0) { "" }
    while (dividend > BigDecimal.ZERO) {
        val (div, rem) = dividend.divideAndRemainder(base)
        result.add(rem.toInt().toString().toBigInteger().toString(targetBase)) // FIXME: brzydkie konwersje, da się lepiej?
        dividend = div
    }

    val int = result.subList(RESULT_SCALE, result.size).reversed().joinToString("")
    val frac = result.subList(0, RESULT_SCALE).reversed().joinToString("")
    return if (int != "" && frac != "") {
        "$int.$frac"
    } else if (int == "") {
        "0.$frac"
    } else {
        int
    }
}
