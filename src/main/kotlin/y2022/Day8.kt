package y2022

import misc.Point

object Day8 : Day {
    override fun day() = 8

    private fun getInput() = getInputAsList()

    data class Forest(val map: Map<Point, Int>) {
        val height = map.keys.maxOf { it.y }
        val width = map.keys.maxOf { it.x }
    }

    private fun createForest(lines: List<String>): Forest {
        val map = lines.mapIndexed { y, line ->
            line.mapIndexed { x, height ->
                Point(x, y) to height.digitToInt()
            }
        }.flatten().toMap()
        return Forest(map)
    }

    private fun allDirections(point: Point, forest: Forest): List<List<Point>> {
        val isNorth = (point.y-1 downTo 0).map { Point(point.x, it) }
        val isEast = (point.x+1 until forest.width+1).map { Point(it, point.y) }
        val isSouth = (point.y+1 until forest.height+1).map { Point(point.x, it) }
        val isWest = (point.x-1 downTo  0).map { Point(it, point.y) }
        return listOf(isNorth, isEast, isSouth, isWest)
    }

    fun part1(): Int {
        val forest = createForest(getInput())

        fun isVisible(point: Point): Boolean {
            val height = forest.map[point]!!
            return allDirections(point, forest).any { it.all { forest.map[it]!! < height } }
        }

        val innerPoints = (1 until forest.height).flatMap { y ->
            (1 until forest.width).map { x->
                Point(x, y)
            }
        }
        val visibleInnerPoints = innerPoints.count { isVisible(it) }

        val outerCount = forest.height * 4
        return outerCount + visibleInnerPoints
    }

    fun part2(): Long {
        val forest = createForest(getInput())

        fun calcPointScore(point: Point): Long {
            val directions = allDirections(point, forest)
            val scores = directions.map {
                val idx = it.indexOfFirst { forest.map[it]!! >= forest.map[point]!! }
                if (idx < 0) it.size else idx+1
            }
            return scores.fold(1L) { a, b -> a * b }
        }
        return forest.map.keys.maxOfOrNull { calcPointScore(it) }!!
    }
}

fun main() {
    println(Day8.part1())
    println(Day8.part2())
}