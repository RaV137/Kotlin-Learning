package hyperskill.hard.minesweeper.stage3

import kotlin.random.Random

class Cell(var isMine: Boolean, var isHidden: Boolean = true, var adjacentMines: Int = 0) {
    companion object {
        const val MINE_MARK = 'X'
        const val SAFE_MARK = '.'
    }

    fun fieldOccurrence(): Char {
//        return if (isMine && !isHidden) MINE_MARK else SAFE_MARK
        return if (isMine) {
            MINE_MARK
        } else if (adjacentMines == 0) {
            SAFE_MARK
        } else {
            adjacentMines.toString()[0]
        }
    }

    override fun toString(): String {
        return fieldOccurrence().toString()
    }
}

class Field(
    private val width: Int,
    private val height: Int,
    private val numberOfMines: Int
) {
    val content = MutableList(height) { MutableList(width) { Cell(false) } }

    init {
        createMines()
        setNumberOfAdjacentMinesToCells()
    }

    fun printField() {
        repeat(height) { row ->
            println(content[row].map { it.fieldOccurrence() }.joinToString(""))
        }
    }

    private fun createMines() {
        var currentMines = 0
        val rand = Random(System.currentTimeMillis())
        while (true) {
            val randRow = rand.nextInt(height)
            val randCol = rand.nextInt(width)
            if (content[randRow][randCol].isMine) continue

            content[randRow][randCol].isMine = true
            currentMines++

            if (currentMines == numberOfMines) return
        }
    }

    private fun setNumberOfAdjacentMinesToCells() {
        repeat(height) { row ->
            repeat(width) { col ->
                val currentCell = content[row][col]
                var adjacentMines = 0
                if (checkIfCellIsMine(row - 1, col - 1)) adjacentMines++ // left top
                if (checkIfCellIsMine(row - 1, col)) adjacentMines++ // top
                if (checkIfCellIsMine(row - 1, col + 1)) adjacentMines++ // right top
                if (checkIfCellIsMine(row, col - 1)) adjacentMines++ // left
                if (checkIfCellIsMine(row, col + 1)) adjacentMines++ // right
                if (checkIfCellIsMine(row + 1, col - 1)) adjacentMines++ // left bottom
                if (checkIfCellIsMine(row + 1, col)) adjacentMines++ // bottom
                if (checkIfCellIsMine(row + 1, col + 1)) adjacentMines++ // right bottom
                currentCell.adjacentMines = adjacentMines
            }
        }
    }

    private fun checkIfCellIsMine(row: Int, col: Int): Boolean {
        return try {
            content[row][col].isMine
        } catch (e: IndexOutOfBoundsException) {
            false
        }
    }
}

fun main() {
    val numberOfMines = readNumberOfMines()
    val field = Field(9, 9, numberOfMines)
    field.printField()
}

fun readNumberOfMines(): Int {
    print("How many mines do you want on the field? ")
    return readLine()!!.toInt()
}
