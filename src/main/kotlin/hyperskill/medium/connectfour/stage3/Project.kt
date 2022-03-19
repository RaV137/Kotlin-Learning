package hyperskill.medium.connectfour.stage3

const val bottomLeftPipe = "\u255a"
const val horizontalPipe = "\u2550"
const val threeWayPipe = "\u2569"
const val bottomRightPipe = "\u255d"
const val verticalPipe = "\u2551"

const val firstPlayerChar = 'o'
const val secondPlayerChar = '*'

data class Player(val name: String, val char: Char)

class ConnectFour(firstPlayerName: String, secondPlayerName: String, _rows: Int, _columns: Int) {
    private val rows: Int = _rows
    private val columns: Int = _columns
    private val board: MutableList<MutableList<Char>> = MutableList(columns) { MutableList(rows) { ' ' } }
    private val players: List<Player> = listOf(
        Player(firstPlayerName, firstPlayerChar),
        Player(secondPlayerName, secondPlayerChar)
    )

    fun run() {
        printGameInfo()
        printBoard()

        var turnIndex = 0
        while (true) {
            val playerSelector = turnIndex % 2
            val currPlayer = players[playerSelector]
            val isGameFinished = playerTurn(currPlayer)
            if (isGameFinished) return
            printBoard()
            turnIndex++
        }
    }

    fun printGameInfo() {
        println(
            """
                ${players[0].name} VS ${players[1].name}
                $rows X $columns board
            """.trimIndent()
        )
    }

    private fun playerTurn(currPlayer: Player): Boolean {
        while (true) {
            println("${currPlayer.name}'s turn:")
            val input = readLine()!!
            if (input == "end") {
                println("Game over!")
                return true
            }

            try {
                val colNum = input.toInt()
                insertPlayerChar(colNum, currPlayer.char)
                return false
            } catch (e: NumberFormatException) {
                println("Incorrect column number")
                continue
            } catch (e: IllegalArgumentException) {
                println(e.message)
                continue
            }
        }
    }

    private fun insertPlayerChar(col: Int, char: Char) {
        val arrayColumnIndex = col - 1 // user inputs 1..columns, we need 0 until columns
        if (arrayColumnIndex < 0 || arrayColumnIndex >= columns) {
            throw IllegalArgumentException("The column number is out of range (1 - $columns)")
        }

        if (board[arrayColumnIndex][rows - 1] != ' ') {
            throw IllegalArgumentException("Column $col is full")
        }

        board[arrayColumnIndex][board[arrayColumnIndex].indexOf(' ')] = char
    }

    private fun printBoard() {
        // header (1 2 3 ...)
        println(" " + (1..columns).joinToString(" "))

        // content
        printBoardContent()

        // bottom line (╚═╩═╩═╝)
        var bottomLine = "$horizontalPipe$threeWayPipe".repeat(columns)
        bottomLine = bottomLine.substring(0, bottomLine.lastIndex)
        println("$bottomLeftPipe$bottomLine$bottomRightPipe")
    }

    private fun printBoardContent() {
        for (row in (0 until rows).reversed()) {
            for (col in 0..(columns * 2)) {
                if (col % 2 == 0) print(verticalPipe)
                else print(board[col / 2][row])
            }
            println()
        }
    }
}

fun main() {
    println("Connect Four")
    val (firstPlayerName, secondPlayerName) = readPlayersNames()
    val (rows, columns) = readBoardSize()
    val game = ConnectFour(firstPlayerName, secondPlayerName, rows, columns)
    game.run()
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
        if (input == "" || input == "\n") {
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
