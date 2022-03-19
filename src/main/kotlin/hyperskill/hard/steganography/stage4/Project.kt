package hyperskill.hard.steganography.stage4

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.IIOException
import javax.imageio.ImageIO
import kotlin.experimental.xor

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

    println("Password:")
    val password = readLine()!!

    val messageBytes = createMessageByteArray(message, password)

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

fun createMessageByteArray(message: String, password: String): ByteArray {
    val encryptedBytes = encryptMessageWithPassword(message.encodeToByteArray(), password.encodeToByteArray())
    val additionalBytes = byteArrayOf(0, 0, 3)
    return encryptedBytes + additionalBytes
}

fun encryptMessageWithPassword(messageBytes: ByteArray, passwordBytes: ByteArray): ByteArray {
    return ByteArray(messageBytes.size) { idx ->
        messageBytes[idx] xor passwordBytes[idx % passwordBytes.size]
    }
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

    println("Password:")
    val password = readLine()!!

    val message = showMessage(inputImage)

    val decryptedMessage = decryptMessage(message, password.encodeToByteArray())

    println("Message:")
    println(decryptedMessage)
}

fun decryptMessage(message: List<Byte>, password: ByteArray): String {
    return ByteArray(message.size) { idx ->
        message[idx] xor password[idx % password.size]
    }.decodeToString()
}

fun showMessage(image: BufferedImage): List<Byte> {
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
                        return bytes.toIntArray().map { it.toByte() }.toByteArray().dropLast(3)
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
