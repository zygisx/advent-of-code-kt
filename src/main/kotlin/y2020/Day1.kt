package y2020


class Day1 : Day {
    override fun day() = 1

    companion object {
        val NoResult = 0 to 0
    }

    private fun getExpenses() = getInputAsList().map { it.toInt() }

    private fun findMatchingPair(expenses: List<Int>, target: Int): Pair<Int, Int> {
        for (expense in expenses) {
            val idx = expenses.binarySearch { expense + it - target }
            if (idx >= 0) {
                return expense to expenses[idx]
            }
        }

        return NoResult
    }

    fun part1(): Int {
        val expenses = getExpenses()
        val ordered = expenses.sorted()

        val matchingPair = findMatchingPair(ordered, 2020)

        return matchingPair.first * matchingPair.second
    }

    fun part2(): Int {
        val expenses = getExpenses()
        val ordered = expenses.sorted()

        for (expense in ordered) {
            val remaining = 2020 - expense
            val matchingPair = findMatchingPair(ordered, remaining)
            if (matchingPair != NoResult) {
                return expense * matchingPair.first * matchingPair.second
            }
        }

        return 0
    }
}

fun main() {
    val day1 = Day1()
    println(day1.part1())
    println(day1.part2())
}