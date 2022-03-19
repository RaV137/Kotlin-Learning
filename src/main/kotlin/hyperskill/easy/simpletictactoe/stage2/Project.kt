package hyperskill.easy.simpletictactoe.stage2

fun main() {
    val board = readBoard()
    printBoard(board)
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
