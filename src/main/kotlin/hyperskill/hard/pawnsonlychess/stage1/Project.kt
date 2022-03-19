package hyperskill.hard.pawnsonlychess.stage1

class ChessBoard() {
    private val board = MutableList(8) { MutableList<String>(8) { " " } }

    init {
        board[1] = MutableList(8) { "B" }
        board[6] = MutableList(8) { "W" }
    }

    fun printBoard() {
        println(" Pawns-Only Chess")
        println("  +---+---+---+---+---+---+---+---+")
        repeat(8) {
            val row = 8 - it
            val boardRowContent = board[it].joinToString(" | ")
            println("$row | $boardRowContent |")
            println("  +---+---+---+---+---+---+---+---+")
        }
        println("    a   b   c   d   e   f   g   h")
    }
}

fun main() {
    ChessBoard().printBoard()
}