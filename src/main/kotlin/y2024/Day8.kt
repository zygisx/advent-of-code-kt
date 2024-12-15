package y2024

import misc.Console.printWithTime
import misc.Point
import misc.getAllUniquePairs
import kotlin.math.absoluteValue

object Day8 : Day {

    override fun day() = 8

    private fun getInput() = getInputAsList().mapIndexed { y, line ->
        line.mapIndexed { x, char -> Point(x, y) to char  }
    }.flatten().toMap()

    private fun findAntinodeLocations(a: Point, b: Point): Sequence<Pair<Point, Point>> {
        val xDiff = (b.x - a.x).absoluteValue
        val yDiff = (b.y - a.y).absoluteValue

        val xDirection = if (b.x > a.x) -1 else 1
        val yDirection = if (b.y > a.y) -1 else 1

        var cntr = 1
        return generateSequence {
            Point((a.x + xDiff*xDirection*cntr), (a.y + yDiff*yDirection*cntr)) to Point((b.x + xDiff*xDirection*-1*cntr), (b.y + yDiff*yDirection*-1*cntr))
                .also { cntr++ }
        }
    }

    fun part1(): Int {
        val map = getInput()
        val antennas = map.filter { it.value != '.' }
        val invertedAntennas = antennas.entries.groupBy { it.value }.mapValues { it.value.map { it.key } }

        val xMax = map.maxOf { it.key.x }
        val yMax = map.maxOf { it.key.y }
        val antinodeLocations = invertedAntennas.map {
            val uniquePairs = it.value.getAllUniquePairs()
            val antinodeLocations = uniquePairs.map { (a, b) -> findAntinodeLocations(a, b).first().toList() }.flatten()
            println(antinodeLocations)
            antinodeLocations.filter{ it.x in 0..xMax && it.y in 0..yMax }.toSet()
        }
        return antinodeLocations.flatten().toSet().size


    }

    fun part2(): Int {
        val map = getInput()
        val antennas = map.filter { it.value != '.' }
        val invertedAntennas = antennas.entries.groupBy { it.value }.mapValues { it.value.map { it.key } }
        val xMax = map.maxOf { it.key.x }
        val yMax = map.maxOf { it.key.y }
        val antinodeLocations = invertedAntennas.map {
            val uniquePairs = it.value.getAllUniquePairs()
            val antinodeLocations = uniquePairs.map { (a, b) ->
                findAntinodeLocations(a, b).takeWhile { (a, b) ->
                    a.x in 0..xMax && a.y in 0..yMax ||
                            b.x in 0..xMax && b.y in 0..yMax
                }.flatMap { listOf(it.first, it.second) }.toList()
            }.flatten()
            antinodeLocations.filter{ it.x in 0..xMax && it.y in 0..yMax }.toSet()
        }
        val antinodes = antinodeLocations.flatten().toSet()
        val antennasCount = invertedAntennas.map { it.value.count { it !in antinodes } }.sum()
        return antinodeLocations.flatten().toSet().size + antennasCount
    }
}

fun main() {
    printWithTime { Day8.part1() }
    printWithTime { Day8.part2() }
}
