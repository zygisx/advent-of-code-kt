package y2021


object Day6 : Day {
    override fun day() = 6

    private fun getInput() = getInputAsString().split(",").filter { it.isNotBlank() }.map { it.toInt() }

    private fun runSimulations(initial: List<Int>, rounds: Int): Long {
        val fishTimers = mutableMapOf<Int, Long>() // days to count
        initial.forEach {
            fishTimers[it] = fishTimers[it]?.inc() ?: 1
        }
        (0 until rounds).forEach {
            val zeros = fishTimers[0] ?: 0
            (1..8).forEach { // all advances
                fishTimers[it-1] = fishTimers[it] ?: 0
            }
            fishTimers[6] = (fishTimers[6] ?: 0) + zeros
            fishTimers[8] = zeros
        }

        return fishTimers.values.sum()
    }

    fun part1(): Long {
        val initial = getInput()
        return runSimulations(initial, 80)
    }

    fun part2(): Long {
        val initial = getInput()
        return runSimulations(initial, 256)
    }
}

fun main() {
    println(Day6.part1())
    println(Day6.part2())
}