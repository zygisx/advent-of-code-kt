package y2021

import misc.Debug
import misc.Point


object Day13 : Day {
    override fun day() = 13

    private fun getInput() = getInputAsList()

    private val foldRegex = Regex("""fold along ([xy])=(\d+)""")
    private fun parseInput(lines: List<String>): Paper {
        val dots = lines.takeWhile { it.isNotBlank() }
            .map {
                val parts = it.split(",")
                Point(parts[0].toInt(), parts[1].toInt())
            }.toSet()
        val folds = lines.dropWhile { !it.startsWith("fold along") }
            .map {
                val match = foldRegex.matchEntire(it)
                val axis = when (match!!.groups[1]!!.value) {
                    "x" -> Axis.X
                    "y" -> Axis.Y
                    else -> throw IllegalArgumentException("Wrong fold along instruction")
                }
                Fold(axis, match.groups[2]!!.value.toInt())
            }
        return Paper(dots, folds)
    }

    data class Paper(val dots: Set<Point>, val folds: List<Fold>)
    data class Fold(val axis: Axis, val position: Int)
    enum class Axis{ X, Y }

    private fun foldCoordinate(coordinate: Int, foldLine: Int): Int {
        return if (coordinate > foldLine) 2 * foldLine - coordinate else coordinate
    }

    private fun fold(dots: Set<Point>, fold: Fold): Set<Point> {
        return when (fold.axis) {
            Axis.X -> { dots.map { it.copy(x = foldCoordinate(it.x, fold.position)) } }
            Axis.Y -> { dots.map { it.copy(y = foldCoordinate(it.y, fold.position)) } }
        }.toSet()
    }

    fun part1(): Int {
        val paper = parseInput(getInputAsList())
        val dots = fold(paper.dots, paper.folds.first())
        return dots.size
    }

    fun part2(): String {
        val paper = parseInput(getInputAsList())

        val dots = paper.folds.fold(paper.dots) { acc, fold -> fold(acc, fold) }
        return Debug.visualizeMap(dots.associateWith { "#" }) { it ?: "." }
    }
}

fun main() {
    println(Day13.part1())
    println(Day13.part2())
}