package y2021


object Day4 : Day {
    override fun day() = 4

    private fun getInput() = parseInput(getInputAsString())

    data class Bingo(val luckyNumbers: List<Int>, val cards: List<Card>)
    data class Card(val numbers: List<List<Int>>)

    private fun parseInput(input: String): Bingo {
        val lines = input.lines()
        val luckyNumbers = lines[0].split(",").map { it.toInt() }

        val whiteSpace = "\\s+".toRegex()
        val numbers = lines.drop(1).chunked(6).map {
            Card(it
                .drop(1)
                .map { it.split(whiteSpace).filter { it.isNotBlank() }.map { it.toInt() } })
        }
        return Bingo(luckyNumbers, numbers)
    }

    private fun hasBingo(numbers: Set<Int>, cards: List<Card>): List<Int> {
        fun hasLine(card: Card) =
            (0..4).any { numbers.containsAll(card.numbers[it])  }
        fun hasColumn(card: Card) =
            (0..4).any { col ->
                val column = card.numbers.map { it[col] }
                numbers.containsAll(column)
            }
        return cards.withIndex().filter { (_, card) ->
            hasLine(card) || hasColumn(card)
        }.map { it.index }
    }

    fun part1(): Int {
        val bingo = getInput()

        val answer = bingo.luckyNumbers.withIndex().firstNotNullOf { (idx, luckyNumber) ->
            val luckyNumbers = bingo.luckyNumbers.subList(0, idx+1).toSet()
            val winningCardIdx = hasBingo(luckyNumbers, bingo.cards).firstOrNull()
            if (winningCardIdx != null) {
                val unusedNumberSum = bingo.cards[winningCardIdx].numbers.flatten().filter { it !in luckyNumbers }.sum()
                luckyNumber * unusedNumberSum
            } else null
        }
        return answer
    }

    fun part2(): Int {
        val bingo = getInput()
        val numOfCards = bingo.cards.size
        val answer = (bingo.luckyNumbers.size downTo 0).firstNotNullOf { idx ->
            val luckyNumbers = bingo.luckyNumbers.subList(0, idx).toSet()
            val winningCardIndices = hasBingo(luckyNumbers, bingo.cards)
            if (winningCardIndices.size < numOfCards) {
                val lastToWin = (0 until numOfCards).subtract(winningCardIndices).first()
                val luckyNumber = bingo.luckyNumbers[idx]
                val unusedNumberSum = bingo.cards[lastToWin].numbers.flatten().filter { it !in luckyNumbers }.sum() - luckyNumber
                luckyNumber * unusedNumberSum
            } else null
        }

        return answer
    }
}

fun main() {
    println(Day4.part1())
    println(Day4.part2())
}