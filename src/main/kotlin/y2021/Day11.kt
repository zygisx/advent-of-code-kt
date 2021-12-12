package y2021

import misc.Debug
import misc.Point


object Day11 : Day {
    override fun day() = 11

    private fun getInput() = getInputAsList().map {
        it.split("").filter { it.isNotBlank() }.map { it.toInt() }
    }

    private fun numberOfFlashes(map: MutableMap<Point, Int>): Int {
        val flashed = mutableSetOf<Point>()

        fun flash(point: Point) {
            if (point in flashed) return@flash
            flashed.add(point)
            point.adjacent().filter { it in map }.forEach {
                map[it as Point] = map[it]!!.inc()
            }
            point.adjacent()
                .filter { it in map }
                .filter { it !in flashed }
                .filter { map[it]!! > 9 }
                .forEach { flash(it as Point) }
        }

        map.forEach { (point, energy) -> map[point] = energy + 1 }
        map.filter { it.value > 9 }.forEach { (point, energy) -> flash(point) }
        flashed.forEach { map[it] = 0 }
        return flashed.size
    }

    fun part1(): Int {
        val map = Point.toPointsMap(getInput())
        val mutableMap = map.toMutableMap()
        return (0 until 100).sumOf { numberOfFlashes(mutableMap) }

    }

    fun part2(): Int {
        val map = Point.toPointsMap(getInput())
        val mutableMap = map.toMutableMap()
        return generateSequence(1) { it + 1 }.dropWhile {
            val flashed = numberOfFlashes(mutableMap)
            flashed < 100
        }.first()
    }
}

fun main() {
    println(Day11.part1())
    println(Day11.part2())
}