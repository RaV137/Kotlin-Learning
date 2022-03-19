package hyperskill.hard.steganography.stage3

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

    println("Message to hide:")
    val message = readLine()!!
    val messageBytes = createMessageByteArray(message)

    val messageSize = messageBytes.size * 8
    val imagePixels = inputImage.height * inputImage.width
    if (imagePixels < messageSize) {
        println("The input image is not large enough to hold this message.")
        return
    }

    val outputImage = hideMessage(messageBytes, inputImage)

    ImageIO.write(outputImage, "png", File(outputImageFile))

    println("Message saved in $outputImageFile image")
}

fun createMessageByteArray(message: String): ByteArray {
    val bytes = message.encodeToByteArray()
    val additionalBytes = byteArrayOf(0, 0, 3)
    return bytes + additionalBytes
}

fun hideMessage(messageBytes: ByteArray, image: BufferedImage): BufferedImage {
    var byteIndex = 0
    var bitIndex = 0
    val messageSize = messageBytes.size
    for (row in 0 until image.height) {
        for (col in 0 until image.width) {
            if (byteIndex == messageSize) return image

            val currBit = getBitFromByte(messageBytes[byteIndex], bitIndex)
            if (++bitIndex == 8) {
                bitIndex = 0
                byteIndex++
            }

            val rgb = image.getRGB(col, row)
            val color = Color(rgb)
            val newColor = Color(color.red, color.green, setLastBitInInt(color.blue, currBit))
            image.setRGB(col, row, newColor.rgb)
        }
    }
    return image // should not get here
}

fun getBitFromByte(byte: Byte, pos: Int): Int {
    return (byte.toInt() shr (7 - pos)) and 1
}

fun setLastBitInInt(int: Int, bit: Int): Int {
    return ((int shr 1) shl 1) or bit
}

fun show() {
    println("Input image file:")
    val inputImageFile = readLine()!!

    val inputImage: BufferedImage
    try {
        inputImage = ImageIO.read(File(inputImageFile))
    } catch (e: IIOException) {
        println("Can't read input file!")
        return
    }

    val message = showMessage(inputImage)
    println("Message:")
    println(message)
}

fun showMessage(image: BufferedImage): String {
    val finalBytes = intArrayOf(0, 0, 3)
    val bytes = intArrayOf().toMutableList()
    var bitIndex = 0
    var currentByte = 0
    for (row in 0 until image.height) {
        for (col in 0 until image.width) {
            if (bitIndex == 8) {
                bitIndex = 0
                bytes.add(currentByte)
                currentByte = 0
                if (bytes.size >= 3) {
                    if (bytes.last() == finalBytes[2] &&
                        bytes[bytes.lastIndex - 1] == finalBytes[1] &&
                        bytes[bytes.lastIndex - 2] == finalBytes[0]
                    ) {
                        return translateIntArrayToString(bytes.toIntArray())
                    }
                }
            } else {
                currentByte = currentByte shl 1
            }
            val rgb = image.getRGB(col, row)
            val blue = Color(rgb).blue
            val bit = getLastBit(blue)
            currentByte += bit
            bitIndex++
        }
    }
    throw RuntimeException("Should not ever happen")
}

fun getLastBit(int: Int): Int {
    return int and 1
}

fun translateIntArrayToString(array: IntArray): String {
    return array
        .map { it.toByte() }
        .toByteArray()
        .decodeToString()
        .dropLast(3)
}
