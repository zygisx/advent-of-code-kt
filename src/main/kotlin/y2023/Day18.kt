package y2023

import misc.Collections
import misc.Point
import kotlin.math.abs
import kotlin.time.measureTimedValue


object Day18 : Day {
    override fun day() = 18

    data class Instruction(val direction: Char, val steps: Long, val colorCode: String = "") {
        fun move(point: Point): List<Point> {
            return (0..<steps).runningFold(point) { acc, _ -> moveOne(acc) }.drop(1) // drop initial point
        }

        fun moveToNext(point: Point): Point {
            return moveOne(point, steps.toInt())
        }

        private fun moveOne(point: Point, steps: Int = 1): Point {
            return when(direction) {
                'R' -> point.east(steps)
                'L' -> point.west(steps)
                'U' -> point.south(steps)
                'D' -> point.north(steps)
                else -> error("unreachable")
            }
        }
    }

    private val COLOR_CODE_REGEX = """\(#(\w+)\)""".toRegex()
    private fun getInput() = getInputAsList().map { line ->
        val (direction, steps, colorCode) = line.split(" ")
        Instruction(direction.first(), steps.toLong(), COLOR_CODE_REGEX.find(colorCode)?.groups!![1]!!.value)
    }

    private fun floodInsidePerimeter(perimeterSet: Set<Point>): Set<Point> {
        val minX = perimeterSet.minOf { it.x }
        val maxX = perimeterSet.maxOf { it.x }
        val minY = perimeterSet.minOf { it.y }
        val maxY = perimeterSet.maxOf { it.y }
        val flooded = mutableSetOf<Point>()
        val neighboursToVisit = Collections.stack(listOf(Point(0, 0).northEast()))
        do {
            val current = neighboursToVisit.pop()
            flooded.add(current)
            current.neighbours()
                .filter { it.x in minX..maxX && it.y in minY..maxY }
                .filter { it !in perimeterSet }
                .filter { it !in flooded }
                .filter { it !in neighboursToVisit }
                .forEach { neighboursToVisit.push(it) }
        } while (neighboursToVisit.isNotEmpty())
        return flooded
    }


    fun part1(): Int {

        val plan = getInput()
        val perimeter = mutableListOf(Point(0, 0))
        plan.forEach { instruction ->
            val newPoints = instruction.move(perimeter.last())
            perimeter.addAll(newPoints)
        }

        val perimeterSet = perimeter.toSet()
        val flooded = floodInsidePerimeter(perimeterSet)

        return flooded.size + perimeterSet.size
    }

    private fun colorToInstruction(color: String): Instruction {

        val distanceHex = color.take(5)
        val distance = distanceHex.toLong(16)
        val direction = when (color.last()) {
            '0' -> 'R'
            '1' -> 'D'
            '2' -> 'L'
            '3' -> 'U'
            else -> error("Unreachable")
        }
        return Instruction(direction, distance)
    }

    data class Interval(val range: IntRange, val constant: Int, val nextInside: Boolean)

    private fun debugMap(
        perimeter: List<Point>,
        flooded: Set<Point>
    ): Map<Point, String> {
        val minX = perimeter.minOf { it.x }
        val maxX = perimeter.maxOf { it.x }
        val minY = perimeter.minOf { it.y }
        val maxY = perimeter.maxOf { it.y }
        val debugMap = (minY..maxY).flatMap { y ->
            (minX..maxX).map { x ->
                val p = Point(x, y)
                val c = when {
                    p in flooded -> "$"
                    p in perimeter -> "#"
                    else -> "."
                }
                p to c
            }
        }.toMap()
        return debugMap
    }

    fun polygonArea(perimeterPoints: List<Point>): Long {
        var sum1 = 0L
        var sum2 = 0L
        (perimeterPoints + perimeterPoints.first()).windowed(2).forEach { (first, second) ->
            sum1 += first.x.toLong() * second.y.toLong()
            sum2 += first.y.toLong() * second.x.toLong()
        }
        return (abs(sum1 - sum2) / 2).also { println("AREA: $it") }
    }

    fun part2(): Long {
        val plan = getInput().map { colorToInstruction(it.colorCode) }

        val perimeter = mutableListOf(Point(0, 0))
        val perimeterPoints = mutableListOf(Point(0, 0))

        plan.forEachIndexed { idx, instruction ->
            val newPoint = instruction.moveToNext(perimeterPoints.last())
            perimeterPoints.add(newPoint)
            val newPoints = instruction.move(perimeter.last())
            perimeter.addAll(newPoints)
        }

        return polygonArea(perimeterPoints) + (perimeter.size / 2) + 1
    }
}

fun main() {
    val (part1Answer, part1Duration) = measureTimedValue { Day18.part1() }
    println("$part1Answer in ${part1Duration.inWholeMilliseconds} ms")

    val (part2Answer, part2Duration) = measureTimedValue { Day18.part2() }
    println("$part2Answer in ${part2Duration.inWholeMilliseconds} ms")
}