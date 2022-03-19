package hyperskill.challenging.seamcarving.stage5

import java.awt.Color

fun main() {
    val image = readImage("trees-seam-hor.png")
//    val image = readImage("trees-horizontal-seam.png")
    val redPixelsRows = mutableListOf<Int>()
    for (col in 0 until image.width) {
        for (row in 0 until image.height) {
            val currColor = Color(image.getRGB(col, row))
            if (currColor.red == 255 && currColor.green == 0 && currColor.blue == 0) {
                redPixelsRows.add(row)
            }
        }
    }

    println("First 20 rows:")
    println(redPixelsRows.take(20).joinToString("\n"))
}