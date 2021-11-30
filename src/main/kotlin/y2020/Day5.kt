package y2020

import kotlin.math.pow

internal enum class Direction {
    LOW,
    UP;

    companion object {
        fun parse(symbol: Char): Direction {
            return when(symbol) {
                'F', 'L' -> LOW
                'B', 'R' -> UP
                else -> throw IllegalArgumentException("Wrong direction '$symbol'")
            }
        }
    }
}

internal data class SeatInstruction(val row: List<Direction>, val column: List<Direction>)

class Day5 : Day {
    override fun day() = 5

    private fun getInput() = getInputAsList()

    private fun parseSeatInstruction(input: String): SeatInstruction {
        val directions = input.map { Direction.parse(it) }
        val rowInstruction = directions.slice(0..6)
        val columnInstruction = directions.slice(7..9)
        return SeatInstruction(rowInstruction, columnInstruction)
    }

    private fun parseAllSeats(input: List<String>) = input.map { parseSeatInstruction(it) }

    private fun binarySearch(instruction: List<Direction>): Int {
        var lastSeat = (2.0.pow(instruction.size) - 1).toInt()

        return instruction.fold(0..lastSeat) { range, direction ->
            val mid = range.first + (range.last - range.first) / 2
            when (direction) {
                Direction.UP -> (mid + 1)..range.last
                Direction.LOW -> range.first..mid
            }
        }.first
    }

    private fun calculateSeatId(seatInstruction: SeatInstruction): Int {
        val row = binarySearch(seatInstruction.row)
        val column = binarySearch(seatInstruction.column)
        return row * 8 + column
    }

    fun part1(): Int {
        return parseAllSeats(getInput())
            .map { calculateSeatId(it) }
            .maxOrNull()!!
    }

    fun part2(): Int {
        val ids = parseAllSeats(getInput())
                .map { calculateSeatId(it) }
                .toSet()
        return ids.fold(listOf<Int>()) { missing, id ->
            if ((id - 1) !in ids) missing + (id - 1)
            else missing
        }.maxOrNull()!!
    }
}

fun main() {
    val day5 = Day5()

//    println(day5.part1())
    println(day5.part2())
}