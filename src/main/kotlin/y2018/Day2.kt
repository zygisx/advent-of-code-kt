package y2018

import java.lang.IllegalArgumentException

data class Occurrances(val twice: Int, val thrice: Int)

class Day2 : Day {
    override fun day() = 2

    private fun getInput() = getInputAsList()

    fun part1(): Int {
        val input = getInput()

        val occurrences = input.map {
            val counter = mutableMapOf<Int, Int>()
            it.chars()
                    .forEach { c ->
                        val currentCount = counter.getOrDefault(c, 0)
                        counter[c] = currentCount + 1
                    }
            Occurrances(
                    if (counter.values.any { it == 2 }) 1 else 0,
                    if (counter.values.any { it == 3 }) 1 else 0
            )
        }

        return occurrences.sumBy { it.twice } * occurrences.sumBy { it.thrice }
    }

    data class SimilarStrings(val no1: String, val no2: String)

    private fun findOneCharDiffSrings(input: List<String>): SimilarStrings {
        for (i in input.indices) {
            val baseStr = input[i]

            for (j in i+1 until input.size) {
                var diff = 0
                val cmpStr = input[j]

                baseStr.forEachIndexed { idx, ch ->
                    if (ch != cmpStr[idx]) diff++
                }

                if (diff == 1) return SimilarStrings(baseStr, cmpStr)
            }
        }
        throw IllegalArgumentException("Similar strings not found")
    }

    fun part2(): String {
        val input = getInput()
        val similar = findOneCharDiffSrings(input)

        return similar.no1.mapIndexed { idx, ch -> if (ch == similar.no2[idx]) ch else "" }.joinToString("")
    }
}

fun main() {
    val day2 = Day2()
    println(day2.part1())
    println(day2.part2())
}