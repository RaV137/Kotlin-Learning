package hyperskill.hard.minesweeper.stage5

import kotlin.random.Random

class Cell {
    private var adjacentMines: Int = 0
    private var isMarked: Boolean = false
    private var type = CellType.EMPTY
    private var isHidden = true

    companion object {
        const val UNEXPLORED_CHAR = '.'
        const val MARKED_CHAR = '*'
        const val EXPLORED_FREE_CHAR = '/'
        const val MINE_CHAR = 'X'
    }

    enum class CellType {
        MINE, EMPTY, NUMBER
    }

    fun getType() = type

    fun isMine() = type == CellType.MINE
    fun isNumeric() = type == CellType.NUMBER

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
        // false -> true, true -> false
        isMarked = isMarked.not()
    }

    fun free() {
        isHidden = false
        if (type != CellType.MINE) {
            isMarked = false
        }
    }

    fun isFreed() = !isHidden

    fun isMarked() = isMarked

    fun cellDisplay(): Char {
        return if (isMarked) {
            MARKED_CHAR
        } else if (isHidden) {
            UNEXPLORED_CHAR
        } else if (type == CellType.EMPTY) {
            EXPLORED_FREE_CHAR
        } else if (type == CellType.NUMBER) {
            adjacentMines.toString()[0]
        } else {
            MINE_CHAR
        }
    }

    override fun toString(): String {
        return cellDisplay().toString()
    }
}

class Field(private val numberOfMines: Int) {
    private val content = MutableList(height) { MutableList(width) { Cell() } }
    private var areMinesSet = false

    companion object {
        const val width = 9
        const val height = 9
    }

    fun printField() {
        println()
        println(" │123456789│")
        println("—│—————————│")
        repeat(height) { row ->
            val lineString = content[row].map { it.cellDisplay() }.joinToString("")
            println("${row + 1}|$lineString|")
        }
        println("—│—————————│")
    }

    fun markCell(column: Int, row: Int) {
        initialise(column, row)
        content[row][column].markByPlayer()
    }

    fun freeCell(column: Int, row: Int): Boolean {
        initialise(column, row)
        if (cellType(column, row) == Cell.CellType.MINE) {
            return true
        }
        freeCellRecursively(column, row)
        return false
    }

    fun printLostField() {
        content.flatten()
            .filter { it.getType() == Cell.CellType.MINE }
            .forEach { it.free() }
        printField()
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

    fun checkAllCellsFreed(): Boolean {
        return content.flatten().count { it.isFreed() } == (height * width - numberOfMines)
    }

    private fun initialise(column: Int, row: Int) {
        if (areMinesSet) return
        createMines(column, row)
        setNumberOfAdjacentMinesToCells()
        areMinesSet = true
    }

    private fun createMines(column: Int, row: Int) {
        var currentMines = 0
        val rand = Random(System.currentTimeMillis())
        while (true) {
            val randRow = rand.nextInt(height)
            val randCol = rand.nextInt(width)
            if (randRow == row && randCol == column) continue
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

    private fun freeCellRecursively(column: Int, row: Int) {
        val cell: Cell
        try {
            cell = content[row][column]
        } catch (e: IndexOutOfBoundsException) {
            return
        }

        if (cell.isFreed() || cell.isMine()) return

        if (cell.isNumeric()) {
            cell.free()
            return
        }

        cell.free()

        freeCellRecursively(column - 1, row - 1) // left top
        freeCellRecursively(column - 1, row) // left
        freeCellRecursively(column - 1, row + 1) // left bottom
        freeCellRecursively(column, row - 1) // top
        freeCellRecursively(column, row + 1) // bottom
        freeCellRecursively(column + 1, row - 1) // right top
        freeCellRecursively(column + 1, row) // right
        freeCellRecursively(column + 1, row + 1) // right bottom
    }

    private fun cellType(col: Int, row: Int): Cell.CellType {
        return content[row][col].getType()
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

    companion object {
        const val MINE_ACTION = "mine"
        const val FREE_ACTION = "free"
    }

    fun run() {
        board.printField()

        while (true) {
            if (playerTurn()) {
                board.printLostField()
                println("You stepped on a mine and failed!")
                return
            }

            board.printField()

            if (checkIfWon()) {
                println("Congratulations! You found all the mines!")
                return
            }
        }
    }

    private fun playerTurn(): Boolean {
        while (true) {
            try {
                print("Set/unset mines marks or claim a cell as free: ")
                val input = readLine()!!.split(" ")
                val (column, row) = input.subList(0, 2).map { it.toInt() }.map { it - 1 }
                when (input.subList(2, 3)[0]) {
                    MINE_ACTION -> board.markCell(column, row)
                    FREE_ACTION -> {
                        if (board.freeCell(column, row)) return true // hit the mine
                    }
                    else -> continue
                }
                return false // keep playing
            } catch (e: Exception) {
                println("Invalid input")
                continue
            }
        }
    }

    private fun checkIfWon(): Boolean {
        val correctlyMarkedMines = board.countCorrectlyMarkedCells()
        val wronglyMarkedMines = board.countWronglyMarkedCells()
        return correctlyMarkedMines == numberOfMines && wronglyMarkedMines == 0 || board.checkAllCellsFreed()
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
