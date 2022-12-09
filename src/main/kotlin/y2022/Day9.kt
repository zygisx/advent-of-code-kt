package y2022

import misc.Point


object Day9 : Day {
    override fun day() = 9

    private fun getInput() = getInputAsList()

    enum class Direction(val char: Char, val moveFn: Point.() -> Point) {
        RIGHT('R', Point::east),
        LEFT('L', Point::west),
        UP('U', Point::south),
        DOWN('D', Point::north);
        companion object {
            fun parse(str: String): Direction {
                return Direction.values().find { str.first() == it.char }!!
            }
        }
    }

    private fun instructions(lines: List<String>): List<Pair<Direction, Int>> {
        return lines.map {
            val parts = it.split(" ")
            Direction.parse(parts[0]) to parts[1].toInt()
        }
    }

    private fun move(start: Point, direction: Direction): Point {
        return direction.moveFn(start)
    }

    private fun tailPosition(tail: Point, head: Point): Point {
        val closePoints = (head.neighbours() + head.diagonal()).toList()
        if (tail == head || tail in closePoints) {
            return tail
        }
        return closePoints.first { tail.distance(it) == 1 }
    }

    fun part1(): Int {
        val instructions = instructions(getInput())
        var head = Point(0, 0)
        var tail = Point(0, 0)
        val tailVisited = mutableSetOf(tail)

        instructions.forEach { (direction, distance) ->
            repeat(distance) {
                head = move(head, direction)
                tail = tailPosition(tail, head)
                tailVisited.add(tail)
            }
        }
        return tailVisited.count()
    }

    fun part2(): Int {
        val instructions = instructions(getInput())
        var head = Point(0, 0)
        var knobs = (0 until 9).map { Point(0, 0) }
        val tailVisited = mutableSetOf(head)

        instructions.forEach { (direction, distance) ->
            repeat(distance) {
                head = move(head, direction)
                knobs = knobs.fold(listOf()) { acc, point  ->
                    val precedingKnob = acc.lastOrNull() ?: head
                    val newPosition = tailPosition(point, precedingKnob)
                    acc + newPosition
                }
                tailVisited.add(knobs.last())
            }
        }
        return tailVisited.count()
    }
}

fun main() {
    println(Day9.part1())
    println(Day9.part2())
}