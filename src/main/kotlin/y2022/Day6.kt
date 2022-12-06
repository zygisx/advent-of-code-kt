package y2022


object Day6 : Day {
    override fun day() = 6

    private fun getInput() = getInputAsList()

    private fun indexOfFirstNonRepeatingSequence(line: String, sequenceLength: Int): Int {
        val indexOfFirst = line.windowed(sequenceLength, 1).indexOfFirst {
            it.toSet().size == sequenceLength
        }
        return indexOfFirst + sequenceLength
    }

    fun part1(): Int {
        val stream = getInput().first()
        return indexOfFirstNonRepeatingSequence(stream, 4)
    }

    fun part2(): Int {
        val stream = getInput().first()
        return indexOfFirstNonRepeatingSequence(stream, 1   4)
    }
}

fun main() {
    println(Day6.part1())
    println(Day6.part2())
}