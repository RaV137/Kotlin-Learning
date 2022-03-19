package hyperskill.easy.cinemaroommanager.stage3

fun main() {
    val (rows, seatsInRow) = readCinemaSize()
    printCinemaPlan(rows, seatsInRow)
    val (blockedRow, blockedSeat) = readBlockedSeat()
    val ticketPrice = calculateTicketPrice(rows, seatsInRow, blockedRow)
    println("Ticket price: \$$ticketPrice")
    printCinemaPlan(rows, seatsInRow, blockedRow, blockedSeat)
}

fun readCinemaSize(): List<Int> {
    println("Enter the number of rows:")
    val rows = readLine()!!.toInt()
    println("Enter the number of seats in each row:")
    val seatsInRow = readLine()!!.toInt()
    return listOf(rows, seatsInRow)
}

fun printCinemaPlan(rows: Int, seatsInRow: Int, blockedRow: Int = -1, blockedSeat: Int = -1) {
    println("Cinema:")
    val seatsHeader = (1..seatsInRow).joinToString(" ")
    println("  $seatsHeader")
    for (row in 1..rows) {
        print("$row ")
        for (seat in 1..seatsInRow) {
            val symbol = if (blockedRow == row && blockedSeat == seat) "B" else "S"
            print("$symbol ")
        }
        println()
    }
}

fun readBlockedSeat(): List<Int> {
    println("Enter a row number:")
    val row = readLine()!!.toInt()
    println("Enter a seat number in that row:")
    val seat = readLine()!!.toInt()
    return listOf(row, seat)
}

const val SMALL_SIZE_SEATS_CAP = 60
const val SCREEN_CLOSE_PRICE = 10
const val SCREEN_FAR_PRICE = 8

fun calculateTicketPrice(rows: Int, seatsInRow: Int, blockedSeatRow: Int): Int {
    val seats = rows * seatsInRow
    return if (seats <= SMALL_SIZE_SEATS_CAP) {
        SCREEN_CLOSE_PRICE
    } else {
        if (blockedSeatRow <= rows / 2) SCREEN_CLOSE_PRICE else SCREEN_FAR_PRICE
    }
}
