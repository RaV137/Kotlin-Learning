package hyperskill.easy.cinemaroommanager.stage2

fun main() {
    println("Enter the number of rows:")
    val rows = readLine()!!.toInt()
    println("Enter the number of seats in each row:")
    val seatsInRow = readLine()!!.toInt()
    println("Total income:")
    val income = calculateIncome(rows, seatsInRow)
    println("\$$income")
}

const val SMALL_SIZE_SEATS_CAP = 60
const val SCREEN_CLOSE_PRICE = 10
const val SCREEN_FAR_PRICE = 8

fun calculateIncome(rows: Int, seatsInRow: Int): Int {
    val seats = rows * seatsInRow
    return if (seats <= SMALL_SIZE_SEATS_CAP) {
        seats * SCREEN_CLOSE_PRICE
    } else {
        var income = 0
        val closeRows = rows / 2
        income += closeRows * seatsInRow * SCREEN_CLOSE_PRICE
        val farRows = rows - closeRows
        income += farRows * seatsInRow * SCREEN_FAR_PRICE
        income
    }
}
