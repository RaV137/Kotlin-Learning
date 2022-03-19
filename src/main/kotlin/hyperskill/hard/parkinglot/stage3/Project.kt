package hyperskill.hard.parkinglot.stage3

data class Car(val plateNumber: String, val color: String)

class ParkingLot(private val size: Int) {
    private val spots = MutableList<Car?>(size) { null }

    fun leave(spot: Int) {
        val realSpot = spot - 1
        if (spots[realSpot] == null) {
            println("There is no car in spot $spot.")
        } else {
            spots[realSpot] = null
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

        println("Sorry, the parking lot is full.")
    }
}

fun main() {
    val parkingLot = ParkingLot(20)

    while(true) {
        val input = readLine()!!
        if (input == "exit") return

        val splittedInput = input.split(" ")
        when (splittedInput[0]) {
            "park" -> parkingLot.park(Car(splittedInput[1], splittedInput[2]))
            "leave" -> parkingLot.leave(splittedInput[1].toInt())
        }
    }
}
