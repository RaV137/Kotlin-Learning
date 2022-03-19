package hyperskill.challenging.seamcarving.stage3

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.math.sqrt

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
    val energyMatrix = createEnergyMatrix(image)
    val maxEnergy = findMaxEnergyValue(energyMatrix)
    val newImage = createImageWithEnergy(energyMatrix, image, maxEnergy)
    saveImageToFile(newImage, arguments.outputFilename)
}

fun readImage(filename: String): BufferedImage {
    return ImageIO.read(File(filename))
}

fun saveImageToFile(image: BufferedImage, filename: String) {
    ImageIO.write(image, "png", File(filename))
}

fun createEnergyMatrix(image: BufferedImage): Array<DoubleArray> {
    val energyMatrix = Array(image.height) { DoubleArray(image.width) }
    for (row in 0 until image.height) {
        for (col in 0 until image.width) {
            val energy = calculateEnergy(image, row, col)
            energyMatrix[row][col] = energy
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

fun findMaxEnergyValue(energyMatrix: Array<DoubleArray>): Double {
    var maxEnergy = Double.MIN_VALUE
    for (row in energyMatrix) {
        for (element in row) {
            if (element > maxEnergy) {
                maxEnergy = element
            }
        }
    }
    return maxEnergy
}

fun createImageWithEnergy(
    energyMatrix: Array<DoubleArray>,
    baseImage: BufferedImage,
    maxEnergy: Double
): BufferedImage {
    val newImage = BufferedImage(baseImage.width, baseImage.height, baseImage.type)
    for (row in 0 until baseImage.height) {
        for (col in 0 until baseImage.width) {
            val normalisedEnergy = normaliseEnergy(energyMatrix[row][col], maxEnergy)
            val color = Color(normalisedEnergy, normalisedEnergy, normalisedEnergy)
            newImage.setRGB(col, row, color.rgb)
        }
    }
    return newImage
}

fun normaliseEnergy(energy: Double, maxEnergy: Double): Int {
    return (255 * energy / maxEnergy).toInt()
}
