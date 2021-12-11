package y2021

import misc.Point


object Day9 : Day {
    override fun day() = 9

    private fun getInput() = getInputAsList().map {
        it.split("").filter { it.isNotBlank() }.map { it.toInt() }
    }

    private fun getHeightMap(input: List<List<Int>>): Map<Point, Int> {
        val heightMap = mutableMapOf<Point, Int>()
        input.forEachIndexed { y, line ->
            line.forEachIndexed { x, depth ->
                heightMap[Point(x, y)] = depth
            }
        }
        return heightMap
    }

    private fun getLowPoints(heightMap: Map<Point, Int>): List<Point> {
        return heightMap.filter { (point, height) ->
            point.neighbours().filter { it in heightMap }.all { heightMap[it]!! > height  }
        }.map { it.key }
    }

    fun part1(): Int {
        val input = getInput()
        val heightMap = getHeightMap(input)

        val riskLevels = getLowPoints(heightMap).map { 1 + heightMap[it]!! }
        return riskLevels.sum()
    }

    fun part2(): Int {
        val input = getInput()
        val heightMap = getHeightMap(input)

        val lowPoints = getLowPoints(heightMap)

        val barrier = 9

        val basins = mutableSetOf<Set<Point>>()
        lowPoints.forEach { lowPoint ->
            val basin = mutableSetOf(lowPoint)
            var newPoints = setOf(lowPoint)
            while (newPoints.isNotEmpty()) {
                val goodNeighbours = newPoints
                    .flatMap { it.neighbours() }
                    .filter { it !in basin }
                    .filter { it in heightMap }
                    .filter { heightMap[it]!! < barrier }
                    .toSet()
                newPoints = goodNeighbours
                basin.addAll(goodNeighbours)
            }
            basins.add(basin)
        }

        val largestBasins = basins.map { it.size }.sortedDescending().take(3)
        return largestBasins.fold(1) { acc, it -> acc * it }
    }
}

fun main() {
    println(Day9.part1())
    println(Day9.part2())
}