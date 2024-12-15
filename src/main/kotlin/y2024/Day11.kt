package y2024

import misc.Collections
import misc.Console.printWithTime
import misc.splitToLongs

object Day11 : Day {

    override fun day() = 11

    private fun getInput() = getInputAsList().first().splitToLongs()

    fun blinkForStone(stone: Long): List<Long> {
        val strStone = stone.toString()
        return when {
            stone == 0L -> listOf(1)
            strStone.length % 2 == 0 -> {
                listOf(
                    strStone.substring(0, strStone.length / 2).toLong(),
                    strStone.substring(strStone.length / 2, strStone.length).toLong())
            }
            else -> listOf(stone*2024)
        }
    }

    fun stonesCount(totalIterations: Int, initial: List<Long>): Long {
        val cache = mutableMapOf<Pair<Long, Int>, Long>()

        fun stonesCountRec(stone: Long, iteration: Int): Long {
            if (iteration == totalIterations) return 1L
            if (stone to iteration in cache) return cache[stone to iteration]!!
            val stones = blinkForStone(stone)
            return stones.sumOf {
                stonesCountRec(it, iteration + 1)
            }.also { cache[stone to (iteration)] = it }
        }

        return initial.sumOf {
            stonesCountRec(it, 0)
        }
    }

    fun part1(): Long {
        val stones = getInput()
        return stonesCount(25, stones)
    }


    fun part2(): Long {
        val stones = getInput()
        return stonesCount(75, stones)
    }
}

fun main() {
    printWithTime { Day11.part1() }
    printWithTime { Day11.part2() }
}
