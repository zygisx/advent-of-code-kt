package y2023

import misc.Debug
import misc.Point
import java.lang.Integer.min
import kotlin.math.abs
import kotlin.math.max


object Day11 : Day {
    override fun day() = 11

    private fun getInput() = getInputAsList()
        .mapIndexed { y, line ->
            line.mapIndexed { x, c ->
                Point(x, y) to c
            }
        }.flatten().toMap()

    private fun emptyRows(map: Map<Point, Char>, dimensions: Pair<Int, Int>): List<Int> {
        return (0..dimensions.second).filter { y ->
            (0..dimensions.first).all { x-> map[Point(x, y)]!! == '.' }
        }
    }

    private fun emptyColumns(map: Map<Point, Char>, dimensions: Pair<Int, Int>): List<Int> {
        return (0..dimensions.first).filter { x ->
            (0..dimensions.second).all { y-> map[Point(x, y)]!! == '.' }
        }
    }

    private fun insertEmptyRows(map: Map<Point, Char>, dimensions: Pair<Int, Int>): Pair<Map<Point, Char>, Pair<Int, Int>> {
        var offsetY = 0
        val newMap = mutableMapOf<Point, Char>()
        (0..dimensions.second).forEach { y ->
            // copy old one
            (0..dimensions.first).forEach { x -> newMap.put(Point(x, y + offsetY), map[Point(x, y)]!!) }
            // check if this is empty
            val isEmptyRow = (0..dimensions.first).all { x-> map[Point(x, y)]!! == '.' }
            if (isEmptyRow) {
                offsetY += 1
                (0..dimensions.first).forEach { x -> newMap.put(Point(x, y + offsetY), '.') }
            }
        }
        return newMap to (dimensions.first to dimensions.second + offsetY)
    }

    private fun insertEmptyColumns(map: Map<Point, Char>, dimensions: Pair<Int, Int>): Pair<Map<Point, Char>, Pair<Int, Int>> {
        var offsetX = 0
        val newMap = mutableMapOf<Point, Char>()
        (0..dimensions.first).forEach { x ->
            // copy old one
            (0..dimensions.second).forEach { y -> newMap.put(Point(x + offsetX, y), map[Point(x, y)]!!) }
            // check if this is empty
            val isEmptyColumn = (0..dimensions.second).all { y-> map[Point(x, y)]!! == '.' }
            if (isEmptyColumn) {
                offsetX += 1
                (0..dimensions.second).forEach { y -> newMap.put(Point(x + offsetX, y), '.') }
            }
        }
        return newMap to (dimensions.first + offsetX to dimensions.second)
    }

    fun part1(): Int {
        val map = getInput()
        val xMax = map.keys.maxOf { it.x }
        val yMax = map.keys.maxOf { it.y }
        val dimensions = xMax to yMax

        val (withEmptyRows, newDimensions) = insertEmptyRows(map, dimensions)
        val (newMap, _) = insertEmptyColumns(withEmptyRows, newDimensions)

        println(Debug.visualizeMap(newMap) { it.toString() })

        val nonEmptyPoints = newMap.filter { it.value != '.' }.keys

        val visited = mutableSetOf<Point>()

        return nonEmptyPoints.sumOf { thisPoint ->
            visited.add(thisPoint)
            (nonEmptyPoints - visited).sumOf { thisPoint.manhattanDistance(it) }
        }
    }

    fun part2(expansionRate: Long): Long {
        val map = getInput()
        val xMax = map.keys.maxOf { it.x }
        val yMax = map.keys.maxOf { it.y }
        val dimensions = xMax to yMax
        val emptyRows = emptyRows(map, dimensions)
        val emptyColumns = emptyColumns(map, dimensions)

        val nonEmptyPoints = map.filter { it.value != '.' }.keys
        val visited = mutableSetOf<Point>()

        fun manhatan(a: Point, b: Point): Long {
            //abs(x - anotherPoint.x) + abs(y - anotherPoint.y)
            val emptyColumnsAlong = (min(a.x, b.x)..max(a.x, b.x)).count { it in emptyColumns }
            val xDist = abs(a.x - b.x) + (emptyColumnsAlong * expansionRate) - emptyColumnsAlong

            val emptyRowsAlong = (min(a.y, b.y)..max(a.y, b.y)).count { it in emptyRows }
            val yDist = abs(a.y - b.y) + (emptyRowsAlong * expansionRate) - emptyRowsAlong
            return xDist + yDist
        }

        return nonEmptyPoints.sumOf { thisPoint ->
            visited.add(thisPoint)
            val toCheck = nonEmptyPoints - visited
            toCheck.sumOf { manhatan(thisPoint, it) }
        }
    }
}

fun main() {
    println(Day11.part1())
    println(Day11.part2(1_000_000))
}