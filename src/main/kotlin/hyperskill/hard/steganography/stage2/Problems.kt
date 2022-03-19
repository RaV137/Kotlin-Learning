package hyperskill.hard.steganography.stage2

import java.awt.Color
import java.awt.image.BufferedImage

fun main() {
    problem3()
}

fun problem1() {
    //Do not touch code below
    var inputArray: Array<Array<String>> = arrayOf()
    val n = readLine()!!.toInt()
    for (i in 0 until n) {
        val strings = readLine()!!.split(' ').toTypedArray()
        inputArray += strings
    }
    //write your code here

    val leftTop = inputArray[0][0]
    val rightTop = inputArray[0][inputArray[0].lastIndex]

    val lastRow = inputArray[inputArray.lastIndex]
    val leftBottom = lastRow[0]
    val rightBottom = lastRow[lastRow.lastIndex]

    println("$leftTop $rightTop")
    println("$leftBottom $rightBottom")
}

fun problem2() {
    // Do not touch code below
    var inputArray: Array<Array<String>> = arrayOf()
    val n = readLine()!!.toInt()
    for (i in 0 until n) {
        val strings = readLine()!!.split(' ').toTypedArray()
        inputArray += strings
    }
    // write your code here

    val first = inputArray.first()
    val last = inputArray.last()
    val newArray = arrayOf(last, first)
    println(newArray.contentDeepToString())
}

fun problem3() {
    val argb = readLine()!!.split(" ").map { it.toInt() }
    for (el in argb) {
        if (el !in 0..255) {
            println("Invalid input")
            return
        }
    }
    val (alpha, red, green, blue) = argb
    println(java.awt.Color(red, green, blue, alpha).rgb.toUInt())
}

fun problem4(): BufferedImage {
    val size = 200
    val image = BufferedImage(size, size, BufferedImage.TYPE_INT_RGB)
    with(image.graphics) {
        color = Color.RED
        drawLine(0, 0, 200, 200)
        color = Color.GREEN
        drawLine(200, 0, 0, 200)
    }
    return image
}

fun problem5(): BufferedImage {
    val size = 200
    val image = BufferedImage(size, size, BufferedImage.TYPE_INT_RGB)
    with(image.graphics) {
        color = Color.RED
        drawOval(50, 50, 100, 100)
        color = Color.YELLOW
        drawOval(50, 75, 100, 100)
        color = Color.GREEN
        drawOval(75, 50, 100, 100)
        color = Color.BLUE
        drawOval(75, 75, 100, 100)
    }
    return image
}

fun problem6(): BufferedImage {
    val size = 500
    val image = BufferedImage(size, size, BufferedImage.TYPE_INT_RGB)
    with(image.graphics) {
        color = Color.RED
        drawRect(100, 100, 300, 300)
    }
    return image
}

fun problem7(): BufferedImage {
    val size = 200
    val image = BufferedImage(size, size, BufferedImage.TYPE_INT_RGB)
    val string = "Hello, images!"
    with(image.graphics) {
        color = Color.RED
        drawString(string, 50, 50)
        color = Color.GREEN
        drawString(string, 51, 51)
        color = Color.BLUE
        drawString(string, 52, 52)
    }
    return image
}
