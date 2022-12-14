package y2022

import misc.Point
import java.lang.Integer.max
import kotlin.math.min


object Day14 : Day {
    override fun day() = 14

    private fun getInput() = getInputAsList()

    enum class Tile {
        ROCK, SAND
    }

    private fun lineToPoints(line: String): List<Point> {
        return line.split(" -> ").map { it.trim() }.windowed(2) { endPoints ->
            val points = endPoints.map { it.split(",").map { it.toInt() }.let { Point(it[0], it[1]) } }
            if (points[0].x == points[1].x) {
                val min = min(points[0].y, points[1].y)
                val max = max(points[0].y, points[1].y)
                (min .. max).map { Point(points[0].x, it) }
            } else {
                val min = min(points[0].x, points[1].x)
                val max = max(points[0].x, points[1].x)
                (min .. max).map { Point(it, points[0].y) }
            }
        }.flatten()
    }

    private fun createMap(input: List<String>): MutableMap<Point, Tile> {
        val map = mutableMapOf<Point, Tile>()
        input.flatMap { lineToPoints(it) }.forEach { map[it] = Tile.ROCK }
        return map
    }

    private fun isBlocked(map: Map<Point, Tile>, point: Point, floorY: Int?): Boolean {
        return point in map || floorY?.let { point.y >= it } ?: false
    }

    private fun nextSand(map: Map<Point, Tile>, start: Point, floorY: Int? = null): Point {
        return generateSequence(start) {
            listOf(it.north(), it.northWest(), it.northEast()).firstOrNull { !isBlocked(map, it, floorY) }
        }.last()
    }

    fun part1(): Int {
        val map = createMap(getInput())
        val lowestY = map.keys.maxOfOrNull { it.y }!!
        val sandPouring = Point(500, 0)

        var sandPoured = 0
        while (true) {
            var sand = sandPouring
            while (sand.y <= lowestY) {
                val nextPoint = listOf(sand.north(), sand.northWest(), sand.northEast())
                    .firstOrNull { !isBlocked(map, it, null) }
                if (nextPoint == null) {
                    break;
                } else {
                    sand = nextPoint
                }
            }
            if (sand.y <= lowestY) {
                map[sand] = Tile.SAND
                sandPoured += 1
            } else {
                break;
            }
        }
        return sandPoured
    }

    fun part2(): Int {
        val map = createMap(getInput())
        val floorY = map.keys.map { it.y }.max() + 2
        val sandPouring = Point(500, 0)

        return generateSequence {
            val sand = nextSand(map, sandPouring, floorY)
            map[sand] = Tile.SAND
            sand
        }.takeWhile { it != sandPouring }.count() + 1
    }
}

fun main() {
    println(Day14.part1())
    println(Day14.part2())
}