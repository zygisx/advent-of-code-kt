package y2023

import misc.Collections
import misc.Point
import kotlin.time.measureTimedValue


object Day17 : Day {
    override fun day() = 17

    private fun getInput() = getInputAsList().mapIndexed { y, line ->
        line.mapIndexed { x, num ->
            Point(x, y) to num.digitToInt()
        }
    }.flatten().toMap()

    enum class Direction {
        NORTH, SOUTH, WEST, EAST;

        fun move(point: Point, steps: Int = 1): Point {
            return when (this) {
                NORTH -> point.south(steps)
                SOUTH -> point.north(steps)
                WEST -> point.west(steps)
                EAST -> point.east(steps)
            }
        }

        fun changeDirection(): List<Direction> {
            return when (this) {
                NORTH, SOUTH -> listOf(WEST, EAST)
                WEST, EAST -> listOf(NORTH, SOUTH)
            }
        }
    }

    data class Move(val point: Point, val direction: Direction)

    fun dijkstra(map: Map<Point, Int>, minMoves: Int, maxMoves: Int): Int {
        val target = Point(
            x = map.maxOf { it.key.x },
            y = map.maxOf { it.key.y }
        )
        val starts = listOf(Move(Point(0, 0), Direction.NORTH), Move(Point(0, 0), Direction.EAST))
        val shortest = mutableMapOf<Move, Int>()
        starts.forEach { shortest[it] = 0}
        val queue = Collections.queue(starts)

        while (queue.isNotEmpty()) {
            val current = queue.poll()
            val currentDistance = shortest[current]!!

            val nextMoves = current.direction.changeDirection().flatMap { newDirection ->
                var lastDist = currentDistance
                if (minMoves > 1) {
                    // skip moves but increase lastDist
                    (1..<minMoves).forEach {
                        val nextPoint = newDirection.move(current.point, steps = it)
                        lastDist += map[nextPoint] ?: 0
                    }
                }
                (minMoves..maxMoves).map { steps ->
                    val move = Move(newDirection.move(current.point, steps), newDirection)
                    lastDist += map[move.point] ?: 0
                    move to lastDist
                }
            }.filter { it.first.point in map }

            nextMoves.forEach { (move, dist) ->
                if (dist < shortest.getOrDefault(move, Int.MAX_VALUE)) {
                    shortest[move] = dist
                    queue.add(move)
                }
            }
        }

        return shortest.filter { it.key.point == target }.minOf { it.value }
    }

    fun part1(): Int {
        val map = getInput()
        return dijkstra(map, 1, 3)
    }

    fun part2(): Int {
        val map = getInput()
        return dijkstra(map, 4, 10)
    }
}

fun main() {
    val (part1Answer, part1Duration) = measureTimedValue { Day17.part1() }
    println("$part1Answer in ${part1Duration.inWholeMilliseconds} ms")

    val (part2Answer, part2Duration) = measureTimedValue { Day17.part2() }
    println("$part2Answer in ${part2Duration.inWholeMilliseconds} ms")
}

// pt1 1044
// pt2 1227