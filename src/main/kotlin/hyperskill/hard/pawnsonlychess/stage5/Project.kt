package hyperskill.hard.pawnsonlychess.stage5

import java.util.*
import kotlin.math.abs

// Enums

enum class PawnType(val stringRepresentation: String) {
    WHITE("W"),
    BLACK("B"),
    EMPTY(" ")
}

enum class PawnMoveType {
    VALID, INVALID, EN_PASSANT
}

enum class GameStatus(val message: String = "") {
    WHITE_WIN("White Wins!"),
    BLACK_WIN("Black Wins!"),
    STALEMATE("Stalemate!"),
    KEEP_PLAYING
}

// Data classes

data class Pawn(val type: PawnType = PawnType.EMPTY) {
    var row: Int = -1 // TODO: change code to use this instead of some filp-flops
    var column: Int = -1
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

data class PawnMoveLog(val boardCoordinates: BoardCoordinates, val isStarting: Boolean = false)

// Exceptions

class ExitException : RuntimeException()
open class InvalidInputException : RuntimeException()
class InvalidMoveException : InvalidInputException()
class NoPawnFoundException(val startPos: String) : RuntimeException()

// Classes

class Board {
    private val board = MutableList(BOARD_SIZE) { MutableList(BOARD_SIZE) { Pawn() } }

    companion object {
        const val BOARD_SIZE = 8
    }

    init {
        board[1] = MutableList(BOARD_SIZE) { Pawn(PawnType.BLACK) }
        board[6] = MutableList(BOARD_SIZE) { Pawn(PawnType.WHITE) }
    }

    fun printBoard() {
        println("  +---+---+---+---+---+---+---+---+")
        repeat(BOARD_SIZE) { rowIndex ->
            val rowNumber = BOARD_SIZE - rowIndex
            val boardRowContent = board[rowIndex].joinToString(" | ") { it.type.stringRepresentation }
            println("$rowNumber | $boardRowContent |")
            println("  +---+---+---+---+---+---+---+---+")
        }
        println("    a   b   c   d   e   f   g   h\n")
    }

    fun getPawnAt(row: Int, col: Int) = board[row][col]

    fun setPawnAt(row: Int, col: Int, pawn: Pawn = Pawn()) {
        board[row][col] = pawn
    }

    fun getContentAsString(row: Int = -1): String {
        return if (row in 0 until BOARD_SIZE) {
            board[row].joinToString("") { it.type.stringRepresentation }
        } else {
            board.flatten().joinToString("") { it.type.stringRepresentation }
        }
    }

    fun getOppositePawnsWithCoordinates(ignoredPawnType: PawnType): List<Pawn> {
        val coordinates = mutableListOf<Pawn>()
        repeat(BOARD_SIZE) { row ->
            repeat(BOARD_SIZE) { column ->
                val pawn = board[row][column]
                if (pawn.type != PawnType.EMPTY && pawn.type != ignoredPawnType) {
                    pawn.row = row
                    pawn.column = column
                    coordinates.add(pawn)
                }
            }
        }
        return coordinates
    }
}

class Game {
    private val board = Board()
    private val pawnMovesHistory = Stack<PawnMoveLog>()

    fun printBoard() = board.printBoard()

    fun movePawn(movePawnCommand: MovePawnCommand) {
        val moveType = validatePawnMove(movePawnCommand)
        if (moveType == PawnMoveType.INVALID) {
            throw InvalidMoveException()
        }

        with(movePawnCommand.boardCoordinates) {
            val pawn = board.getPawnAt(startRow, startColumn)
            board.setPawnAt(startRow, startColumn)
            board.setPawnAt(endRow, endColumn, pawn)
            pawn.markMoved()

            if (moveType == PawnMoveType.EN_PASSANT) {
                val lastMoveCoordinates = pawnMovesHistory.peek().boardCoordinates
                board.setPawnAt(lastMoveCoordinates.endRow, lastMoveCoordinates.endColumn)
            }

            pawnMovesHistory.push(PawnMoveLog(this, abs(startRow - endRow) == 2))
        }

        val gameStatus = checkWinningConditions(movePawnCommand.playerPawnType)

        if (gameStatus != GameStatus.KEEP_PLAYING) {
            board.printBoard()
            println(gameStatus.message)
            throw ExitException()
        }
    }

