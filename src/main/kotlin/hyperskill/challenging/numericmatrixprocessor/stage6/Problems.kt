package hyperskill.problems.coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    problem1()
}

fun problem1() = runBlocking {
    for (i in 200..1000 step 200) {
        launch {
            delay(1000L - i)
            print(i)
        }
    }
}
