package y2022

import DayInputReader


interface Day : DayInputReader {

    fun day(): Int

    override fun inputFile(): String {
        return "inputs/y2022/day${day()}.txt"
    }
}