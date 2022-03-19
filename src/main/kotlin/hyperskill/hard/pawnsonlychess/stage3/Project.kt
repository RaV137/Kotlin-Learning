package hyperskill.hard.pawnsonlychess.stage3

import kotlin.math.abs

// Enums

enum class PawnType(val stringRepresentation: String) {
    WHITE("W"),
    BLACK("B"),
    EMPTY(" ")
}

// Data classes

data class Pawn(val type: PawnType = PawnType.EMPTY) {
    private var isAtStart = true

    fun isAtStart() = isAtStart

    fun markMoved() {
        isAtStart = false
    }
}

data class Player(val name: String, val color: String, val pawnType: PawnType)

data class BoardCoordinates(
    val startRow: Int,
    val startColumn: Int,
    val endRow: Int,
    val endColumn: Int
)

data class MovePawnCommand(val boardCoordinates: BoardCoordinates, val startPoint: String, val playerPawnType: PawnType)

// Exceptions

class ExitException : RuntimeException()
open class InvalidInputException : RuntimeException()
class InvalidMoveException : InvalidInputException()
class NoPawnFoundException(val startPos: String) : RuntimeException()

// Classes

class ChessBoard {
    private val board = MutableList(8) { MutableList(8) { Pawn() } }

    init {
        board[1] = MutableList(8) { Pawn(PawnType.BLACK) }
        board[6] = MutableList(8) { Pawn(PawnType.WHITE) }
    }

    fun movePawn(movePawnCommand: MovePawnCommand) {
        validatePawnMove(movePawnCommand)
        with(movePawnCommand.boardCoordinates) {
            val pawn = board[startRow][startColumn]
            board[startRow][startColumn] = Pawn() // TODO: zastanowić się czy nie lepiej korzystać z null
            board[endRow][endColumn] = pawn
            pawn.markMoved()
        }
    }

    fun printBoard() {
        println("  +---+---+---+---+---+---+---+---+")
        repeat(8) { rowIndex ->
            val rowNumber = 8 - rowIndex
            val boardRowContent = board[rowIndex].joinToString(" | ") { it.type.stringRepresentation }
            println("$rowNumber | $boardRowContent |")
            println("  +---+---+---+---+---+---+---+---+")
        }
        println("    a   b   c   d   e   f   g   h\n")
    }

    private fun validatePawnMove(movePawnCommand: MovePawnCommand) {
        with(movePawnCommand.boardCoordinates) {
            val pawn = board[startRow][startColumn]
            if (pawn.type != movePawnCommand.playerPawnType) {
                throw NoPawnFoundException(movePawnCommand.startPoint)
            }

            // check if moving horizontal
            if (startColumn != endColumn) {
                throw InvalidMoveException()
            }

            // check if moving too far
            val verticalDiff = endRow - startRow
            val verticalDiffAbs = abs(verticalDiff)
            if (verticalDiffAbs > 2 || verticalDiffAbs == 0 || (verticalDiffAbs == 2 && !pawn.isAtStart())) {
                throw InvalidMoveException()
            }

            // check if going backwards
            if (verticalDiff > 0 && pawn.type == PawnType.WHITE ||
                verticalDiff < 0 && pawn.type == PawnType.BLACK
            ) {
                throw InvalidMoveException()
            }

            // check if target point is empty
            if (board[endRow][endColumn].type != PawnType.EMPTY) {
                throw InvalidMoveException()
            }
        }
    }
}

class GameManager(fistPlayerName: String, secondPlayerName: String) {
    private val board = ChessBoard()
    private val players = listOf(
        Player(fistPlayerName, "white", PawnType.WHITE),
        Player(secondPlayerName, "black", PawnType.BLACK)
    )

    companion object {
        val validPlayerTurnRegex = "[a-h][1-8][a-h][1-8]".toRegex()
    }

    fun run() {
        board.printBoard()
        var turnIndex = 0

        while (true) {
            val playerTakingTurn = players[turnIndex % 2]

            while (true) {
                try {
                    val playerInput = readPlayerInput(playerTakingTurn.name)
                    val boardCoordinates = translatePlayerInputToCoordinates(playerInput)
                    val movePawnCommand = MovePawnCommand(
                        boardCoordinates,
                        playerInput.substring(0..1),
                        playerTakingTurn.pawnType
                    )
                    board.movePawn(movePawnCommand)
                    board.printBoard()
                    break
                } catch (e: ExitException) {
                    println("Bye!")
                    return
                } catch (e: InvalidInputException) {
                    println("Invalid Input")
                    continue
                } catch (e: NoPawnFoundException) {
                    println("No ${playerTakingTurn.color} pawn at ${e.startPos}")
                    continue
                }
            }
            turnIndex++
        }
    }

    private fun readPlayerInput(playerTakingTurn: String): String {
        println("$playerTakingTurn's turn:")

        val input = readLine()!!
        if (input == "exit") {
            throw ExitException()
        }

        if (input.matches(validPlayerTurnRegex)) {
            return input
        } else {
            throw InvalidInputException()
        }
    }

    private fun translatePlayerInputToCoordinates(input: String): BoardCoordinates {
        val startCol = input[0].minus('a')
        val startRow = 8 - input[1].digitToInt()
        val endCol = input[2].minus('a')
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
