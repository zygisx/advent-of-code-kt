package y2021

import misc.Collections.queue
import misc.Debug
import misc.Point


object Day15 : Day {
    override fun day() = 15

    private fun getInput() = getInputAsList().map { it.split("").filter { it.isNotBlank() } }

    const val INF = Int.MAX_VALUE

    private fun leastExpensivePath(map: Map<Point, Int>, start: Point, finish: Point): Int {
        val shortest = map.mapValues { INF }.toMutableMap()
        shortest[start] = map[start]!!
        val visited = mutableSetOf<Point>()
        val queue = queue(mutableListOf(start))

        while (queue.isNotEmpty()) {
            val current = queue.poll()
            val currentDistance = shortest[current]!!
            val neighbours = current.neighbours().filter { it in map.keys }
            neighbours.forEach {
                if (currentDistance + map[it]!! < shortest[it]!!) {
                    shortest[it] = currentDistance + map[it]!!
                    queue.add(it)
                }
            }
            visited.add(current)
        }

        return shortest[finish]!! - shortest[start]!!

    }

    fun part1(): Int {
        val map = Point.toPointsMap(getInput()).mapValues { it.value.toInt() }
        val start = Point(0, 0)
        val finish = Point(map.keys.map { it.x }.maxOrNull()!!, map.keys.map { it.y }.maxOrNull()!!)

        return leastExpensivePath(map, start, finish)

    }

    fun part2(): Int {
        val initialMap = Point.toPointsMap(getInput()).mapValues { it.value.toInt() }
        val start = Point(0, 0)
        val initialFinish = Point(initialMap.keys.map { it.x }.maxOrNull()!!, initialMap.keys.map { it.y }.maxOrNull()!!)
        val width = initialFinish.x + 1
        val height = initialFinish.y + 1

        fun Int.inc(num: Int) =  if (this + num < 10) this + num else 1 + ((this + num) % 10)
        val fullMap = (0 until 5).flatMap { x ->
            (0 until 5).map { y ->
                val increment = (x + y)
                initialMap.map {
                    Point(x * width + it.key.x, y * height + it.key.y) to it.value.inc(increment)
                }.toMap()
            }
        }.fold(mapOf<Point, Int>()) { acc, map -> acc + map }

        val finish = Point(fullMap.keys.map { it.x }.maxOrNull()!!, fullMap.keys.map { it.y }.maxOrNull()!!)
        return leastExpensivePath(fullMap, start, finish)
    }
}

fun main() {
    println(Day15.part1())
    println(Day15.part2())
}