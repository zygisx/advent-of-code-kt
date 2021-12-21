package y2021

import misc.Point


object Day17 : Day {
    override fun day() = 17

    private fun getInput() = getInputAsString().let { parseInput(it) }

    data class Target(val xMin: Int, val xMax: Int, val yMin: Int, val yMax: Int)

    private val inputRegex = Regex("""target area: x=(-?\d+)..(-?\d+), y=(-?\d+)..(-?\d+)""")
    private fun parseInput(input: String): Target {
        val match = inputRegex.matchEntire(input)
        val groups = match?.groups ?: throw IllegalArgumentException()
        return Target(
            groups[1]!!.value.toInt(),
            groups[2]!!.value.toInt(),
            groups[3]!!.value.toInt(),
            groups[4]!!.value.toInt())
    }

    private fun move(coordinates: Point, velocity: Point): Pair<Point, Point> {
        val newCoordinates = coordinates + velocity
        val newVelocity = velocity.copy(
            x = when {
                velocity.x > 0 -> velocity.x - 1
                velocity.x < 0 -> velocity.x + 1
                else -> 0
            },
            y = velocity.y - 1
        )
        return newCoordinates to newVelocity
    }

    private fun inTarget(coordinates: Point, target: Target): Boolean {
        return coordinates.x in target.xMin..target.xMax
                && coordinates.y in target.yMin..target.yMax
    }

    private fun possibleToHitTarget(coordinates: Point, target: Target, velocity: Point): Boolean {
        if (coordinates.y < target.yMin) return false
        if (coordinates.x !in target.xMin..target.xMax && velocity.x == 0) return false
        return true
    }

    private fun highestPoint(initialVelocity: Point, target: Target): Int? {
        var coordinates = Point(0, 0)
        var velocity = initialVelocity
        var maxHeight = 0
        while (possibleToHitTarget(coordinates, target, velocity)) {
            val (newCoordinates, newVelocity) = move(coordinates, velocity)
            coordinates = newCoordinates
            velocity = newVelocity

            maxHeight = maxOf(coordinates.y, maxHeight)
            if (inTarget(coordinates, target)) {
                return maxHeight
            }
        }
        return null
    }

    fun part1(): Int {
        val target = getInput()
        val highestPoints = (0..500).flatMap { x ->
            (0..500).mapNotNull { y ->
                val velocity = Point(x, y)
                highestPoint(velocity, target)
            }
        }

        return highestPoints.maxOrNull()!!
    }

    fun part2(): Int {
        val target = getInput()
        val countInTarget = (-500..500).flatMap { x ->
            (-500..500).mapNotNull { y ->
                highestPoint(Point(x, y), target)
            }
        }.count()
        return countInTarget
    }
}

fun main() {
    println(Day17.part1())
    println(Day17.part2())
}