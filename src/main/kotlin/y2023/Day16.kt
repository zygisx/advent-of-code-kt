package y2023

import misc.Point
import kotlin.time.measureTimedValue


object Day16 : Day {
    override fun day() = 16

    private fun getInput() = getInputAsList()
        .mapIndexed { y, line ->
            line.mapIndexed { x, c ->
                Point(x, y) to Square.fromSymbol(c)
            }
        }.flatten().toMap()

    enum class Square(val symbol: Char) {
        EMPTY('.'),
        MIRROR_DT('/'),
        MIRROR_TD('\\'),
        SPLITTER_V('|'),
        SPLITTER_H('-'),
        ;


        companion object {
            fun fromSymbol(symbol: Char) = values().first { it.symbol == symbol }
        }
    }

    data class Beam(val point: Point, val direction: Direction) {
        fun move(): Point {
            return direction.move(point)
        }

        fun nextBeams(map: Map<Point, Square>): List<Beam> {
            val newPoint = this.move()
            return when (map[newPoint]) {
                Square.MIRROR_DT -> {
                    listOf(this.copy(point = newPoint, direction = this.direction.withMirrorDT()))
                }
                Square.MIRROR_TD -> {
                    listOf(this.copy(point = newPoint, direction = this.direction.withMirrorTD()))
                }
                Square.SPLITTER_V -> {
                    when (this.direction) {
                        Direction.WEST, Direction.EAST -> {
                            listOf(
                                this.copy(point = newPoint, direction = Direction.SOUTH),
                                Beam(newPoint, Direction.NORTH)
                            )
                        }
                        else -> listOf(this.copy(point = newPoint))
                    }
                }
                Square.SPLITTER_H -> {
                    when (this.direction) {
                        Direction.NORTH, Direction.SOUTH -> {
                            listOf(
                                this.copy(point = newPoint, direction = Direction.WEST),
                                Beam(newPoint, Direction.EAST)
                            )
                        }
                        else -> listOf(this.copy(point = newPoint))
                    }
                }
                Square.EMPTY -> listOf(this.copy(point = newPoint))
                null -> emptyList()
            }
        }
    }

    enum class Direction {
        NORTH, SOUTH, WEST, EAST,
        ;

        fun withMirrorDT(): Direction {
            return when (this) {
                NORTH -> WEST
                SOUTH -> EAST
                WEST -> NORTH
                EAST -> SOUTH
            }
        }

        fun withMirrorTD(): Direction {
            return when (this) {
                NORTH -> EAST
                SOUTH -> WEST
                WEST -> SOUTH
                EAST -> NORTH
            }
        }
        fun move(point: Point): Point {
            return when (this) {
                NORTH -> point.north()
                SOUTH -> point.south()
                WEST -> point.west()
                EAST -> point.east()
            }
        }
    }

    private fun getVisited(map: Map<Point, Square>, startBeam: Beam): Int {
        var beams = listOf(startBeam)
        val visited = mutableSetOf<Beam>()
        while (beams.isNotEmpty()) {
            beams = beams
                .flatMap { it.nextBeams(map) }
                .filter { it.point in map }
                .filter { it !in visited }
                .onEach { visited.add(it) }
        }
        return visited.distinctBy { it.point }.size
    }

    fun part1(): Int {
        val map = getInput()
        val startBeam = Beam(Point(-1, 0), Direction.EAST)
        return getVisited(map, startBeam)
    }

    fun part2(): Int {
        val map = getInput()
        val xMax = map.keys.maxOf { it.x }
        val yMax = map.keys.maxOf { it.y }
        val startBeam = listOf(
            (0..yMax).map { Beam(Point(-1, it), Direction.EAST) },
            (0..xMax).map { Beam(Point(it, -1), Direction.NORTH) },
            (0..yMax).map { Beam(Point(xMax+1, it), Direction.WEST) },
            (0..xMax).map { Beam(Point(it, yMax+1), Direction.SOUTH) },
        ).flatten()

        return startBeam.maxOf { getVisited(map, it) }
    }
}

fun main() {
    val (part1Answer, part1Duration) = measureTimedValue { Day16.part1() }
    println("$part1Answer in ${part1Duration.inWholeMilliseconds} ms")

    val (part2Answer, part2Duration) = measureTimedValue { Day16.part2() }
    println("$part2Answer in ${part2Duration.inWholeMilliseconds} ms")
}
