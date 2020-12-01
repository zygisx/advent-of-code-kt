package y2020

import DayInputReader


interface Day : DayInputReader {

    fun day(): Int

    override fun inputFile(): String {
        return "inputs/y2020/day${day()}.txt"
    }
}