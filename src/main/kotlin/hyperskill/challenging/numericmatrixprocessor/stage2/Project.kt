package hyperskill.challenging.numericmatrixprocessor.stage2

fun main() {
    val rows = readMatrixDimensions()[0]
    val matrix = readMatrix(rows)
    val scalar = readLine()!!.toInt()

    multiplyMatrixByScalar(matrix, scalar)
}

fun readMatrixDimensions() = readLine()!!.split(" ").map { it.toInt() }

fun readMatrix(rows: Int): Array<IntArray> {
    return Array(rows) { readLine()!!.split(" ").map { it.toInt() }.toIntArray() }
}

fun multiplyMatrixByScalar(matrix: Array<IntArray>, scalar: Int) {
    for (row in matrix.indices) {
        for (col in 0 until matrix.first().size) {
            print(matrix[row][col] * scalar)
            print(" ")
        }
        println()
    }
}

fun addMatrices(matrix1: Array<IntArray>, matrix2: Array<IntArray>) {
    if (matrix1.size == matrix2.size && matrix1.first().size == matrix2.first().size) {
        for (row in matrix1.indices) {
            for (col in 0 until matrix1.first().size) {
                print(matrix1[row][col] + matrix2[row][col])
                print(" ")
            }
            println()
        }
    } else {
        println("ERROR")
    }
}
