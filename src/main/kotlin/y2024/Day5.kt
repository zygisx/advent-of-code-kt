package y2024

import misc.Point
import misc.swap

object Day5 : Day {
    override fun day() = 5

    private fun getInput() = getInputAsList()

    class Print(val rules: Map<Int, Set<Int>>, val updates: List<List<Int>>) {
        companion object {
            fun parse(inputList: List<String>): Print {
                val rules = inputList.takeWhile { it.isNotBlank() }.map { it.split("|").map { it.toInt() } }
                    .groupBy { it.first() }
                    .mapValues { entry -> entry.value.flatten().filter { it != entry.key }.toSet() }
                val updates = getInput().dropWhile { it.isNotBlank() }.drop(1).map { it.split(",").map { it.toInt() } }
                return Print(rules, updates)
            }
        }
    }

    private fun getFailedUpdateIdx(print: Print, updates: List<Int>): Int {
        val mustBePrintedBefore = mutableSetOf<Int>()
        for ((idx, update) in updates.withIndex()) {
            val mustBePrintedAfter = print.rules[update] ?: emptySet()
            val contradict = mustBePrintedBefore.intersect(mustBePrintedAfter).isNotEmpty()
            if (contradict) {
                return idx
            }
            mustBePrintedBefore.add(update)
        }
        return -1
    }


    fun part1(): Int {
        val print = Print.parse(getInput())
        return print.updates.filter { getFailedUpdateIdx(print, it) == -1 }.sumOf { it.get(it.size / 2) }
    }

    fun part2(): Int {
        val print = Print.parse(getInput())
        val badUpdates = print.updates.filter { getFailedUpdateIdx(print, it) != -1 }
        return badUpdates.sumOf {
            val fixed = it.toMutableList()
            do {
                val failedIdx = getFailedUpdateIdx(print, fixed)
                if (failedIdx != -1) {
                    fixed.swap(failedIdx, failedIdx - 1)
                }
            } while (failedIdx != -1)
            fixed[fixed.size / 2]
        }
    }
}

fun main() {
    println(Day5.part1())
    println(Day5.part2())
}
