package hyperskill.challenging.flashcards.stage1

data class FlashCard(val term: String, val definition: String) {
    fun print() {
        println("""
            Card:
            $term
            Definition:
            $definition
        """.trimIndent())
    }
}

fun main() {
    val card = FlashCard("2 + 2", "4")
    card.print()
}
