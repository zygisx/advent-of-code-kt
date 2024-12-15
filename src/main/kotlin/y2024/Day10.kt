package y2024

import misc.Console.printWithTime
import misc.Point
import misc.PointHelper

object Day10 : Day {

    override fun day() = 10

    private fun getInput() = getInputAsList()

    private fun getAllDestinations(map: Map<Point, Int>, start: Point): Set<Point> {
        val xMax = map.maxOf { it.key.x }
        val yMax = map.maxOf { it.key.y }
        val destinations = mutableSetOf<Point>()
        val visited = mutableSetOf<Point>()
        val queue = mutableListOf(start)
        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            visited.add(current)
            val neighbors = current.neighbours().filter { it.x in 0..xMax && it.y in 0..yMax }
            val currentHeight = map[current]!!
            for (neighbor in neighbors) {
                if (neighbor !in visited && map[neighbor]!! == currentHeight + 1) {
                    queue.add(neighbor)
                    if (map[neighbor] == 9) {
                        destinations.add(neighbor)
                    }
                }
            }
        }
        return destinations
    }

    fun part1(): Int {
        val map = PointHelper.mapFromList(getInput()).mapValues { it.value.digitToInt() }
        val trailHeads = map.filter { it.value == 0 }.keys

        return trailHeads.map { getAllDestinations(map, it) }.map { it.size }.sum()
    }

    private fun getAllTrails(map: Map<Point, Int>, start: Point): Int {
        val xMax = map.maxOf { it.key.x }
        val yMax = map.maxOf { it.key.y }
        var numberOfTrails = 0
        val visited = mutableSetOf<Point>()
        val queue = mutableListOf(start)
        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            visited.add(current)
            val neighbors = current.neighbours().filter { it.x in 0..xMax && it.y in 0..yMax }
            val currentHeight = map[current]!!
            for (neighbor in neighbors) {
                if (neighbor !in visited && map[neighbor]!! == currentHeight + 1) {
                    queue.add(neighbor)
                    if (map[neighbor] == 9) {
                        numberOfTrails++
                    }
                }
            }
        }
        return numberOfTrails
    }

    fun part2(): Int {
        val map = PointHelper.mapFromList(getInput()).mapValues { it.value.digitToInt() }
        val trailHeads = map.filter { it.value == 0 }.keys

        return trailHeads.map { getAllTrails(map, it) }.sum()
    }
}

fun main() {
    printWithTime { Day10.part1() }
    printWithTime { Day10.part2() }
}
