package hyperskill.easy.simpletictactoe.stage3

fun main() {
    val board = readBoard()
    printBoard(board)
    checkGameState(board)
}

fun readBoard(): MutableList<MutableList<Char>> {
    print("Enter cells: ")
    val input = readLine()!!
    return mutableListOf(
        mutableListOf(input[0], input[1], input[2]),
        mutableListOf(input[3], input[4], input[5]),
        mutableListOf(input[6], input[7], input[8]),
    )
}

fun printBoard(board: List<List<Char>>) {
    println("---------")
    for (row in 0..2) {
        print("| ")
        print(board[row].joinToString(" "))
        println(" |")
    }
    println("---------")
}

fun checkGameState(board: List<List<Char>>) {
    val hasEmptyCells = board.flatten().count { it == '_' }.let { it > 0 }
    val hasXWon = hasWon(board, 'X')
    val hasOWon = hasWon(board, 'O')
    val oxDiff = board.flatten()
        .filter { it == 'O' || it == 'X' }
        .groupingBy { it }
        .eachCount()
        .let { kotlin.math.abs((it['X'] ?: 0) - (it['O'] ?: 0)) }

    println(
        when {
            hasXWon && hasOWon || oxDiff >= 2 -> "Impossible"
            hasXWon -> "X wins"
            hasOWon -> "O wins"
            !hasEmptyCells && !hasXWon && !hasOWon -> "Draw"
            hasEmptyCells && !hasXWon && !hasOWon -> "Game not finished"
            else -> "Impossible"
        }
    )
}

fun hasWon(board: List<List<Char>>, potentialWinner: Char): Boolean {
    // rows
    for (row in 0..2) {
        if (board[row].joinToString("") == "$potentialWinner$potentialWinner$potentialWinner") return true
    }

    // columns
    for (columns in 0..2) {
        if (board[0][columns] == potentialWinner && board[1][columns] == potentialWinner && board[2][columns] == potentialWinner) return true
    }

    // diagonals
    if (board[0][0] == potentialWinner && board[1][1] == potentialWinner && board[2][2] == potentialWinner
        || board[0][2] == potentialWinner && board[1][1] == potentialWinner && board[2][0] == potentialWinner
    ) return true

    return false
}
