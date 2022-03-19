package hyperskill.easy.simpletictactoe.stage5

fun main() {
    val board = createBoard()
    printBoard(board)
    for (iteration in 1..9) {
        val currChar = if (iteration % 2 == 1) 'X' else 'O'
        doAMove(board, currChar)
        printBoard(board)
        if (checkGameFinished(board)) {
            return
        }
    }
}

fun createBoard(): MutableList<MutableList<Char>> {
    return mutableListOf(
        mutableListOf('_', '_', '_'),
        mutableListOf('_', '_', '_'),
        mutableListOf('_', '_', '_'),
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

fun doAMove(board: MutableList<MutableList<Char>>, char: Char) {
    while (true) {
        print("Enter the coordinates: ")

        try {
            val (row, column) = readLine()!!.split(" ").map { it.toInt() - 1 }
            if (board[row][column] != '_') {
                println("This cell is occupied! Choose another one!")
                continue
            }
            board[row][column] = char
            return
        } catch (e: IndexOutOfBoundsException) {
            println("Coordinates should be from 1 to 3!")
        } catch (e: NumberFormatException) {
            println("You should enter numbers!")
        }
    }
}

fun checkGameFinished(board: List<List<Char>>): Boolean {
    val hasEmptyCells = board.flatten().count { it == '_' }.let { it > 0 }
    val hasXWon = hasWon(board, 'X')
    val hasOWon = hasWon(board, 'O')

    return if (hasXWon) {
        println("X wins")
        true
    } else if (hasOWon) {
        println("O wins")
        true
    } else if (!hasEmptyCells && !hasXWon && !hasOWon) {
        println("Draw")
        true
    } else {
        false
    }
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
