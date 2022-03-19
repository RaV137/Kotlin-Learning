package hyperskill.hard.indigocardgame.stage5

import java.io.File

fun main() {
    problem3()
}

fun problem1() {
    val rootFile = File("C:\\Users\\TC99GJ\\Projects\\kotlin_learining_project\\src\\main\\resources\\basedir\\basedir")
    var maxFilesSize = 0
    var maxFilesDirName = ""
    for (file in rootFile.listFiles()!!) {
        if (file.isDirectory) {
            val currSize = (file.listFiles() ?: emptyArray()).size
            if (currSize > maxFilesSize) {
                maxFilesSize = currSize
                maxFilesDirName = file.name
            }
        }
    }
    println("$maxFilesDirName: $maxFilesSize")
}

fun problem2() {
    val rootFile = File("C:\\Users\\TC99GJ\\Projects\\kotlin_learining_project\\src\\main\\resources\\basedir_2\\basedir")
    var maxDepth = 0
    var maxDepthFileName = ""
    rootFile.walkBottomUp().forEach {
        val depth = it.toString().count { char -> char == '\\' }
        if (depth > maxDepth) {
            maxDepth = depth
            maxDepthFileName = it.name

            println("NEW: $maxDepth $it")
        }
    }
    println()

    maxDepth -= 9

    println("$maxDepthFileName: $maxDepth")
}

fun problem3() {
    val rootFile = File("C:\\Users\\TC99GJ\\Projects\\kotlin_learining_project\\src\\main\\resources\\basedir_3\\basedir")
    val emptyDirs = mutableListOf<String>()
    rootFile.walkTopDown().forEach {
        if (it.isDirectory && it.listFiles()!!.isEmpty()) {
            emptyDirs.add(it.name)
        }
    }
    println(emptyDirs.joinToString(" "))
}
