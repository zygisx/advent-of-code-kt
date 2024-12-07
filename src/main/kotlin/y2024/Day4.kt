package y2024

import misc.Point

object Day4 : Day {
    override fun day() = 4

    private fun getInput() = getInputAsList().mapIndexed { y, line ->
        line.mapIndexed { x, char -> Point(x, y) to char  }
    }.flatten().toMap()

    private fun testDirection(map: Map<Point, Char>, point: Point, moveFn: (Point) -> Point): Boolean {
        val pointsSeq = generateSequence(point) { moveFn(it) }
        val res = pointsSeq.map { map.getOrDefault(it, '.') }.take(4).joinToString("")
        return res == "XMAS"
    }

    private fun countXmas(map: Map<Point, Char>, point: Point): Int {
        return point.adjacentFn().count { testDirection(map, point, it) }
    }

    fun part1(): Int {
        val map = getInput()
        val allX = map.filter { it.value == 'X' }.map { it.key }
        return allX.sumOf { countXmas(map, it) }
    }

    fun part2(): Int {
        val map = getInput()
        val allA = map.filter { it.value == 'A' }.map { it.key }

        val gooDiagonal = setOf('M', 'S')
        return allA.count { point ->
            val a = setOf(point.northEast(), point.southWest()).map { map.getOrDefault(it, '.') }
            val b = setOf(point.northWest(), point.southEast()).map { map.getOrDefault(it, '.') }
            a.containsAll(gooDiagonal) && b.containsAll(gooDiagonal)
        }
    }
}

fun main() {
    println(Day4.part1())
    println(Day4.part2())
}
