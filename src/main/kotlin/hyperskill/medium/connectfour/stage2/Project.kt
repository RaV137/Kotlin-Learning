package hyperskill.medium.connectfour.stage2

const val bottomLeftPipe = "\u255a"
const val horizontalPipe = "\u2550"
const val threeWayPipe = "\u2569"
const val bottomRightPipe = "\u255d"
const val verticalPipe = "\u2551"

class ConnectFour(val firstPlayer: String, val secondPlayer: String, val rows: Int, val columns: Int) {
    fun printGameInfo() {
        println(
            """
                $firstPlayer VS $secondPlayer
                $rows X $columns board
            """.trimIndent()
        )
    }

    fun printBoard() {
        println(" " + (1..columns).joinToString(" "))
        for (row in 1..rows) {
            println(verticalPipe.repeat(columns + 1).split("").joinToString(" ").trim())
        }
        var bottomLine = "$horizontalPipe$threeWayPipe".repeat(columns)
        bottomLine = bottomLine.substring(0, bottomLine.lastIndex)
        println("$bottomLeftPipe$bottomLine$bottomRightPipe")
    }
}

fun main() {
    println("Connect Four")
    val (firstPlayerName, secondPlayerName) = readPlayersNames()
    val (rows, columns) = readBoardSize()
    val game = ConnectFour(firstPlayerName, secondPlayerName, rows, columns)
    game.printGameInfo()
    game.printBoard()
}

fun readPlayersNames(): List<String> {
    println("First player's name:")
    val firstPlayerName = readLine()!!
    println("Second player's name:")
    val secondPlayerName = readLine()!!
    return listOf(firstPlayerName, secondPlayerName)
}

fun readBoardSize(): List<Int> {
    val boardSizeSplittingRegex = "\\s*[xX]\\s*".toRegex()
    val boardSizeRange = 5..9
    while (true) {
        println("Set the board dimensions (Rows x Columns)")
        println("Press Enter for default (6 x 7)")
        val input = readLine()!!.trim()
        if (input == "" || input =="\n") {
            return listOf(6, 7)
        }
        try {
            val (rows, columns) = input.split(boardSizeSplittingRegex).map { it.toInt() }
            if (rows !in boardSizeRange) {
                println("Board rows should be from 5 to 9")
                continue
            }
            if (columns !in boardSizeRange) {
                println("Board columns should be from 5 to 9")
                continue
            }
            return listOf(rows, columns)
        } catch (e: Exception) {
            println("Invalid input")
            continue
        }
    }
}
