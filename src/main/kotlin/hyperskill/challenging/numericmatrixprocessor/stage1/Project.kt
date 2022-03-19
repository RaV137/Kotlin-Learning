package hyperskill.challenging.numericmatrixprocessor.stage1

fun main() {
    val (rows1, columns1) = readMatrixDimensions()
    val matrix1 = readMatrix(rows1)

    val (rows2, columns2) = readMatrixDimensions()
    val matrix2 = readMatrix(rows2)

    if (rows1 == rows2 && columns1 == columns2) {
        for (row in 0 until rows1) {
            for (col in 0 until columns1) {
                print(matrix1[row][col] + matrix2[row][col])
                print(" ")
            }
            println()
        }
    } else {
        println("ERROR")
    }
}

fun readMatrixDimensions() = readLine()!!.split(" ").map { it.toInt() }

fun readMatrix(rows: Int): Array<IntArray> {
    return Array(rows) { readLine()!!.split(" ").map { it.toInt() }.toIntArray() }
}
