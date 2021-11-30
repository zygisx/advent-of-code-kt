package y2020

import misc.IPoint
import misc.Point3d
import misc.Point4d


object Day17 : Day {
    enum class State(val symbol: Char) {
        ACTIVE('#'),
        INACTIVE('.');
        companion object {
            fun parse(symbol: Char): State {
                return values().find { it.symbol == symbol } ?: throw IllegalArgumentException("Unknown direction $symbol")
            }
        }
    }

    override fun day() = 17

    private fun getInput3d() = getInputAsString().let { parseMap(it) { x, y -> Point3d(x, y,0) } }
    private fun getInput4d() = getInputAsString().let { parseMap(it) { x, y -> Point4d(x, y,0, 0) } }

    private fun parseMap(str: String, provider: (x: Int, y: Int) -> IPoint): Map<IPoint, State> {
        return str.lines().mapIndexed { y, line ->
            line.mapIndexed { x, symbol ->
                provider(x, y) to State.parse(symbol)
            }
        }.flatten().toMap()
    }

    private fun allNeighbours(startPoint: IPoint, map: Map<IPoint, State>): Map<IPoint, State> {
        return startPoint.adjacent()
                .map { it to map.getOrDefault(it, State.INACTIVE) }
                .toMap()
    }

    private fun pointState(point: IPoint, currentState: State, map: Map<IPoint, State>): State {
        val neighbours = allNeighbours(point, map)
        val activeNeighbours = neighbours.values.count { it == State.ACTIVE }
        return when (currentState) {
            State.ACTIVE -> if (activeNeighbours in (2..3)) State.ACTIVE else State.INACTIVE
            State.INACTIVE -> if (activeNeighbours == 3) State.ACTIVE else State.INACTIVE
        }
    }

    private fun runIteration(initialMap: Map<IPoint, State>): Map<IPoint, State> {
        val alreadyRecalculated = mutableSetOf<IPoint>()
        return initialMap.flatMap { (point, state) ->
            val thisItem = point to pointState(point, state, initialMap)
            val newDiscovers = allNeighbours(point, initialMap)
                    .filter { it !in initialMap.keys && it !in alreadyRecalculated }
                    .map { (neighbourPoint, neighbourState) -> point to pointState(neighbourPoint, neighbourState, initialMap) }
            val all = newDiscovers + thisItem
            alreadyRecalculated.addAll(all.map { it.first })
            all
        }.toMap()
    }

    fun part1(): Int {
        val map = getInput3d()

        return (0 until 6)
                .fold(map) { acc, _ -> runIteration(acc) }
                .count { it.value == State.ACTIVE }
    }

    fun part2(): Int {
        val map = getInput4d()

        return (0 until 6)
                .fold(map) { acc, _ -> runIteration(acc) }
                .count { it.value == State.ACTIVE }
    }
}

fun main() {
    println(Day17.part1())
    println(Day17.part2())
}