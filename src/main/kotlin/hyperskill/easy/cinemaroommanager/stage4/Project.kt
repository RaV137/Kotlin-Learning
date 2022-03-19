package hyperskill.easy.cinemaroommanager.stage4

fun main() {
    val (rows, seatsInRow) = readCinemaSize()
    val cinemaPlan = createCinemaPlan(rows, seatsInRow)
    menu(rows, seatsInRow, cinemaPlan)
}

fun readCinemaSize(): List<Int> {
    println("Enter the number of rows:")
    val rows = readLine()!!.toInt()
    println("Enter the number of seats in each row:")
    val seatsInRow = readLine()!!.toInt()
    return listOf(rows, seatsInRow)
}

fun createCinemaPlan(rows: Int, seatsInRow: Int): MutableList<MutableList<String>> {
    return MutableList(rows) { MutableList(seatsInRow) { "S" } }
}

fun menu(rows: Int, seatsInRow: Int, cinemaPlan: MutableList<MutableList<String>>) {
    while(true) {
        println()
        println("1. Show the seats")
        println("2. Buy a ticket")
        println("0. Exit")

        when (readLine()!!.toInt()) {
            1 -> printCinemaPlan(rows, seatsInRow, cinemaPlan)
            2 -> buyASeat(rows, seatsInRow, cinemaPlan)
            0 -> return
        }
    }
}

fun printCinemaPlan(rows: Int, seatsInRow: Int, cinemaPlan: List<List<String>>) {
    println()
    println("Cinema:")
    val seatsHeader = (1..seatsInRow).joinToString(" ")
    println("  $seatsHeader")
    for (row in 1..rows) {
        print("$row ")
        println(cinemaPlan[row - 1].joinToString(" "))
    }
}

fun buyASeat(rows: Int, seatsInRow: Int, cinemaPlan: MutableList<MutableList<String>>) {
    println()
    println("Enter a row number:")
    val row = readLine()!!.toInt()
    println("Enter a seat number in that row:")
    val seat = readLine()!!.toInt()
    cinemaPlan[row - 1][seat - 1] = "B"
    val ticketPrice = calculateTicketPrice(rows, seatsInRow, row)
    println("Ticket price: \$$ticketPrice")
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
