package hyperskill.challenging.seamcarving.stage1

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun main() {
    val (width, height) = readDimensions()
    val filename = readFilename()
    val image = createImage(width, height)
    saveImageToFile(image, filename)
}

fun readDimensions(): Pair<Int, Int> {
    println("Enter rectangle width:")
    val width = readLine()!!.toInt()
    println("Enter rectangle height:")
    val height = readLine()!!.toInt()
    return width to height
}

fun readFilename(): String {
    println("Enter output image name:")
    return readLine()!!
}

fun createImage(width: Int, height: Int): BufferedImage {
    val image = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
    val graphics = image.graphics
    graphics.color = Color.RED
    graphics.drawLine(0, 0, width - 1, height - 1)
    graphics.drawLine(0, height - 1, width - 1, 0)
    return image
}

fun saveImageToFile(image: BufferedImage, filename: String) {
    ImageIO.write(image, "png", File(filename))
}
