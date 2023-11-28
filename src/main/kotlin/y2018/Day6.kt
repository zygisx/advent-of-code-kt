package y2018

import misc.Point
import misc.Points
import java.util.LinkedList
import java.util.Queue

typealias SuitablePointPredicate = (point: Point, visiting: Point, all: Points) -> Boolean

class Day6(private val regionSize: Int = 10000) : Day {
    override fun day() = 6

    private fun getInput() = getInputAsList()

    private fun parsePoint(str: String): Point {
        val (x, y) = str.split(",").map { it.trim() }.map { it.toInt() }
        return Point(x, y)
    }

    private fun points() = getInput().map { parsePoint(it) }

    fun isInfinite(points: Points, point: Point): Boolean {
        return points.all { it.x < point.x }
                || points.all { it.x > point.x }
                || points.all { it.y < point.y }
                || points.all { it.y > point.y }
    }

    fun closest(points: Points, point: Point): List<Point> {
        val distances = points.map { Pair(it, it.manhattanDistance(point)) }
        val min = distances.minByOrNull { it.second }!!
        return distances.filter { it.second == min.second }.map { it.first }
    }

    private fun area(point: Point, all: Points, suitablePointPredicate: SuitablePointPredicate): Int {
        val toVisit: Queue<Point> = LinkedList()
        val discovered = mutableSetOf(point)
        var totalArea = 0
        toVisit.offer(point)
        while (toVisit.isNotEmpty()) {
            val visiting = toVisit.poll()!!
            if (isInfinite(all, visiting)) {
                return -1
            }
            if (suitablePointPredicate(point, visiting, all)) {
                totalArea += 1
                visiting.neighbours()
                    .filter { it !in discovered }
                    .forEach {
                        discovered.add(it)
                        toVisit.offer(it)
                    }
            }
        }

        return totalArea
    }

    private fun isClosestRoot(root: Point, visiting: Point, all: Points): Boolean {
        val closest = closest(all, visiting)
        return closest.size == 1 && closest[0] == root
    }


    fun part1(): Int {
        val points = points()

        val areas = points
                .map { area(it, points, ::isClosestRoot) }
                .filter { it > 0 }
        return areas.maxOrNull()!!

    }

    private fun totalDistanceWithinRegion(root: Point, visiting: Point, all: Points): Boolean {
        val totalDistance = all.map { visiting.manhattanDistance(it) }.sum()
        return totalDistance < regionSize
    }

    fun part2(): Int {
        val points = points()
        val centroid = Point.centroid(points)

        return area(centroid, points, ::totalDistanceWithinRegion)

    }
}

fun main() {
    val day6 = Day6(10000)

    println(day6.part1())
    println(day6.part2())
}