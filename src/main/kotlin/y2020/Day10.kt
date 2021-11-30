package y2020

import misc.Counter


class Day10 : Day {
    override fun day() = 10

    private fun getInput() = getInputAsList().map { it.toInt() }

    private fun jolts(): List<Int> {
        val jolts = getInput().sorted()
        return listOf(0) + jolts + listOf(jolts.last() + 3)
    }

    fun part1(): Int {
        val jolts = jolts()

        val diffsCounter = (1 until jolts.size).fold(Counter<Int>()) { counter, it ->
            counter.inc(jolts[it] - jolts[it-1])
            counter
        }

        return diffsCounter[1] * diffsCounter[3]

    }

    fun part2(): Long {
        val jolts = jolts()

        val cache = mutableMapOf<Int, Long>()
        fun findJoltsSeq(lastTaken: Int): Long {
            val acceptable = jolts.dropWhile { it <= lastTaken }.takeWhile { lastTaken + 3 >= it }
            if (acceptable.isEmpty()) {
                return 1
            }
            return acceptable.map {
                cache[it] ?: findJoltsSeq(it).let { res -> cache[it] = res; res }
            }.sum()
        }

        return findJoltsSeq(0)
    }
}

fun main() {
    val day10 = Day10()

    println(day10.part1())
    println(day10.part2())
}