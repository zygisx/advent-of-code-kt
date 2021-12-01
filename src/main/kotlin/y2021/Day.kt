package y2021

import DayInputReader


interface Day : DayInputReader {

    fun day(): Int

    override fun inputFile(): String {
        return "inputs/y2021/day${day()}.txt"
    }
}