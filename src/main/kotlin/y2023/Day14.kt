package y2023

import misc.Debug
import misc.Point
import kotlin.time.measureTimedValue


object Day14 : Day {
    override fun day() = 14

    private fun getInput() = getInputAsList().mapIndexed { y, line ->
        line.mapIndexed { x, char -> Point(x, y) to char  }
    }.flatten().toMap()

    private fun tiltNorth(originalPlatform: Map<Point, Char>): Map<Point, Char> {
        val platform = originalPlatform.toMutableMap()

        val maxY = platform.maxOf { it.key.y }
        val maxX = platform.maxOf { it.key.x }

        fun swap(a: Point, b: Point) {
            val tmp = platform[a]!!
            platform[a] = platform[b]!!
            platform[b] = tmp
        }

        do {
            var swapped = false
            (1..maxY).forEach { y ->
                (0..maxX).forEach { x ->
                    val current = Point(x, y)
                    val south = current.south()
                    if (platform[current] == 'O' && platform[south] == '.') {
                        swapped = true
                        swap(current, south)
                    }
                }
            }
        } while (swapped)
        return platform.toMap()
    }

    private fun calculateLoad(platform: Map<Point, Char>): Int {
        val maxY = platform.maxOf { it.key.y }
        val maxX = platform.maxOf { it.key.x }

        return (0..maxY).sumOf { y ->
            (0..maxX).sumOf { x ->
                val char = platform[Point(x, y)]
                if (char == 'O') {
                    maxY - y + 1
                } else 0
            }
        }
    }

    fun part1(): Int {
        val platform = getInput()
        val tilted = tiltNorth(platform)

        println(Debug.visualizeMap(tilted) { it.toString() })
        return calculateLoad(tilted)
    }

    private fun cycle(platform: MutableMap<Point, Char>, maxX: Int, maxY: Int): MutableMap<Point, Char> {
        fun swap(a: Point, b: Point) {
            val tmp = platform[a]!!
            platform[a] = platform[b]!!
            platform[b] = tmp
        }

        fun tilt(moveFn: (Point) -> Point) {
            do {
                var swapped = false
                (0..maxY).forEach { y ->
                    (0..maxX).forEach { x ->
                        val current = Point(x, y)
                        val next = moveFn(current)
                        if (platform[current] == 'O' && platform[next] == '.') {
                            swapped = true
                            swap(current, next)
                        }
                    }
                }
            } while (swapped)
        }

        tilt { it.south() }
        tilt { it.west() }
        tilt { it.north() }
        tilt { it.east() }

        return platform
    }

    fun findLoop(list: List<Int>): List<Int> {
        var i = 1
        while (true) {
            if (list[i] == list[0] && i > 1) { // verify thats the loop
                if ((0..<i).all { list[it] == list[i+it] }) {
                    return list.subList(0, i)
                }
            }
            i+=1
        }
    }

    fun part2(): Int {
        val cycles = 1000000000L
        val dropFirst = 100
        val precalculate = 200

        val platform = getInput()
        val maxY = platform.maxOf { it.key.y }
        val maxX = platform.maxOf { it.key.x }

        val platformSeq = sequence {
            var next = platform.toMutableMap()
            (0..<cycles).forEach {
                next = cycle(next, maxX, maxY)
                yield(next)
            }
        }

        val loads = platformSeq.drop(dropFirst).take(precalculate).map { calculateLoad(it) }
        val loop = findLoop(loads.toList())

        val mod = ((cycles - dropFirst - 1) % loop.size).toInt()
        return loop[mod]
    }
}

fun main() {
    val (part1Answer, part1Duration) = measureTimedValue { Day14.part1() }
    println("$part1Answer in ${part1Duration.inWholeMilliseconds} ms")

    val (part2Answer, part2Duration) = measureTimedValue { Day14.part2() }
    println("$part2Answer in ${part2Duration.inWholeMilliseconds} ms")
}