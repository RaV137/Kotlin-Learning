package hyperskill.medium.connectfour.stage5

class Player(val name: String, val char: Char) {
    var score = 0

    fun markWin() {
        score += 2
    }

    fun markDraw() = score++
}

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

enum class GameStatus {
    RUNNING, FINISHED, TERMINATED
}

class ConnectFour(_players: List<Player>, _rows: Int, _columns: Int) {
    private val rows: Int = _rows
    private val columns: Int = _columns
    private val board = Board(_rows, _columns)
    private val players = _players
    private val gameWinnerChecker = GameWinnerChecker()

    fun run(turnIndexOffset: Int = 0): GameStatus {
        board.printBoard()

        var turnIndex = turnIndexOffset
        while (true) {
            val playerSelector = turnIndex % 2
            val currPlayer = players[playerSelector]
            val gameStatus = playerTurn(currPlayer)
            if (gameStatus != GameStatus.RUNNING) {
                return gameStatus
            }
            turnIndex++
        }
    }

    private fun playerTurn(currPlayer: Player): GameStatus {
        while (true) {
            println("${currPlayer.name}'s turn:")
            val input = readLine()!!
            if (input == "end") {
                return GameStatus.TERMINATED
            }

            try {
                val colNum = input.toInt()
                insertPlayerChar(colNum, currPlayer.char)
                board.printBoard()
                val isGameFinished = gameWinnerChecker.checkGameFinished()
                return if (isGameFinished) GameStatus.FINISHED else GameStatus.RUNNING
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

        if (board.content[arrayColumnIndex][rows - 1] != ' ') {
            throw IllegalArgumentException("Column $col is full")
        }

        board.content[arrayColumnIndex][board.content[arrayColumnIndex].indexOf(' ')] = char
    }

    inner class GameWinnerChecker {
        fun checkGameFinished(): Boolean {
            var playerWonName = ""
            players.forEach {
                if (checkPlayerWon(it.char)) {
                    playerWonName = it.name
                    it.markWin()
                }
            }

            if (playerWonName != "") {
                println("Player $playerWonName won")
                return true
            } else {
                if (' ' !in board.content.flatten()) { // no empty spaces in board
                    players.forEach { it.markDraw() }
                    println("It is a draw")
                    return true
                }
                return false
            }
        }

        fun checkPlayerWon(char: Char): Boolean {
            return isWinInColumns(char) ||
                    isWinInRows(char) ||
                    isWinDiagonal(char)
        }

        fun isWinInColumns(char: Char): Boolean {
            for (col in 0 until columns) {
                for (row in 0..rows - 4) {
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

        fun isWinInRows(char: Char): Boolean {
            for (row in 0 until rows) {
                for (col in 0..columns - 4) {
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

        fun isWinDiagonal(char: Char): Boolean {
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
    }
}

class GameManager(firstPlayerName: String, secondPlayerName: String, _rows: Int, _columns: Int, _numberOfGames: Int) {
    private val rows: Int = _rows
    private val columns: Int = _columns
    private val players: List<Player> = listOf(
        Player(firstPlayerName, firstPlayerChar),
        Player(secondPlayerName, secondPlayerChar)
    )
    private val numberOfGames: Int = _numberOfGames

    companion object {
        const val firstPlayerChar = 'o'
        const val secondPlayerChar = '*'
    }

    fun run() {
        printGameInfo()
        if (numberOfGames == 1) {
            println("Single game")
            ConnectFour(players, rows, columns).run()
        } else {
            println("Total $numberOfGames games")
            repeat(numberOfGames) {
                println("Game #${it + 1}")
                val gameStatus = ConnectFour(players, rows, columns).run(it)
                if (gameStatus == GameStatus.TERMINATED) {
                    println("Game over!")
                    return
                }

                printScore()
            }
        }
        println("Game over!")
    }

    private fun printGameInfo() {
        println(
            """
                ${players[0].name} VS ${players[1].name}
                $rows X $columns board
            """.trimIndent()
        )
    }

    private fun printScore() {
        println("Score")
        println("${players[0].name}: ${players[0].score} ${players[1].name}: ${players[1].score}")
    }
}

fun main() {
    println("Connect Four")
    val (firstPlayerName, secondPlayerName) = readPlayersNames()
    val (rows, columns) = readBoardSize()
    val numberOfGames = readNumberOfGames()
    val gameManager = GameManager(firstPlayerName, secondPlayerName, rows, columns, numberOfGames)
    gameManager.run()
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

fun readNumberOfGames(): Int {
    while (true) {
        println(
            """
        Do you want to play single or multiple games?
        For a single game, input 1 or press Enter
        Input a number of games:
    """.trimIndent()
        )
        try {
            val input = readLine()!!
            if (input.isBlank()) return 1

            val numberOfGames = input.toInt()
            require(numberOfGames > 0)
            return numberOfGames
        } catch (e: Exception) {
            println("Invalid input")
            continue
        }
    }
}
