package y2023

import misc.indicesOf
import misc.splitToInts
import kotlin.time.measureTimedValue


object Day12 : Day {
    override fun day() = 12

    data class Row(val record: String, val splits: List<Int>)

    private fun getInput() = getInputAsList().map { line ->
        val (record, splitsText) = line.split(" ")
        Row(record, splitsText.split(",").map { it.toInt() })
    }

    private fun rowPermutations(row: Row, cache: MutableMap<String, Int>): Int {

        fun isMatchSplit(record: String): Boolean {
            val splits = record.split(".").filter { it.isNotEmpty() }
            return splits.size == row.splits.size &&
                    splits.zip(row.splits).all { it.first.length == it.second }
//                .also { println("$splits | ${row.splits} : $it" ) }
        }

        val unknownIndices = row.record.indicesOf('?')
        val needHash = row.splits.sum() - row.record.count { it == '#' }
        val needDots = row.record.length - row.splits.sum() - row.record.count { it == '.' }

        fun settleUnknowns(unknowns: List<Char>) = unknowns
            .fold(row.record) { acc, c -> acc.replaceFirst('?', c) }

        fun findReplacement(unknowns: List<Char>, hashes: Int): Int {
            val memoKey = unknowns.joinToString("") + ":$hashes"
            if (memoKey in cache) return cache[memoKey]!!
            if (unknowns.size == unknownIndices.size) {
                val settle = settleUnknowns(unknowns)
                return (if (isMatchSplit(settle)) 1 else 0).also { cache[memoKey] = it }
            }
            if (hashes == needHash) {
                val missing = unknownIndices.size - unknowns.size
                return findReplacement(unknowns + List(missing) {'.'}, hashes)
            }
            val dots = unknowns.size - hashes
            if (dots == needDots) {
                val missing = unknownIndices.size - unknowns.size
                return findReplacement(unknowns + List(missing) {'#'}, hashes + missing)
            }
            return findReplacement(unknowns + '.', hashes) +
                    findReplacement(unknowns + '#', hashes + 1)
        }

        return findReplacement(emptyList(), 0)
    }

    fun part1(): Int {
        val rows = getInput()

        // global cache might be bad idea
        val cache = mutableMapOf<String, Int>()
        return rows.sumOf { rowPermutations(it, cache) }
    }

    private fun unfoldRow(row: Row): Row {
        val record = (1..5).map { row.record }.joinToString("?")
        val splits = (1..5).map { row.splits }.flatten()
        return Row(record, splits)
    }

    private fun rowPermutations2(row: Row): Int {

        fun isMatchSplit(record: String): Boolean {
            val splits = record.split(".").filter { it.isNotEmpty() }
            return splits.size == row.splits.size &&
                    splits.zip(row.splits).all { it.first.length == it.second }
        }

        val unknownIndices = row.record.indicesOf('?')
        val needHash = row.splits.sum() - row.record.count { it == '#' }
        val needDots = row.record.length - row.splits.sum() - row.record.count { it == '.' }

        fun settleUnknowns(unknowns: List<Char>) = unknowns
            .fold(row.record) { acc, c -> acc.replaceFirst('?', c) }

        fun findReplacement(unknowns: List<Char>, hashes: Int): Int {
            val memoKey = unknowns.joinToString("") + ":$hashes"
            if (unknowns.size == unknownIndices.size) {
                val settle = settleUnknowns(unknowns)
                return (if (isMatchSplit(settle)) 1 else 0)
            }
            if (hashes == needHash) {
                val missing = unknownIndices.size - unknowns.size
                return findReplacement(unknowns + List(missing) {'.'}, hashes)
            }
            val dots = unknowns.size - hashes
            if (dots == needDots) {
                val missing = unknownIndices.size - unknowns.size
                return findReplacement(unknowns + List(missing) {'#'}, hashes + missing)
            }
            return findReplacement(unknowns + '.', hashes) +
                    findReplacement(unknowns + '#', hashes + 1)
        }

        return findReplacement(emptyList(), 0)
    }

    fun part2(): Long {
        val rows = getInput()
        val rowsUnfolded = rows.map { unfoldRow(it) }
        println(rowsUnfolded)
        val cache = mutableMapOf<String, Int>()
        return rowsUnfolded.sumOf { rowPermutations(it, cache).toLong() }
    }
}

fun main() {
    val (part1Answer, part1Duration) = measureTimedValue { Day12.part1() }
    println("$part1Answer in ${part1Duration.inWholeMilliseconds} ms")

//    val (part2Answer, part2Duration) = measureTimedValue { Day12.part2() }
//    println("$part2Answer in ${part2Duration.inWholeMilliseconds} ms")
}