    private fun validatePawnMove(movePawnCommand: MovePawnCommand): PawnMoveType {
        with(movePawnCommand.boardCoordinates) {
            val pawn = board.getPawnAt(startRow, startColumn)
            if (pawn.type != movePawnCommand.playerPawnType) {
                throw NoPawnFoundException(movePawnCommand.startPoint)
            }

            // check if moving too far vertical
            val verticalDiff = endRow - startRow
            val verticalDiffAbs = abs(verticalDiff)
            if (verticalDiffAbs > 2 || verticalDiffAbs == 0 || (verticalDiffAbs == 2 && !pawn.isAtStart())) {
                return PawnMoveType.INVALID
            }

            // check if going backwards
            if (verticalDiff > 0 && pawn.type == PawnType.WHITE ||
                verticalDiff < 0 && pawn.type == PawnType.BLACK
            ) {
                return PawnMoveType.INVALID
            }

            val targetPawn = board.getPawnAt(endRow, endColumn)
            val horizontalDiff = abs(startColumn - endColumn)

            // check if target point is empty
            if (targetPawn.type == PawnType.EMPTY) {
                if (pawnMovesHistory.isNotEmpty()) {
                    val lastMove = pawnMovesHistory.peek()
                    if (lastMove.isStarting &&
                        lastMove.boardCoordinates.endColumn == endColumn &&
                        abs(lastMove.boardCoordinates.endRow - endRow) == 1 &&
                        lastMove.boardCoordinates.endRow == startRow &&
                        abs(lastMove.boardCoordinates.endColumn - startColumn) == 1
                    ) {
                        return if (targetPawn.type == movePawnCommand.playerPawnType) {
                            // trying to capture own pawn via en passant
                            PawnMoveType.INVALID
                        } else {
                            PawnMoveType.EN_PASSANT
                        }
                    }
                }

                // plain move
                if (horizontalDiff != 0) {
                    return PawnMoveType.INVALID
                }
            } else {
                // check if moving too far horizontal
                if (horizontalDiff == 0 || horizontalDiff > 1) {
                    return PawnMoveType.INVALID
                }

                // check if trying to capture
                if (targetPawn.type == movePawnCommand.playerPawnType) {
                    // trying to capture own pawn
                    return PawnMoveType.INVALID
                }
            }
        }

        return PawnMoveType.VALID
    }

    private fun checkWinningConditions(playerPawnType: PawnType): GameStatus {
        // check all pawns captured condition
        val boardStringRepresentation = board.getContentAsString()
        if (!boardStringRepresentation.contains(PawnType.BLACK.stringRepresentation)) {
            return GameStatus.WHITE_WIN
        }
        if (!boardStringRepresentation.contains(PawnType.WHITE.stringRepresentation)) {
            return GameStatus.BLACK_WIN
        }

        val blackHomeRow = board.getContentAsString(0)
        if (blackHomeRow.contains(PawnType.WHITE.stringRepresentation)) {
            return GameStatus.WHITE_WIN
        }

        val whiteHomeRow = board.getContentAsString(7)
        if (whiteHomeRow.contains(PawnType.BLACK.stringRepresentation)) {
            return GameStatus.BLACK_WIN
        }

        val oppositePawnsWithCoordinates = board.getOppositePawnsWithCoordinates(playerPawnType)
        for (pawn in oppositePawnsWithCoordinates) {
            repeat(Board.BOARD_SIZE) { row ->
                repeat(Board.BOARD_SIZE) { column ->
                    val pawnMoveType = validatePawnMove(
                        MovePawnCommand(
                            BoardCoordinates(pawn.row, pawn.column, row, column),
                            "doesn't matter",
                            pawn.type
                        )
                    )

                    if (pawnMoveType != PawnMoveType.INVALID) {
                        // if got here, it means, that there is a valid move for an opposite player - no stalemate
                        return GameStatus.KEEP_PLAYING
                    }
                }
            }
        }

        // if got here, it means that no pawn can do a valid move - stalemate
        return GameStatus.STALEMATE
    }
}

class GameManager(fistPlayerName: String, secondPlayerName: String) {
    private val game = Game()
    private val players = listOf(
        Player(fistPlayerName, "white", PawnType.WHITE),
        Player(secondPlayerName, "black", PawnType.BLACK)
    )

    companion object {
        val validPlayerTurnRegex = "[a-h][1-8][a-h][1-8]".toRegex()
    }

    fun run() {
        game.printBoard()
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
                    game.movePawn(movePawnCommand)
                    game.printBoard()
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
