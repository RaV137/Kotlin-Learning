package hyperskill.hard.steganography.stage2

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.IIOException
import javax.imageio.ImageIO

fun main() {
    while (true) {
        println("Task (hide, show, exit):")
        when (val input = readLine()!!) {
            "exit" -> {
                println("Bye!")
                return
            }
            "hide" -> hide()
            "show" -> show()
            else -> println("Wrong task: $input")
        }
    }
}

fun show() {
    println("Obtaining message from image.")
}

fun hide() {
    println("Input image file:")
    val inputImageFile = readLine()!!
    println("Output image file:")
    val outputImageFile = readLine()!!

    val inputImage: BufferedImage
    try {
        inputImage = ImageIO.read(File(inputImageFile))
    } catch (e: IIOException) {
        println("Can't read input file!")
        return
    }

    println("Input Image: $inputImageFile")
    println("Output Image: $outputImageFile")

    val outputImage = setLastBitTo1InImage(inputImage)
    ImageIO.write(outputImage, "png", File(outputImageFile))

    println("Image $outputImageFile is saved")
}

fun setLastBitTo1InImage(image: BufferedImage): BufferedImage {
    for (col in 0 until image.width) {
        for (row in 0 until image.height) {
            val rgb = image.getRGB(col, row)
            val color = Color(rgb)
            with(color) {
                val newColor = Color(red or 1, green or 1, blue or 1)
                image.setRGB(col, row, newColor.rgb)
            }
        }
    }
    return image
}
