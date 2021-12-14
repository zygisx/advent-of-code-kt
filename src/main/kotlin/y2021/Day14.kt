package y2021

import kotlin.math.roundToLong


object Day14 : Day {
    override fun day() = 14

    private fun getInput() = getInputAsList()

    private fun parseInstruction(lines: List<String>): Instruction {
        val template = lines.first()
        val rules = lines.drop(2).associate {
            val (from, to) = it.split(" -> ").filter { it.isNotBlank() }
            from to listOf("${from[0]}$to", "$to${from[1]}")
        }
        return Instruction(template, rules)
    }

    data class Instruction(val template: String, val rules: Map<String, List<String>>)

    private fun calcPairs(runs: Int, template: String, rules: Map<String, List<String>>): Map<String, Long> {
        val pairsCounter = template.windowed(2).groupingBy { it }.eachCount().mapValues { it.value.toLong() }

        return (0 until runs).fold(pairsCounter) { pairs, _ ->
            // replace old pairs with new pairs
            pairs.flatMap { (pair, count) ->
                (rules[pair] ?: emptyList()).map { it to count }
            }.groupBy { it.first }.mapValues { it.value.sumOf { it.second } }
        }
    }

    private fun calcAnswer(pairs: Map<String, Long>): Long {
        val charCounter = mutableMapOf<Char, Long>()
        pairs.forEach { (pair, count) ->
            charCounter[pair[0]] = (charCounter[pair[0]] ?: 0L) + count
            charCounter[pair[1]] = (charCounter[pair[1]] ?: 0L) + count
        }
        return (charCounter.values.maxOrNull()!! / 2.0).roundToLong() -
                (charCounter.values.minOrNull()!! / 2.0).roundToLong()
    }

    fun part1(): Long {
        val (template, rules) = parseInstruction(getInput())
        val finalPairs = calcPairs(10, template, rules)
        return calcAnswer(finalPairs)
    }

    fun part2(): Long {
        val (template, rules) = parseInstruction(getInput())
        val finalPairs = calcPairs(40, template, rules)
        return calcAnswer(finalPairs)
    }
}

fun main() {
    println(Day14.part1())
    println(Day14.part2())
}