package hyperskill.hard.minesweeper.stage4

import kotlin.random.Random

class Cell {
    private var adjacentMines: Int = 0
    private var isMarked: Boolean = false
    private var type = CellType.EMPTY

    companion object {
        const val EMPTY_MARK = '.'
        const val MAYBE_MINE_MARK = '*'
    }

    enum class CellType {
        MINE, EMPTY, NUMBER
    }

    fun getType() = type

    fun isMine() = type == CellType.MINE

    fun markAsMine() {
        type = CellType.MINE
    }

    fun setAdjacentMinesNumber(numberOfAdjacentMines: Int) {
        adjacentMines = numberOfAdjacentMines
        if (adjacentMines > 0 && type == CellType.EMPTY) {
            type = CellType.NUMBER
        }
    }

    fun markByPlayer() {
        isMarked = isMarked.not() // false -> true, true -> false
    }

    fun isMarked() = isMarked

    fun fieldOccurrence(): Char {
        return if (isMarked) {
            MAYBE_MINE_MARK
        } else if (type != CellType.NUMBER) {
            EMPTY_MARK
        } else {
            adjacentMines.toString()[0]
        }
    }

    override fun toString(): String {
        return fieldOccurrence().toString()
    }
}

class Field(private val numberOfMines: Int) {
    private val content = MutableList(height) { MutableList(width) { Cell() } }

    companion object {
        const val width = 9
        const val height = 9
    }

    init {
        createMines()
        setNumberOfAdjacentMinesToCells()
    }

    fun printField() {
        println()
        println(" │123456789│")
        println("—│—————————│")
        repeat(height) { row ->
            val lineString = content[row].map { it.fieldOccurrence() }.joinToString("")
            println("${row + 1}|$lineString|")
        }
        println("—│—————————│")
    }

    fun cellType(col: Int, row: Int): Cell.CellType {
        return content[row][col].getType()
    }

    fun markCell(column: Int, row: Int) {
        content[row][column].markByPlayer()
    }

    fun countWronglyMarkedCells(): Int {
        return content.flatten()
            .filter { it.getType() == Cell.CellType.EMPTY }
            .count { it.isMarked() }
    }

    fun countCorrectlyMarkedCells(): Int {
        return content.flatten()
            .filter { it.getType() == Cell.CellType.MINE }
            .count { it.isMarked() }
    }

    private fun createMines() {
        var currentMines = 0
        val rand = Random(System.currentTimeMillis())
        while (true) {
            val randRow = rand.nextInt(height)
            val randCol = rand.nextInt(width)
            if (content[randRow][randCol].isMine()) continue

            content[randRow][randCol].markAsMine()
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
                currentCell.setAdjacentMinesNumber(adjacentMines)
            }
        }
    }

    private fun checkIfCellIsMine(row: Int, col: Int): Boolean {
        return try {
            content[row][col].isMine()
        } catch (e: IndexOutOfBoundsException) {
            false
        }
    }
}

class Game(private val numberOfMines: Int) {
    private val board: Field = Field(numberOfMines)

    fun run() {
        board.printField()

        while (true) {
            markCell()

            board.printField()

            if (checkIfWon()) {
                println("Congratulations! You found all the mines!")
                return
            }
        }
    }

    private fun markCell() {
        while (true) {
            try {
                print("Set/delete mines marks (x and y coordinates): ")
                val (column, row) = readLine()!!.split(" ").map { it.toInt() }.map { it - 1 }
                val cellType = board.cellType(column, row)

                if (cellType == Cell.CellType.NUMBER) {
                    println("There is a number here!")
                    continue
                }

                board.markCell(column, row)

                return
            } catch (e: Exception) {
                println("Invalid input")
                continue
            }
        }
    }

    private fun checkIfWon(): Boolean {
        val correctlyMarkedMines = board.countCorrectlyMarkedCells()
        val wronglyMarkedMines = board.countWronglyMarkedCells()
        return correctlyMarkedMines == numberOfMines && wronglyMarkedMines == 0
    }
}

fun main() {
    val numberOfMines = readNumberOfMines()
    val game = Game(numberOfMines)
    game.run()
}

fun readNumberOfMines(): Int {
    print("How many mines do you want on the field? ")
    return readLine()!!.toInt()
}
