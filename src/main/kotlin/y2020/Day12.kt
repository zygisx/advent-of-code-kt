package y2020

import misc.Point

enum class Cmd(val char: Char) {
    NORTH('N'),
    SOUTH('S'),
    EAST('E'),
    WEST('W'),
    LEFT('L'),
    RIGHT('R'),
    FORWARD('F');

    companion object {
        fun parse(symbol: Char): Cmd {
            return values().find { it.char == symbol } ?: throw IllegalArgumentException("Unknown direction $symbol")
        }
    }
}

data class Action(val cmd: Cmd, val units: Int)

data class Position(val ship: Point, val direction: Cmd, val wayPoint: Point = Point(0, 0))

class Day12 : Day {
    override fun day() = 12

    private fun parseAction(str: String): Action {
        val cmd = Cmd.parse(str[0])
        val units = str.slice(1 until str.length).toInt()
        return Action(cmd, units)
    }

    private fun getInput() = getInputAsList().map { parseAction(it) }

    private fun turn(currentDirection: Cmd, action: Action): Cmd {
        val turns = action.units / 90
        val directions = listOf(Cmd.EAST, Cmd.SOUTH, Cmd.WEST, Cmd.NORTH)
        return generateSequence { if (action.cmd == Cmd.LEFT) directions.reversed() else directions }
                .flatten()
                .dropWhile { it != currentDirection }
                .take(turns + 1)
                .last()
    }

    private fun move(start: Point, action: Action, multiplier: Int = 1): Point {
        return when (action.cmd) {
            Cmd.EAST -> start.east(action.units * multiplier)
            Cmd.WEST -> start.west(action.units * multiplier)
            Cmd.NORTH -> start.north(action.units * multiplier)
            Cmd.SOUTH -> start.south(action.units * multiplier)
            else -> throw IllegalArgumentException("${action.cmd} is not supported")
        }
    }

    private fun rotate(waypoint: Point, action: Action): Point {
        val vector = if (action.cmd == Cmd.RIGHT) Point(1, -1) else Point(-1, 1)
        return (0 until action.units / 90).fold(waypoint) { point, _ ->
            Point(vector.x * point.y, vector.y * point.x)
        }
    }

    fun part1(): Int {
        val actions = getInput()

        val startingPosition = Position(Point(0, 0), Cmd.EAST)
        val finalPosition = actions.foldRight(startingPosition) { action, pos ->
            when (action.cmd) {
                Cmd.SOUTH, Cmd.NORTH, Cmd.WEST, Cmd.EAST -> Position(move(pos.ship, action), pos.direction)
                Cmd.FORWARD -> Position(move(pos.ship, action.copy(cmd = pos.direction)), pos.direction)
                Cmd.LEFT, Cmd.RIGHT -> Position(pos.ship, turn(pos.direction, action))
            }
        }

        return startingPosition.ship.manhattanDistance(finalPosition.ship)
    }

    fun part2(): Int {
        val actions = getInput()

        val startingPosition = Position(Point(0, 0), Cmd.EAST, Point(10, 1))
        val finalPosition = actions.fold(startingPosition) { pos, action ->
            when (action.cmd) {
                Cmd.SOUTH, Cmd.NORTH, Cmd.WEST, Cmd.EAST -> pos.copy(wayPoint = move(pos.wayPoint, action))
                Cmd.FORWARD -> {
                    val shipPos = move(pos.ship, Action(Cmd.EAST, pos.wayPoint.x), action.units)
                        .let { move(it, Action(Cmd.SOUTH, pos.wayPoint.y), action.units) }
                    pos.copy(ship = shipPos)
                }
                Cmd.LEFT, Cmd.RIGHT -> pos.copy(wayPoint = rotate(pos.wayPoint, action))
            }
        }

        return startingPosition.ship.manhattanDistance(finalPosition.ship)
    }
}

fun main() {
    val day12 = Day12()

    println(day12.part1())
    println(day12.part2())
}


