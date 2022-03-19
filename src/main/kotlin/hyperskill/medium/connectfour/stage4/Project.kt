package hyperskill.medium.connectfour.stage4

const val firstPlayerChar = 'o'
const val secondPlayerChar = '*'

data class Player(val name: String, val char: Char)

class Board(private val rows: Int, private val columns: Int) {
    val content: MutableList<MutableList<Char>> = MutableList(columns) { MutableList(rows) { ' ' } }

    companion object {
        const val bottomLeftPipe = "\u255a"
        const val horizontalPipe = "\u2550"
        const val threeWayPipe = "\u2569"
        const val bottomRightPipe = "\u255d"
        const val verticalPipe = "\u2551"
    }

    fun printBoard() {
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
                else print(content[col / 2][row])
            }
            println()
        }
    }
}

class ConnectFour(firstPlayerName: String, secondPlayerName: String, _rows: Int, _columns: Int) {
    private val rows: Int = _rows
    private val columns: Int = _columns
    private val board = Board(_rows, _columns)
    private val players: List<Player> = listOf(
        Player(firstPlayerName, firstPlayerChar),
        Player(secondPlayerName, secondPlayerChar)
    )

    fun run() {
        printGameInfo()
        board.printBoard()

        var turnIndex = 0
        while (true) {
            val playerSelector = turnIndex % 2
            val currPlayer = players[playerSelector]
            val isGameFinished = playerTurn(currPlayer)
            if (isGameFinished) {
                println("Game over!")
                return
            }
            turnIndex++
        }
    }

    private fun printGameInfo() {
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
                return true
            }

            try {
                val colNum = input.toInt()
                insertPlayerChar(colNum, currPlayer.char)
                board.printBoard()
                val isGameFinished = checkWinningConditions()
                return isGameFinished
            } catch (e: NumberFormatException) {
                println("Incorrect column number")
                continue
            } catch (e: IllegalArgumentException) {
                println(e.message)
                continue
            }
        }
    }

    private fun checkWinningConditions(): Boolean {
        var playerWonName = ""
        for (i in 0..1) {
            with(players[i]) {
                if (checkPlayerWon(char)) {
                    playerWonName = name
                }
            }
        }

        if (playerWonName != "") {
            println("Player $playerWonName won")
            return true
        } else {
            if (' ' !in board.content.flatten()) { // no empty spaces
                println("It is a draw")
                return true
            }
            return false
        }
    }

    private fun checkPlayerWon(char: Char): Boolean {
        return isWinInColumns(char) ||
                isWinInRows(char) ||
                isWinDiagonal(char)
    }

    private fun isWinInColumns(char: Char): Boolean {
        for (col in 0 until columns) {
            for (row in 0 until rows - 4) {
                if (board.content[col][row] == char &&
                    board.content[col][row + 1] == char &&
                    board.content[col][row + 2] == char &&
                    board.content[col][row + 3] == char
                ) {
                    return true
                }
            }
        }
        return false
    }

    private fun isWinInRows(char: Char): Boolean {
        for (row in 0 until rows) {
            for (col in 0 until columns - 4) {
                if (board.content[col][row] == char &&
                    board.content[col + 1][row] == char &&
                    board.content[col + 2][row] == char &&
                    board.content[col + 3][row] == char
                ) {
                    return true
                }
            }
        }
        return false
    }

    private fun isWinDiagonal(char: Char): Boolean {
        for (row in 0 until rows - 4) {

            // bottom left -> right up
            for (col in 0 until columns - 3) {
                if (board.content[col][row] == char &&
                    board.content[col + 1][row + 1] == char &&
                    board.content[col + 2][row + 2] == char &&
                    board.content[col + 3][row + 3] == char
                ) {
                    return true
                }
            }

            // up left -> right bottom
            for (col in (3 until columns).reversed()) {
                if (board.content[col][row] == char &&
                    board.content[col - 1][row + 1] == char &&
                    board.content[col - 2][row + 2] == char &&
                    board.content[col - 3][row + 3] == char
                ) {
                    return true
                }
            }
        }
        return false
    }

    private fun insertPlayerChar(col: Int, char: Char) {
        val arrayColumnIndex = col - 1 // user inputs 1..columns, we need 0 until columns
        if (arrayColumnIndex < 0 || arrayColumnIndex >= columns) {
            throw IllegalArgumentException("The column number is out of range (1 - $columns)")
        }

        if (board.content[arrayColumnIndex][rows - 1] != ' ') {
            throw IllegalArgumentException("Column $col is full")
        }

        board.content[arrayColumnIndex][board.content[arrayColumnIndex].indexOf(' ')] = char
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
