package hyperskill.easy.cinemaroommanager.stage5

import java.math.RoundingMode

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
    while (true) {
        println()
        println("1. Show the seats")
        println("2. Buy a ticket")
        println("3. Statistics")
        println("0. Exit")

        when (readLine()!!.toInt()) {
            1 -> printCinemaPlan(rows, seatsInRow, cinemaPlan)
            2 -> buyASeat(rows, seatsInRow, cinemaPlan)
            3 -> printStatistics(rows, seatsInRow, cinemaPlan)
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
    while (true) {
        println()
        println("Enter a row number:")
        val row = readLine()!!.toInt()
        println("Enter a seat number in that row:")
        val seat = readLine()!!.toInt()

        try {
            checkBoughtSeat(row, seat, cinemaPlan)
        } catch (e: IndexOutOfBoundsException) {
            println("\nWrong input!")
            continue
        } catch (e: IllegalArgumentException) {
            println("\n${e.message}")
            continue
        }

        cinemaPlan[row - 1][seat - 1] = "B"
        val ticketPrice = calculateTicketPrice(rows, seatsInRow, row)
        println("\nTicket price: \$$ticketPrice")
        return
    }
}

fun checkBoughtSeat(row: Int, seat: Int, cinemaPlan: List<List<String>>) {
    if (cinemaPlan[row - 1][seat - 1] == "B") throw IllegalArgumentException("That ticket has already been purchased!")
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

fun printStatistics(rows: Int, seatsInRow: Int, cinemaPlan: List<List<String>>) {
    val numberOfBoughtTickets = cinemaPlan.flatten().count { it == "B" }
    val percentageOfBoughtTickets = ((numberOfBoughtTickets * 100.0) / (rows * seatsInRow))
        .toBigDecimal()
        .setScale(2, RoundingMode.HALF_UP)
    println(
        """
        
        Number of purchased tickets: $numberOfBoughtTickets
        Percentage: $percentageOfBoughtTickets%
        Current income: $${calculateCurrentIncome(rows, seatsInRow, cinemaPlan)}
        Total income: $${calculateTotalIncome(rows, seatsInRow)}
    """.trimIndent()
    )
}

fun calculateCurrentIncome(rows: Int, seatsInRow: Int, cinemaPlan: List<List<String>>): Int {
    var currentIncome = 0
    for (row in 0 until rows) {
        for (seat in 0 until seatsInRow) {
            if (cinemaPlan[row][seat] == "B") {
                currentIncome += calculateTicketPrice(rows, seatsInRow, row + 1)
            }
        }
    }
    return currentIncome
}

fun calculateTotalIncome(rows: Int, seatsInRow: Int): Int {
    val seats = rows * seatsInRow
    return if (seats <= SMALL_SIZE_SEATS_CAP) {
        seats * SCREEN_CLOSE_PRICE
    } else {
        (rows / 2).let { it * seatsInRow * SCREEN_CLOSE_PRICE + (rows - it) * seatsInRow * SCREEN_FAR_PRICE }
    }
}
