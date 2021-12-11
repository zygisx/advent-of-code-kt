package y2021

import misc.Collections.stack
import java.util.ArrayDeque


object Day10 : Day {
    override fun day() = 10

    private fun getInput() = getInputAsList().map { it.toList() }

    private val charPairs = mapOf(
        '(' to ')',
        '[' to ']',
        '{' to '}',
        '<' to '>'
    )
    private val openChars = charPairs.keys
    private val part1Score = mapOf(
        ')' to 3,
        ']' to 57,
        '}' to 1197,
        '>' to 25137)
    private val part2Score = mapOf(
        ')' to 1,
        ']' to 2,
        '}' to 3,
        '>' to 4)

    data class LineCheckResult(val openChars: ArrayDeque<Char>, val firstIllegalChar: Char?)


    private fun findIllegalCharScore(line: List<Char>): LineCheckResult {
        val openStack = stack(emptyList<Char>())
        return line.firstNotNullOfOrNull {
            if (it in openChars) {
                openStack.push(it)
                null
            } else {
                val lastOpen = openStack.pop()
                val match = charPairs[lastOpen]
                if (it != match) {
                    LineCheckResult(openStack, it)
                } else null
            }
        } ?: LineCheckResult(openStack, null)
    }

    fun part1(): Int {
        val lines = getInput()
        return lines.mapNotNull { findIllegalCharScore(it).firstIllegalChar }.sumOf { part1Score[it]!! }
    }

    private fun calcScore(openChars: ArrayDeque<Char>): Long {
        return openChars.fold(0L) { score, openChar ->
            val closing = charPairs[openChar]!!
            score * 5 + part2Score[closing]!!
        }
    }

    fun part2(): Long {
        val lines = getInput()
        val scores = lines
            .map { findIllegalCharScore(it) }
            .filter { it.firstIllegalChar == null }
            .map { calcScore(it.openChars) }
            .sortedDescending()

        return scores[(scores.size / 2)]


    }
}

fun main() {
    println(Day10.part1())
    println(Day10.part2())
}