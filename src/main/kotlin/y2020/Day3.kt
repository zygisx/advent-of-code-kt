package y2020

import misc.Point

enum class Square {
    OPEN,
    TREE;

    companion object {
        fun fromSymbol(symbol: Char): Square {
            return when (symbol) {
                '.' -> OPEN
                '#' -> TREE
                else -> throw IllegalArgumentException("illegal symbol in map: $symbol ")
            }
        }
    }
}

typealias PlanMap = LinkedHashMap<Point, Square>

data class Plan(val plan: PlanMap, val width: Int, val height: Int)

class Day3 : Day {
    override fun day() = 3

    private fun getInput() = getInputAsList()

    private fun makePlanMap(lines: List<String>): PlanMap {
        val plan = PlanMap()
        lines.forEachIndexed { i, line ->
            line.forEachIndexed { j, char ->
                val square = Square.fromSymbol(char)
                plan[Point(j, i)] = square
            }
        }
        return plan
    }

    private fun makePlan(lines: List<String>): Plan {
        val height = lines.size
        val width = lines.first().length
        val planMap = makePlanMap(lines)
        return Plan(planMap, width, height)
    }

    private fun traversePlan(plan: Plan, right: Int, down: Int): Long {
        fun movePoint(p: Point) = Point((p.x + right) % plan.width, p.y + down)

        var point = Point(0, 0)
        var treesCounter = 0L
        repeat(plan.height) {
            point = movePoint(point)
            if (plan.plan[point] == Square.TREE) treesCounter++
        }
        return treesCounter
    }


    fun part1(): Long {
        val input = getInput()
        val plan = makePlan(input)

        return traversePlan(plan, 3, 1)
    }

    fun part2(): Long {
        val input = getInput()
        val plan = makePlan(input)

        return traversePlan(plan, 1, 1) *
                traversePlan(plan, 3, 1) *
                traversePlan(plan, 5, 1) *
                traversePlan(plan, 7, 1) *
                traversePlan(plan, 1, 2)
    }
}

fun main() {
    val day3 = Day3()

    println(day3.part1())
    println(day3.part2())
}