package hyperskill.hard.parkinglot.stage4

data class Car(val plateNumber: String, val color: String)

class ParkingLot(private val size: Int) {
    private val spots = MutableList<Car?>(size) { null }

    init {
        println("Created a parking lot with $size spots.")
    }

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

    fun status() {
        if (spots.filterNotNull().count() == 0) {
            println("Parking lot is empty.")
        } else {
            repeat (size) { index ->
                val car = spots[index]
                if (car != null) {
                    println("${index + 1} ${car.plateNumber} ${car.color}")
                }
            }
        }
    }
}

fun main() {
    var parkingLot: ParkingLot? = null

    while(true) {
        val input = readLine()!!
        if (input == "exit") return

        val splittedInput = input.split(" ")
        try {
            when (splittedInput[0]) {
                "create" -> parkingLot = ParkingLot(splittedInput[1].toInt())
                "park" -> parkingLot!!.park(Car(splittedInput[1], splittedInput[2]))
                "leave" -> parkingLot!!.leave(splittedInput[1].toInt())
                "status" -> parkingLot!!.status()
            }
        } catch (e: NullPointerException) {
            println("Sorry, a parking lot has not been created.")
        }

    }
}
