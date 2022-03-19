package hyperskill.hard.pawnsonlychess.stage2

data class BoardCoordinates(val startRow: Int, val startColumn: Int, val endRow: Int, val endColumn: Int)

class ExitException : RuntimeException()

class ChessBoard {
    private val board = MutableList(8) { MutableList(8) { " " } }

    init {
        board[1] = MutableList(8) { "B" }
        board[6] = MutableList(8) { "W" }
    }

    fun movePawn(coordinates: BoardCoordinates) {
        // TODO
    }

    fun printBoard() {
        println("  +---+---+---+---+---+---+---+---+")
        repeat(8) {
            val row = 8 - it
            val boardRowContent = board[it].joinToString(" | ")
            println("$row | $boardRowContent |")
            println("  +---+---+---+---+---+---+---+---+")
        }
        println("    a   b   c   d   e   f   g   h\n")
    }
}

class GameManager(fistPlayerName: String, secondPlayerName: String) {
    private val board = ChessBoard()
    private val players = listOf(fistPlayerName, secondPlayerName)

    companion object {
        val validPlayerTurnRegex = "[a-h][1-8][a-h][1-8]".toRegex()
    }

    fun run() {
        board.printBoard()
        var turnIndex = 0

        while (true) {
            val playerTakingTurn = players[turnIndex % 2]

            try {
                val playerInput = readPlayerInput(playerTakingTurn)
                val boardCoordinates = translatePlayerInputToCoordinates(playerInput)
                board.movePawn(boardCoordinates)
            } catch (e: ExitException) {
                println("Bye!")
                return
            }

            turnIndex++
        }
    }

    private fun readPlayerInput(playerTakingTurn: String): String {
        while (true) {
            println("$playerTakingTurn's turn:")
            val input = readLine()!!
            if (input == "exit") throw ExitException()
            if (input.matches(validPlayerTurnRegex)) {
                return input
            } else {
                println("Invalid Input")
                continue
            }
        }
    }

    private fun translatePlayerInputToCoordinates(input: String): BoardCoordinates {
        val startCol = 'a'.minus(input[0])
        val startRow = 8 - input[1].digitToInt()
        val endCol = 'a'.minus(input[2])
        val endRow = 8 - input[3].digitToInt()
        return BoardCoordinates(startRow, startCol, endRow, endCol)
    }
}

fun main() {
    println("Pawns-Only Chess")
    val (firstPlayerName, secondPlayerName) = readPlayersNames()
    val gameManager = GameManager(firstPlayerName, secondPlayerName)
    gameManager.run()
}

fun readPlayersNames(): List<String> {
    println("First Player's name:")
    val firstPlayerName = readLine()!!
    println("Second Player's name:")
    val secondPlayerName = readLine()!!
    return listOf(firstPlayerName, secondPlayerName)
}
