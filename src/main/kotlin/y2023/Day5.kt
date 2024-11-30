package y2023

import misc.splitToLongs
import misc.splitWithPredicate


object Day5 : Day {
    override fun day() = 5

    data class Range(val destinationRangeStart: Long, val sourceRangeStart: Long, val length: Long) {
        fun getDest(num: Long) = getDest(num, sourceRangeStart, destinationRangeStart)

        fun getDestReversed(num: Long) = getDest(num, destinationRangeStart, sourceRangeStart)

        private fun getDest(num: Long, sourceStart: Long, destinationStart: Long): Long? {
            return if (num in sourceStart..<sourceStart+length) {
                val offset = num - sourceStart
                destinationStart + offset
            } else null
        }
    }

    data class Almanac(val seeds: List<Long>, val mappings: List<List<Range>>)

    private fun getMappings(initialLines: List<String>): List<List<Range>> {
        return initialLines
            .splitWithPredicate { it.isBlank() }
            .map { chunk ->
                chunk.drop(1)
                    .map { it.splitToLongs() }
                    .map {
                        val (destinationRangeStart, sourceRangeStart, length, ) = it
                        Range(destinationRangeStart, sourceRangeStart, length)
                    }
            }.toList()
    }

    private fun getInput(): Almanac {
        val lines = getInputAsList()
        val seeds = lines.first().replace("seeds: ", "").splitToLongs()
        val mappings = getMappings(lines.drop(2))
        return Almanac(seeds, mappings)
    }

    fun part1(): Long {
        val almanac = getInput()
        val locations = almanac.seeds.map { seed ->
            almanac.mappings.fold(seed) { acc, mapping ->
                mapping.firstNotNullOfOrNull { it.getDest(acc) } ?: acc
            }
        }
        return locations.min()
    }

    fun part2(): Long {
        val almanac = getInput()
        val seedsRanges = almanac.seeds.chunked(2).map { it[0]..<(it[0]+it[1]) }
        val mappingsReversed =  almanac.mappings.reversed()
        return (0L..Long.MAX_VALUE).first { minLocation ->
            val seedMin = mappingsReversed.fold(minLocation) { acc, mapping ->
                mapping.firstNotNullOfOrNull { it.getDestReversed(acc) } ?: acc
            }
            val seedMatch = seedsRanges.firstOrNull { seedMin in it }
            seedMatch != null
        }
    }
}

fun main() {
    println(Day5.part1())
    println("----")
    println(Day5.part2())
}