package y2022

import kotlin.time.ExperimentalTime
import kotlin.time.measureTime


object Day16 : Day {
    override fun day() = 16

    private fun getInput() = getInputAsList()

    data class Valve(val valve: String, val rate: Int, val neighbours: List<String>)

    private val valveRegex = """^Valve (\w+) has flow rate=(\d+); tunnels? leads? to valves? (.+)${'$'}""".toRegex()
    private fun parseValve(line: String): Valve {
        val match = valveRegex.matchEntire(line)
        val groups = match?.groups ?: error("regex not match $line")
        val valve = groups[1]!!.value
        val rate = groups[2]!!.value.toInt()
        val neighbours = groups[3]!!.value.split(",").map { it.trim() }
        return Valve(valve, rate, neighbours)
    }

    fun part1(): Int {
        val valves = getInput().map { parseValve(it) }.associateBy { it.valve }

        fun calcPressureReleased(currentPosition: String, remainingMinutes: Int, totalPressure: Int, openedValves: Set<String>, visited: Set<String>): Int {
            if (remainingMinutes == 0) {
                return totalPressure
            } else {
                val pressureWithOpening = if (currentPosition !in openedValves && valves[currentPosition]!!.rate > 0) {
                    // open current
                    val minutesLeft = remainingMinutes - 1
                    val pressureAggregated = totalPressure + (minutesLeft * valves[currentPosition]!!.rate)
                    calcPressureReleased(currentPosition, minutesLeft, pressureAggregated, openedValves + currentPosition, emptySet())

                } else totalPressure
                // go through tunnel
                val neighbours = valves[currentPosition]!!.neighbours
                val bestNeighbourPressure = neighbours.filterNot { it in visited }.maxOfOrNull {
                    calcPressureReleased(it, remainingMinutes - 1, totalPressure, openedValves, visited + it)
                } ?: return pressureWithOpening
                return maxOf(
                    pressureWithOpening, bestNeighbourPressure, totalPressure
                )
            }
        }

        return calcPressureReleased("AA", 30, 0, emptySet(), setOf("AA"))
    }

    fun part2() {
        throw NotImplementedError()
    }
}

@OptIn(ExperimentalTime::class)
fun main() {
    measureTime {
        println(Day16.part1())
    }.also { println("Took $it") }
    println(Day16.part2())
}