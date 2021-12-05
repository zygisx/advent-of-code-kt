package y2021

import misc.Point

object Day5 : Day {
     override fun day() = 5

     private fun getInput() = getInputAsList().map { toLine(it) }

     private fun toLine(lineStr: String): Line {
         val coordinates = lineStr.split(" -> ").map {
             it.split(",").map { it.trim().toInt() }
         }
         return Line(Point(coordinates[0][0]!!, coordinates[0][1]!!), Point(coordinates[1][0]!!, coordinates[1][1]!!))
     }

     data class Line(val from: Point, val to: Point)

    private fun createMap(lines: List<Line>): Map<Point, Int> {
        return lines.fold(mutableMapOf()) { acc, line ->
            Point.pointsInBetween(line.from, line.to).forEach {
                acc[it] = acc[it]?.plus(1) ?: 1
            }
            acc
        }
    }

     fun part1(): Int {
         val lines = getInput().filter { it.from.x == it.to.x || it.from.y == it.to.y  }
         val map = createMap(lines)

         return map.count { (_, value) -> value > 1 }
     }

     fun part2(): Int {
         val lines = getInput()
         val map = createMap(lines)

         return map.count { (_, value) -> value > 1 }
     }
 }

 fun main() {
     println(Day5.part1())
     println(Day5.part2())
 }
