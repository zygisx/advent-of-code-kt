package y2024

import misc.splitToInts
import kotlin.math.abs


object Day1 : Day {
    override fun day() = 1

    private fun getInput(): Pair<List<Int>, List<Int>> {
        val lists = getInputAsList().map { it.splitToInts() }.map { it[0] to it[1] }
        return lists.map { it.first } to lists.map { it.second }
    }

    fun part1(): Int {
        val (listA, listB) = getInput()
        return listA.sorted().zip(listB.sorted()).map {
            abs(it.first - it.second)
        }.sum()
    }

    fun part2(): Int {
        val (listA, listB) = getInput()
        return listA.map { a ->
            a * listB.count { b -> a == b}
        }.sum()
    }
}

fun main() {
    println(Day1.part1())
    println(Day1.part2())
}

