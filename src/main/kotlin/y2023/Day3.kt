package y2023

import misc.Collections.queue
import misc.Point
import java.util.Queue

typealias NumbersInMap = List<Pair<Point, Int>>

object Day3 : Day {
    override fun day() = 3

    private fun getInput(): Map<Point, Char> {
        return getInputAsList().mapIndexed { y, line ->
            line.mapIndexed { x, c ->
                Point(x, y) to c
            }
        }.flatten().toMap()
    }

    private fun findNumbers(map: Map<Point, Char>): List<NumbersInMap> {
        val yMax = map.keys.maxOf { it.y }
        val xMax = map.keys.maxOf { it.x }
        val foundNumbers = mutableListOf<NumbersInMap>()

        fun flushQueue(queue: Queue<Pair<Point, Int>>) {
            queue
                .takeIf { it.isNotEmpty() }
                ?.let { foundNumbers.add(it.toList()); it.clear() }
        }

        (0..yMax).forEach { y ->
            val numbersQueue = queue<Pair<Point, Int>>(mutableListOf())
            (0..xMax).forEach { x ->
                val point = Point(x, y)
                val c = map[point]!!
                when {
                    c.isDigit() -> numbersQueue.add(point to c.digitToInt())
                    else -> flushQueue(numbersQueue)
                }
            }
            flushQueue(numbersQueue)
        }
        return foundNumbers
    }

    private fun pointsWithAdjacentSymbols(map: Map<Point, Char>, points: List<Point>): Boolean {
        return points.any {
            (it.neighbours() + it.diagonal()).any {
                when {
                    it !in map -> false
                    map[it]!!.isDigit() -> false
                    map[it] == '.' -> false
                    else -> true
                }
            }
        }
    }

    fun part1(): Int {
        val map = getInput()
        val foundNumbers = findNumbers(map)
        return foundNumbers
            .filter {
                pointsWithAdjacentSymbols(map, it.map { it.first })
            }
            .map { it.map { it.second }.joinToString("") }
            .map { it.toInt() }
            .sum()
    }

    fun part2(): Long {
        val map = getInput()
        val foundNumbers = findNumbers(map)
        val potentialGears = map.filter { it.value == '*' }.keys

        return potentialGears.mapNotNull {
            val gearNeighbours = (it.neighbours() + it.diagonal()).toSet()
            val gearNeighbourNumbers = foundNumbers.filter {
                val numberCoordinates = it.map { it.first }.toSet()
                (gearNeighbours intersect numberCoordinates).isNotEmpty()
            }
            if (gearNeighbourNumbers.size == 2) {
                gearNeighbourNumbers
                    .map { it.map { it.second }.joinToString("").toLong() }
                    .reduce { a, b -> a*b}
            } else null
        }.sum()
    }
}

fun main() {
    println(Day3.part1())
    println(Day3.part2())
}
