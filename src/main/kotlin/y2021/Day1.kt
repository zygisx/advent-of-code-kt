package y2021


object Day1 : Day {
    enum class Change { INC, DEC }

    override fun day() = 1

    private fun getInput() = getInputAsList().map { it.toInt() }

    private fun calcChanges(measurements: List<Int>): List<Int> {
        return measurements
            .dropLast(1)
            .mapIndexed { idx, _ ->
                measurements[idx+1] - measurements[idx]
            }
    }

    fun part1(): Int {
        val measurements = getInput()
        val changes = calcChanges(measurements)

        return changes.count { it > 0 }
    }

    fun part2(): Int {
        val measurements = getInput()
        val slidingWindowSums = measurements
            .dropLast(2)
            .mapIndexed { idx, _ ->
                measurements[idx] + measurements[idx+1] + measurements[idx+2]
            }
        val changes = calcChanges(slidingWindowSums)

        return changes.count { it > 0 }
    }
}

fun main() {
    println(Day1.part1())
    println(Day1.part2())
}