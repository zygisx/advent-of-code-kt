package y2021

import kotlin.math.abs


object Day7 : Day {
    override fun day() = 7

    private fun getInput() = getInputAsString().split(",").filter { it.isNotBlank() }.map { it.toInt() }

    private fun calcFuelNeededPart1(positions: List<Int>, target: Int): Int {
        return positions.fold(0) { acc, pos ->
            acc + abs(pos - target)
        }
    }

    private val cache = mutableMapOf<Int, Int>()
    private fun calcFuelForDistance(distance: Int): Int {
        if (distance in cache) return cache[distance]!!
        return when (distance) {
            0 -> 0
            1 -> 1
            else -> (calcFuelForDistance(distance - 1) + distance)
                .also { cache[distance] = it }
        }
    }

    private fun calcFuelNeededPart2(positions: List<Int>, target: Int): Int {
        return positions.fold(0) { acc, pos ->
            acc + calcFuelForDistance(abs(pos - target))
        }
    }

    fun part1(): Int {
        val positions = getInput()
        val minPos = positions.minOrNull()!!
        val maxPos = positions.maxOrNull()!!
        return (minPos..maxPos).map { calcFuelNeededPart1(positions, it) }.minOrNull()!!
    }

    fun part2(): Int {
        val positions = getInput()
        val minPos = positions.minOrNull()!!
        val maxPos = positions.maxOrNull()!!
        return (minPos..maxPos).map { calcFuelNeededPart2(positions, it) }.minOrNull()!!
    }
}

fun main() {
    println(Day7.part1())
    println(Day7.part2())
}