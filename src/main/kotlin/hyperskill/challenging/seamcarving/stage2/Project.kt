package hyperskill.challenging.seamcarving.stage2

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

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
    invertColors(image)
    saveImageToFile(image, arguments.outputFilename)
}

fun readImage(filename: String): BufferedImage {
    return ImageIO.read(File(filename))
}

fun invertColors(image: BufferedImage) {
    for (row in 0 until image.height) {
        for (col in 0 until image.width) {
            val currColor = Color(image.getRGB(col, row))
            val red = currColor.red
            val green = currColor.green
            val blue = currColor.blue
            val newColor = Color(255 - red, 255 - green, 255 - blue)
            image.setRGB(col, row, newColor.rgb)
        }
    }
}

fun saveImageToFile(image: BufferedImage, filename: String) {
    ImageIO.write(image, "png", File(filename))
}
