package y2023

import misc.splitWithPredicate
import java.lang.StringBuilder
import kotlin.time.measureTimedValue


object Day13 : Day {
    override fun day() = 13

    data class Mirror(val rows: List<String>, val columns: List<String>, var smudgeX: Int? = null, var smudgeY: Int? = null) {

        fun rowsAdj() =
            if (smudgeX == null) rows
            else {
                rows.mapIndexed { y, row ->  if (y == smudgeY) invert(row, smudgeX!!) else row }
            }

        fun columnsAdj() =
            if (smudgeY == null) columns
            else {
                columns.mapIndexed { x, column ->  if (x == smudgeX) invert(column, smudgeY!!) else column }
            }

        private fun invert(str: String, idx: Int): String {
            val char = when (str[idx]) {
                '#' -> '.'
                '.' -> '#'
                else -> error("Unreacchabel")
            }
            val builder = StringBuilder(str)
            builder.set(idx, char)
            return builder.toString()
        }
    }

    private fun getInput() = getInputAsList()
        .splitWithPredicate { it.isBlank() }
        .map { it.toList() }
        .map { rows ->
            val columns = mutableListOf<String>()
            (0..<rows.first().length).forEach { idx ->
                columns.add(rows.map { it[idx]!! }.joinToString(""))
            }
            Mirror(rows, columns)
        }

    private fun findIn(rows: List<String>, smudge: Int?): Int? {
        val candidateIndices = rows.windowed(2)
            .mapIndexedNotNull { idx, pair ->
                if (pair[0] == pair[1]) idx else null
            }

        val mirrorIdx = candidateIndices.firstOrNull { candidate ->
            val positionsToCheck = kotlin.math.min(candidate, rows.size - candidate - 2)
            val smudgeInRange = when (smudge) {
                null -> true
                else -> smudge in (candidate-positionsToCheck..candidate+positionsToCheck+1)
            }
             smudgeInRange && (1..positionsToCheck).all {
                rows[candidate-it] == rows[candidate+it+1]
            }
        }
        return mirrorIdx
    }

    private fun mirrorSum(mirror: Mirror): Int? {
        val rowsIdx = findIn(mirror.rowsAdj(), mirror.smudgeY)
        val columnsIdx = findIn(mirror.columnsAdj(), mirror.smudgeX)
        return when {
            rowsIdx != null -> 100 * (rowsIdx+1)
            columnsIdx != null -> columnsIdx+1
            else -> null
        }
    }

    fun part1(): Int {
        val mirrors = getInput()
        return mirrors.mapNotNull { mirrorSum(it) }.sum()
    }

    private fun mirrorSumWithSmudge(mirror: Mirror): Int {
        val xMax = mirror.columns.size
        val yMax = mirror.rows.size
        val smudgeCandidateSeq = sequence {
            (0..<yMax).forEach { y ->
                (0..<xMax).forEach { x ->
                    yield(x to y)
                }
            }
        }
        return smudgeCandidateSeq.firstNotNullOf {
            mirror.smudgeX = it.first
            mirror.smudgeY = it.second
            mirrorSum(mirror)
        }
    }

    fun part2(): Int {
        val mirrors = getInput()
        return mirrors.map { mirrorSumWithSmudge(it) }.sum()
    }
}

fun main() {
    val (part1Answer, part1Duration) = measureTimedValue { Day13.part1() }
    println("$part1Answer in ${part1Duration.inWholeMilliseconds} ms")

    val (part2Answer, part2Duration) = measureTimedValue { Day13.part2() }
    println("$part2Answer in ${part2Duration.inWholeMilliseconds} ms")
}