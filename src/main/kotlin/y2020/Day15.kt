package y2020

import misc.Counter


object Day15 : Day {
    override fun day() = 15

    private fun getInput() = getInputAsString().split(",").map { it.toInt() }

    fun findLastSpoken(iterations: Int, initialNums: List<Int>): Int {
        val spokenRecord = initialNums
                .mapIndexed { idx, it -> it to mutableListOf(idx) }
                .toMap()
                .toMutableMap()

        var lastSpoken = initialNums.last()

        fun addToRecord(number: Int, turn: Int) {
            spokenRecord.compute(number) { _, v ->
                if (v == null) mutableListOf(turn)
                else {
                    (v + turn).takeLast(2).toMutableList()
                }
            }
        }

        (initialNums.size until iterations).forEach { turn ->
            val lastSpokenIndexBefore = spokenRecord.get(lastSpoken)
                    ?.let { if (it.size > 1) it[0] to it[1] else null }
            if (lastSpokenIndexBefore == null) {
                lastSpoken = 0
                addToRecord(0, turn)
            } else {
                val diff = lastSpokenIndexBefore.second - lastSpokenIndexBefore.first
                lastSpoken = diff
                addToRecord(diff, turn)
            }
        }
        return lastSpoken
    }

    fun part1(): Int {
        val nums = getInput()
        return findLastSpoken(2020, nums)
    }

    fun part2(): Int {
        val nums = getInput()
        return findLastSpoken(30000000, nums)

    }
}

fun main() {
    println(Day15.part1())
    println(Day15.part2())
}