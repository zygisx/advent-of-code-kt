package y2022

import misc.Collections.queue
import misc.Point


object Day12 : Day {
    override fun day() = 12

    private fun getInput() = getInputAsList()

    val charHeights = (
            ('a' .. 'z').mapIndexed { index, c -> c to index }
            + listOf('S' to 0, 'E' to 25)
        ).toMap()

    private fun createMap(lines: List<String>): Map<Point, Char> {
        return lines.mapIndexed { y, line ->
            line.mapIndexed { x, char ->
                Point(x, y) to char
            }
        }.flatten().toMap()
    }

    private fun bfs(map: Map<Point, Char>, start: Point, finish: Point): Int {
        val queue = queue(listOf(start to 0))
        val visited = mutableSetOf(start)
        val results = mutableSetOf<Int>()

        while (queue.isNotEmpty()) {
            val (point, distance) = queue.poll()
            val currentHeight = charHeights[map[point]]!!
            visited.add(point)

            if (point == finish) {
                results.add(distance)
                continue
            }

            val neighbours = point.neighbours()
            neighbours
                .filter { it !in visited }
                .filter { it in map }
                .filter {
                    val candidatePoint = map[it]!!
                    charHeights[candidatePoint]!!-1 <= currentHeight
                }
                .forEach {
                    queue.add(it to distance+1)
                    visited.add(it)
                }
        }

        return results.minOrNull() ?: Int.MAX_VALUE
    }

    fun part1(): Int {
        val map = createMap(getInput())

        val start = map.filterValues { it == 'S' }.entries.first().key
        val finish = map.filterValues { it == 'E' }.entries.first().key

        return bfs(map, start, finish)
    }

    fun part2(): Int {
        val map = createMap(getInput())

        val finish = map.filterValues { it == 'E' }.entries.first().key
        val possibleStartingPoints = map.filterValues { charHeights[it] == 0 }.keys.toList()

        val shortestPaths = possibleStartingPoints
            .map { start -> bfs(map, start, finish) }
        return shortestPaths.min()
    }
}

fun main() {
    println(Day12.part1())
    println(Day12.part2())
}