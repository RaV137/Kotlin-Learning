package hyperskill.challenging.numericmatrixprocessor.stage4

fun main() {
    while (true) {
        print(
            """
            1. Add matrices
            2. Multiply matrix by a constant
            3. Multiply matrices
            4. Transpose matrix
            0. Exit
            Your choice: 
        """.trimIndent()
        )

        when (readLine()!!.toInt()) {
            0 -> return
            1 -> addMatrices()
            2 -> multiplyMatrixByScalar()
            3 -> multiplyMatrixByMatrix()
            4 -> transposeMatrix()
        }
    }
}

fun readFirstMatrixDimensions() = readMatrixDimensions(" first")
fun readSecondMatrixDimensions() = readMatrixDimensions(" second")
fun readOnlyMatrixDimensions() = readMatrixDimensions("")

fun readMatrixDimensions(noOfMatrix: String): List<Int> {
    print("Enter size of$noOfMatrix matrix: ")
    return readLine()!!.trim().split(" ").map { it.toInt() }
}

fun readFirstMatrix(rows: Int) = readMatrix(rows, " first")
fun readSecondMatrix(rows: Int) = readMatrix(rows, " second")
fun readOnlyMatrix(rows: Int) = readMatrix(rows, "")

fun readMatrix(rows: Int, noOfMatrix: String): Array<DoubleArray> {
    println("Enter$noOfMatrix matrix:")
    return Array(rows) { readLine()!!.trim().split(" ").map { it.toDouble() }.toDoubleArray() }
}

fun printMatrix(matrix: Array<DoubleArray>) {
    println("The result is:")
    for (row in matrix.indices) {
        println(matrix[row].joinToString(" "))
    }
}

// ### ADD MATRICES ###

fun addMatrices() {
    val (rows1, cols1) = readFirstMatrixDimensions()
    val matrix1 = readFirstMatrix(rows1)
    val (rows2, cols2) = readSecondMatrixDimensions()
    val matrix2 = readSecondMatrix(rows2)

    if (rows1 != rows2 || cols1 != cols2) {
        println("The operation cannot be performed.")
        return
    }

    val result = addMatrices(matrix1, matrix2)
    printMatrix(result)
}

fun addMatrices(matrix1: Array<DoubleArray>, matrix2: Array<DoubleArray>): Array<DoubleArray> {
    val newMatrix = mutableListOf<DoubleArray>()
    for (row in matrix1.indices) {
        val newRow = DoubleArray(matrix1.first().size)
        for (col in 0 until matrix1.first().size) {
            newRow[col] = matrix1[row][col] + matrix2[row][col]
        }
        newMatrix.add(newRow)
    }
    return newMatrix.toTypedArray()
}

// ### MULTIPLY MATRIX BY SCALAR ###

fun multiplyMatrixByScalar() {
    val rows = readOnlyMatrixDimensions()[0]
    val matrix = readOnlyMatrix(rows)
    print("Enter constant: ")
    val scalar = readLine()!!.toDouble()
    val result = multiplyMatrixByScalar(matrix, scalar)
    printMatrix(result)
}

fun multiplyMatrixByScalar(matrix: Array<DoubleArray>, scalar: Double): Array<DoubleArray> {
    val newMatrix = mutableListOf<DoubleArray>()
    for (row in matrix.indices) {
        val newRow = DoubleArray(matrix.first().size)
        for (col in 0 until matrix.first().size) {
            newRow[col] = matrix[row][col] * scalar
        }
        newMatrix.add(newRow)
    }
    return newMatrix.toTypedArray()
}

// ### MULTIPLY MATRIX BY MATRIX ###

fun multiplyMatrixByMatrix() {
    val (rows1, cols1) = readFirstMatrixDimensions()
    val matrix1 = readFirstMatrix(rows1)
    val (rows2, cols2) = readSecondMatrixDimensions()
    val matrix2 = readSecondMatrix(rows2)

    if (cols1 != rows2) {
        println("The operation cannot be performed.")
        return
    }

    val result = multiplyMatrixByMatrix(matrix1, matrix2)
    printMatrix(result)
}

fun multiplyMatrixByMatrix(matrix1: Array<DoubleArray>, matrix2: Array<DoubleArray>): Array<DoubleArray> {
    val newMatrix = mutableListOf<DoubleArray>()
    for (row in matrix1.indices) {
        val newRow = DoubleArray(matrix2.first().size)
        for (col in matrix2.first().indices) {
            val currColumn = mutableListOf<Double>()
            for (i in matrix2.indices) {
                currColumn.add(matrix2[i][col])
            }
            newRow[col] = dotProduct(matrix1[row], currColumn.toDoubleArray())
        }
        newMatrix.add(newRow)
    }
    return newMatrix.toTypedArray()
}

fun dotProduct(vector1: DoubleArray, vector2: DoubleArray): Double {
    var result = 0.0
    for (i in vector1.indices) {
        result += vector1[i] * vector2[i]
    }
    return result
}

// ### TRANSPOSE ###

enum class TransposeType(val numValue: Int) {
    MAIN_DIAGONAL(1),
    SIDE_DIAGONAL(2),
    VERTICAL_LINE(3),
    HORIZONTAL_LINE(4);

    companion object {
        fun fromInt(num: Int): TransposeType {
            for (type in values()) {
                if (type.numValue == num) {
                    return type
                }
            }
            throw IllegalArgumentException()
        }
    }
}

fun transposeMatrix() {
    println(
        """
        
        1. Main diagonal
        2. Side diagonal
        3. Vertical line
        4. Horizontal line
        Your choice: 
    """.trimIndent()
    )

    val transposeType = TransposeType.fromInt(readLine()!!.toInt())
    val rows = readOnlyMatrixDimensions()[0]
    val matrix = readOnlyMatrix(rows)
    val result = transposeMatrix(matrix, transposeType)
    printMatrix(result)
}

fun transposeMatrix(matrix: Array<DoubleArray>, transposeType: TransposeType): Array<DoubleArray> {
    val columns = matrix.first().size
    val rows = matrix.size
    val newMatrix = Array(rows) { DoubleArray(columns) }
    for (row in matrix.indices) {
        for (col in 0 until columns) {
            when (transposeType) {
                TransposeType.MAIN_DIAGONAL -> newMatrix[col][row] = matrix[row][col]
                TransposeType.SIDE_DIAGONAL -> newMatrix[columns - 1 - col][rows - 1 - row] = matrix[row][col]
                TransposeType.VERTICAL_LINE -> newMatrix[row][columns - 1 - col] = matrix[row][col]
                TransposeType.HORIZONTAL_LINE -> newMatrix[rows - 1 - row][col] = matrix[row][col]
            }
        }
    }
    return newMatrix
}
