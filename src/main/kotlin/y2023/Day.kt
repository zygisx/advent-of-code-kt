package y2023

import DayInputReader


interface Day : DayInputReader {

    fun day(): Int

    override fun inputFile(): String {
        return "inputs/y2023/day${day()}.txt"
    }
}