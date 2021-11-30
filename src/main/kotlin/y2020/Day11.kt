package y2020

import misc.Debug
import misc.Point

enum class SeatState(val char: Char) {
    OCCUPIED('#'),
    FREE('L'),
    FLOOR('.');

    companion object {
        fun parse(symbol: Char): SeatState {
            return values().find { it.char == symbol } ?: throw IllegalArgumentException("Unknown seat state $symbol")
        }
    }
}

class Day11 : Day {
    override fun day() = 11

    private fun getInput() = parse(getInputAsString())

    private fun parse(input: String): Map<Point, SeatState> {
        return input.lines().mapIndexed { y, line ->
            line.mapIndexed { x, symbol ->
                Point(x, y) to SeatState.parse(symbol)
            }
        }.flatten().toMap()
    }

    fun adjacent(point: Point, map: Map<Point, SeatState>): Map<Point, SeatState> {
        val adjacent = point.adjacent().toSet()
        return map.filter { it.key in adjacent }
    }

    fun nonFloorAdjacent(point: Point, map: Map<Point, SeatState>): Map<Point, SeatState> {
        fun firstNotFloor(start: Point, moveFn: (Point) -> Point): Pair<Point, SeatState>? {
            val adjacentSeq = generateSequence(moveFn(start)) { moveFn(it) }
            val adjacentPoint = adjacentSeq.dropWhile { it in map && map[it] == SeatState.FLOOR }.first()
            return if (adjacentPoint !in map) null else adjacentPoint to map[adjacentPoint]!!
        }

        return point.adjacentFn().mapNotNull { firstNotFloor(point, it) }.toMap()
    }

    fun rule1(seat: SeatState, adjacent: Map<Point, SeatState>): Boolean {
        return seat == SeatState.FREE && adjacent.values.all { it != SeatState.OCCUPIED }
    }

    fun rule2(seat: SeatState, threshold: Int, adjacent: Map<Point, SeatState>): Boolean {
        return seat == SeatState.OCCUPIED && adjacent.values.count { it == SeatState.OCCUPIED } >= threshold
    }

    fun mapToString(map: Map<Point, SeatState>): String {
        return Debug.visualizeMap(map) { it.char.toString() }
    }

    fun countOccupiedSeats(
            map: Map<Point, SeatState>,
            threshold: Int,
            adjacentFn: (Point, Map<Point, SeatState>) -> Map<Point, SeatState>
    ): Int {
        data class Iteration(val changes: Int, val map: Map<Point, SeatState>)
        val iterationsSeq = generateSequence(Iteration(1, map)) { prevIteration ->
            var changes = 0
            val oldMap = prevIteration.map
            val newMap = oldMap.map {
                val adjacent = adjacentFn(it.key, oldMap)
                when {
                    rule1(it.value, adjacent) -> {
                        changes++
                        it.key to SeatState.OCCUPIED
                    }
                    rule2(it.value, threshold, adjacent) -> {
                        changes++
                        it.key to SeatState.FREE
                    }
                    else -> {
                        it.key to it.value
                    }
                }
            }.toMap()
            Iteration(changes, newMap)
        }

        val lastIteration = iterationsSeq.dropWhile { it.changes > 0 }.first()
        return lastIteration.map.count { it.value == SeatState.OCCUPIED }
    }

    fun part1(): Int {
        val map = getInput()
        return countOccupiedSeats(map, 4, ::adjacent)
    }

    fun part2(): Int {
        val map = getInput()
        return countOccupiedSeats(map, 5, ::nonFloorAdjacent)
    }
}

fun main() {
    val day11 = Day11()

    println(day11.part1())
    println(day11.part2())
}