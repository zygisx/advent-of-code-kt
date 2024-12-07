package y2024

import misc.Point

object Day6 : Day {
    override fun day() = 6

    private fun getInput() = getInputAsList().mapIndexed { y, line ->
        line.mapIndexed { x, char -> Point(x, y) to char  }
    }.flatten().toMap()

    private fun makeGuardMap(input: Map<Point, Char>): GuardMap {
        val obstacles = input.filter { it.value == '#' }.keys
        val guard = input.filter { it.value == '^' }.keys.first()
        val xMax = input.maxOf { it.key.x }
        val yMax = input.maxOf { it.key.y }
        return GuardMap(Guard(guard, Direction.NORTH), obstacles, xMax, yMax)
    }

    data class GuardMap(var guard: Guard, val obstacles: Set<Point>, val xMax: Int, val yMax: Int) {
        fun inMap(position: Point) = position.x in 0..xMax && position.y in 0..yMax
    }
    data class Guard(val position: Point, val direction: Direction)
    enum class Direction {
        NORTH, SOUTH, WEST, EAST;

        fun right(): Direction {
            return when (this) {
                NORTH -> EAST
                EAST -> SOUTH
                SOUTH -> WEST
                WEST -> NORTH
            }
        }
        fun move(position: Point): Point {
            return when (this) {
                NORTH -> position.south()
                EAST -> position.east()
                SOUTH -> position.north()
                WEST -> position.west()
            }
        }
        fun back(position: Point): Point {
            return when (this) {
                NORTH -> position.north()
                EAST -> position.west()
                SOUTH -> position.south()
                WEST -> position.east()
            }
        }
    }


    fun part1(): Int {
        val map = makeGuardMap(getInput())
        val visited = mutableSetOf<Point>()
        do {
            var nextPosition = map.guard.position
            while (nextPosition !in map.obstacles && map.inMap(nextPosition)) {
                visited.add(nextPosition)
                nextPosition = map.guard.direction.move(nextPosition)
            }
            val guardPosition = map.guard.direction.back(nextPosition)
            map.guard = Guard(guardPosition, map.guard.direction.right())
            println(visited)
            println(map.guard)
        } while (map.guard.position.x in 1..<map.xMax && map.guard.position.y in 1..<map.yMax)

        return visited.size
    }

    private fun hasLoop(obstacles: Set<Point>, map: GuardMap): Boolean {
        val visited = mutableSetOf<Point>()
        val newVisits = mutableSetOf<Point>()
        var guard = map.guard
        var noVisitsStrike = 0
        do {
            var nextPosition = guard.position
            while (nextPosition !in obstacles && map.inMap(nextPosition)) {
                newVisits.add(nextPosition)
                nextPosition = guard.direction.move(nextPosition)
            }
            if (newVisits.all { it in visited } && newVisits.isNotEmpty()) {
                noVisitsStrike++
            } else {
                noVisitsStrike = 0
            }
            if (noVisitsStrike > 3) {
                return true
            }
            val guardPosition = guard.direction.back(nextPosition)
            guard = Guard(guardPosition, guard.direction.right())
            visited.addAll(newVisits)
            newVisits.clear()
        } while (guard.position.x in 1..<map.xMax && guard.position.y in 1..<map.yMax)
        return false
    }

    fun part2(): Int {
        val map = makeGuardMap(getInput())

        val possibleNewObstacles = (0..map.xMax).flatMap { x ->
            (0..map.yMax).map { y -> Point(x, y) }
        }.filter { it !in map.obstacles }

        return possibleNewObstacles.count { point ->
            hasLoop(map.obstacles + point, map).also { if (it) println(point) }
        }
    }
}

fun main() {
    println(Day6.part1())
    println(Day6.part2())
}
