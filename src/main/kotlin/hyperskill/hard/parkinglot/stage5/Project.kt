package hyperskill.hard.parkinglot.stage5

data class Car(val plateNumber: String, val color: String) {
    override fun toString(): String = "$plateNumber $color"
}

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
            repeat(size) { index ->
                val car = spots[index]
                if (car != null) {
                    println("${index + 1} $car")
                }
            }
        }
    }

    fun registrationPatesByColor(color: String) {
        val plateNumbers = spots.filterNotNull()
            .filter { it.color.lowercase() == color.lowercase() }
            .joinToString(", ") { it.plateNumber }
        println(plateNumbers.ifEmpty { "No cars with color $color were found." })
    }

    fun spotsByColor(color: String) {
        val indexes = MutableList(0) { 0 }
        repeat(size) { index ->
            if (spots[index]?.color?.lowercase() == color.lowercase()) {
                indexes.add(index + 1)
            }
        }
        println(
            if (indexes.size > 0) {
                indexes.joinToString(", ")
            } else {
                "No cars with color $color were found."
            }
        )
    }

    fun spotsByRegistration(registration: String) {
        val indexes = MutableList(0) { 0 }
        repeat(size) { index ->
            if (spots[index]?.plateNumber == registration) {
                indexes.add(index + 1)
            }
        }
        println(
            if (indexes.size > 0) {
                indexes.joinToString(", ")
            } else {
                "No cars with registration number $registration were found."
            }
        )
    }
}

fun main() {
    var parkingLot: ParkingLot? = null

    while (true) {
        val input = readLine()!!
        if (input == "exit") return

        val splittedInput = input.split(" ")
        try {
            when (splittedInput[0]) {
                "create" -> parkingLot = ParkingLot(splittedInput[1].toInt())
                "park" -> parkingLot!!.park(Car(splittedInput[1], splittedInput[2]))
                "leave" -> parkingLot!!.leave(splittedInput[1].toInt())
                "status" -> parkingLot!!.status()
                "reg_by_color" -> parkingLot!!.registrationPatesByColor(splittedInput[1])
                "spot_by_color" -> parkingLot!!.spotsByColor(splittedInput[1])
                "spot_by_reg" -> parkingLot!!.spotsByRegistration(splittedInput[1])
            }
        } catch (e: NullPointerException) {
            println("Sorry, a parking lot has not been created.")
        } catch (e: Exception) {
            println("Unknown exception has occurred: ${e.message}")
        }
    }
}
