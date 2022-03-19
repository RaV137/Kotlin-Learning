package hyperskill.challenging.seamcarving.stage5

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.math.sqrt

data class EnergyPixel(
    val energyValue: Double,
    var totalEnergy: Double = energyValue,
    var minimumSeamMember: Boolean = false,
    var isTotalEnergySet: Boolean = false
) {
    companion object {
        val EMPTY_ENERGY_PIXEL = EnergyPixel(0.0)
    }
}

data class Arguments(val inputFilename: String, val outputFilename: String)

object ArgumentParser {
    private const val IN_ARG_NAME = "-in"
    private const val OUT_ARG_NAME = "-out"

    fun parseArguments(args: Array<String>): Arguments {
        val inArgIdx = args.indexOf(IN_ARG_NAME)
        val outArgIdx = args.indexOf(OUT_ARG_NAME)

        if (inArgIdx >= 0) {
            val inputFilename = args[inArgIdx + 1]
            if (outArgIdx >= 0) {
                val outputFilename = args[outArgIdx + 1]
                return Arguments(inputFilename, outputFilename)
            }
        }

        throw IllegalArgumentException("No -in or -out arguments")
    }
}

fun main(args: Array<String>) {
    val arguments = ArgumentParser.parseArguments(args)

    val image = readImage(arguments.inputFilename)
    val transposedImage = transposeImage(image)
    val energyMatrix = createEnergyMatrix(transposedImage)
    createSeams(energyMatrix)
    markMinimumSeam(energyMatrix)
    drawSeamOnImage(transposedImage, energyMatrix)
    val reTransposedImage = transposeImage(transposedImage)
    saveImageToFile(reTransposedImage, arguments.outputFilename)
}

fun readImage(filename: String): BufferedImage {
    return ImageIO.read(File(filename))
}

fun saveImageToFile(image: BufferedImage, filename: String) {
    ImageIO.write(image, "png", File(filename))
}

fun transposeImage(image: BufferedImage): BufferedImage {
    val transposedImage = BufferedImage(image.height, image.width, image.type)
    repeat(transposedImage.width) { col ->
        repeat(transposedImage.height) { row ->
            transposedImage.setRGB(col, row, image.getRGB(row, col))
        }
    }
    return transposedImage
}

fun createEnergyMatrix(image: BufferedImage): Array<Array<EnergyPixel>> {
    val energyMatrix = Array(image.height) { Array(image.width) { EnergyPixel.EMPTY_ENERGY_PIXEL } }
    for (row in 0 until image.height) {
        for (col in 0 until image.width) {
            val energy = calculateEnergy(image, row, col)
            energyMatrix[row][col] = EnergyPixel(energy)
        }
    }
    return energyMatrix
}

fun calculateEnergy(image: BufferedImage, row: Int, col: Int): Double {
    val horizontalCol = getValidCoordinate(col, image.width)
    val xGradient = calculateHorizontalGradient(image, row, horizontalCol)

    val verticalRow = getValidCoordinate(row, image.height)
    val yGradient = calculateVerticalGradient(image, verticalRow, col)

    return sqrt(xGradient + yGradient)
}

fun getValidCoordinate(coord: Int, maxCoord: Int): Int {
    return if (coord == 0) 1 else if (coord == maxCoord - 1) maxCoord - 2 else coord
}

fun calculateHorizontalGradient(image: BufferedImage, row: Int, col: Int): Double {
    val leftColor = Color(image.getRGB(col - 1, row))
    val rightColor = Color(image.getRGB(col + 1, row))
    return calculateGradient(leftColor, rightColor)
}

fun calculateVerticalGradient(image: BufferedImage, row: Int, col: Int): Double {
    val upperColor = Color(image.getRGB(col, row - 1))
    val lowerColor = Color(image.getRGB(col, row + 1))
    return calculateGradient(upperColor, lowerColor)
}

fun calculateGradient(firstColor: Color, secondColor: Color): Double {
    val r = firstColor.red - secondColor.red
    val g = firstColor.green - secondColor.green
    val b = firstColor.blue - secondColor.blue
    return r * r + g * g + b * b.toDouble()
}

fun createSeams(energyMatrix: Array<Array<EnergyPixel>>) {
    for (row in 0 until energyMatrix.lastIndex) {
        for (col in energyMatrix.first().indices) {
            val currEnergy = energyMatrix[row][col].totalEnergy
            val bottomRow = energyMatrix[row + 1]
            repeat(3) {
                val bottomPixel = findEnergyPixel(bottomRow, col + 1 - it)
                updateBottomEnergyPixelTotalEnergy(bottomPixel, currEnergy)
            }
        }
    }
}

fun findEnergyPixel(energyMatrix: Array<EnergyPixel>, col: Int): EnergyPixel? {
    return try {
        energyMatrix[col]
    } catch (e: Exception) {
        null
    }
}

fun updateBottomEnergyPixelTotalEnergy(pixel: EnergyPixel?, currEnergy: Double) {
    if (pixel == null) return

    with(pixel) {
        if (isTotalEnergySet.not()) {
            totalEnergy = currEnergy + energyValue
            isTotalEnergySet = true
        } else if (currEnergy + energyValue < totalEnergy) {
            totalEnergy = currEnergy + energyValue
            isTotalEnergySet = true
        }
    }
}

fun markMinimumSeam(energyMatrix: Array<Array<EnergyPixel>>) {
    var totalEnergyInSeam = 0.0

    var minEnergyIndex = -1
    for (row in energyMatrix.lastIndex downTo 0) {
        var minEnergy = Double.MAX_VALUE
        val currRow = energyMatrix[row]
        val range = getValidHorizontalRowRange(minEnergyIndex - 1, minEnergyIndex + 1, currRow.lastIndex)
        for (col in range) {
            val energy = currRow[col].totalEnergy
            if (energy < minEnergy) {
                minEnergy = energy
                minEnergyIndex = col
            }
        }

        currRow[minEnergyIndex].minimumSeamMember = true
        totalEnergyInSeam += currRow[minEnergyIndex].energyValue
    }

    println("Total energy in seam: $totalEnergyInSeam")
}

fun getValidHorizontalRowRange(left: Int, right: Int, maxSize: Int): IntRange {
    if (left == -2 && right == 0) { // warunek startowy
        return 0..maxSize
    }
    return left.coerceAtLeast(0)..right.coerceAtMost(maxSize)
}

fun drawSeamOnImage(image: BufferedImage, energyMatrix: Array<Array<EnergyPixel>>) {
    for (row in 0 until image.height) {
        val minimumSeamIndex = energyMatrix[row]
            .withIndex()
            .find { (_, pixel) -> pixel.minimumSeamMember }!!
            .index
        image.setRGB(minimumSeamIndex, row, Color(255, 0, 0).rgb)
    }
}
