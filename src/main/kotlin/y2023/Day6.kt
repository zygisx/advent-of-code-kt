package y2023

import misc.splitToLongs


object Day6 : Day {
    override fun day() = 6

    private fun getInput() = getInputAsList()

    private fun distanceReached(time: Long, buttonTime: Long): Long {
        val leftTime = time - buttonTime
        return leftTime * buttonTime
    }

    fun part1(): Int {
        val (times, distances) = getInput()
            .map { it.drop(10).splitToLongs() }
        return times.zip(distances).map { (time, distance) ->
            (1..<time).count {
                distanceReached(time, it) > distance
            }
        }.reduce { a, b -> a * b}
    }

    private fun findStart(time: Long, distance: Long): Long {
        var low = 1L
        var high = time - 1
        while (low <= high) {
            val mid = low + (high - low) / 2;
            val leftTime = time - mid
            val distanceReached = leftTime * mid

            if (distanceReached > distance) {
                high = mid - 1;
            }
            if (distanceReached <= distance) {
                low = mid + 1
            }
        }
        return low
    }

    fun part2(): Long {
        val (time, distance) = getInput()
            .map { it.drop(10).replace(" ", "").toLong() }
        val start = findStart(time, distance)
        val end = time - start + 1
        return (end) - start
    }
}

fun main() {
    println(Day6.part1())
    println(Day6.part2())
}
