package hyperskill.hard.steganography.stage4

fun main() {
    problem1()
}

data class Comment(val id: Int, val body: String, val author: String)

fun problem1() {
    val commentsData: MutableList<Comment> = mutableListOf(
        Comment(1, "Test", "Admin"),
        Comment(2, "Heey", "ActiveUser")
    )

    repeat(commentsData.size) {
        val (_, text, author) = commentsData[it]
        println("Author: $author; Text: $text")
    }
}
