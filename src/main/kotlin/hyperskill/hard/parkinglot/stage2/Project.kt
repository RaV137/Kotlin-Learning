package hyperskill.hard.parkinglot.stage2

data class Car(val plateNumber: String, val color: String)

class ParkingLot(private val size: Int) {
    val spots = MutableList<Car?>(size) { null }

    fun leave(spot: Int) {
        val realSpot = spot - 1
        if (spots[realSpot] == null) {
            println("There is no car in spot $spot.")
        } else {
            println("Spot $spot is free.")
        }
    }

    fun park(car: Car) {
        repeat(size) { index ->
            if (spots[index] == null) {
                spots[index] = car
                println("${car.color} car parked in spot ${index + 1}.")
                return
            }
        }
    }
}

fun main() {
    val parkingLot = ParkingLot(2)
    parkingLot.spots[0] = Car("test", "test")

    val input = readLine()!!.split(" ")

    when (input[0]) {
        "park" -> parkingLot.park(Car(input[1], input[2]))
        "leave" -> parkingLot.leave(input[1].toInt())
    }
}
