package y2024

import DayInputReader


interface Day : DayInputReader {

    fun day(): Int

    override fun inputFile(): String {
        return "inputs/y2024/day${day()}.txt"
    }
}