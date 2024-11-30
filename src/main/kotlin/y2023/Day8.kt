package y2023

import misc.Math
import java.math.BigInteger


object Day8 : Day {
    override fun day() = 8

    data class Route(val source: String, val left: String, val right: String)

    private fun getInput() = getInputAsList()

    val ROUTE_REGEX = """(\w{3}) = \((\w{3}), (\w{3})\)""".toRegex()
    private fun parseRoute(line: String): Route {
        val match = ROUTE_REGEX.matchEntire(line)!!.groups
        return Route(
            source = match[1]!!.value,
            left = match[2]!!.value,
            right = match[3]!!.value
        )
    }

    private fun parseInput(): Pair<String, List<Route>> {
        val lines = getInput()
        val instruction = lines.first()
        val routes = lines.drop(2).map { parseRoute(it) }
        return instruction to routes
    }

    private fun findStepsCount(
        source: String,
        routesMap: Map<String, Route>,
        instructions: Sequence<Char>,
        targetPredicate: (String) -> Boolean
    ): Long {
        var steps = 0L
        var current = source
        val instructionIterator = instructions.iterator()
        while (!targetPredicate(current)) {
            val instruct = instructionIterator.next()
            val route = routesMap[current]!!
            current = if (instruct == 'L') route.left else route.right
            steps += 1
        }
        return steps
    }

    fun part1(): Long {
        val (instruction, routes) = parseInput()
        val routesMap = routes.associateBy { it.source }
        val instructionSeq = sequence { while (true) yieldAll(instruction.asSequence()) }
        return findStepsCount("AAA", routesMap, instructionSeq) { it == "ZZZ" }
    }


    fun part2(): BigInteger {
        val (instruction, routes) = parseInput()
        val routesMap = routes.associateBy { it.source }
        val instructionSeq = sequence { while (true) yieldAll(instruction.asSequence()) }

        val starts = routes.map { it.source }.filter { it.endsWith("A") }
        val counts = starts.map { findStepsCount(it, routesMap, instructionSeq) { it.endsWith("Z")} }
        return counts
            .map { BigInteger.valueOf(it) }
            .reduce { a, b -> Math.lcm(a, b) }
    }
}

fun main() {
    println(Day8.part1())
    println(Day8.part2())
}
