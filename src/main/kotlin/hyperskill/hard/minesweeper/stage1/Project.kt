package hyperskill.hard.minesweeper.stage1

import kotlin.random.Random

class Cell(var isMine: Boolean, var isHidden: Boolean = true) {
    companion object {
        const val MINE_MARK = 'X'
        const val SAFE_MARK = '.'
    }

    fun fieldOccurrence(): Char {
//        return if (isMine && !isHidden) MINE_MARK else SAFE_MARK
        return if (isMine) MINE_MARK else SAFE_MARK
    }
}

class Field(val width: Int, val height: Int, val numberOfMines: Int) {
    val content = MutableList(height) { MutableList(width) { Cell(false) } }

    init {
        createMines()
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
}

fun main() {
    val field = Field(9, 9, 10)
    field.printField()
}
