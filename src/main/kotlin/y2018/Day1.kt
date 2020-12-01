package y2018

class Day1 : Day {
    override fun day() = 1

    private fun getFrequencies() = getInputAsList().map { it.toInt() }

    fun part1(): Int {
        val frequencies = getFrequencies()
        val startFrequency = 0
        
        return frequencies.fold(startFrequency) { acc, frequency -> frequency + acc }
    }

    fun part2(): Int {
        val frequencies = getFrequencies()
        val infiniteFrequencies = sequence { while(true) yieldAll(frequencies) }
        val memory = mutableSetOf<Int>()
        val iterator = infiniteFrequencies.iterator()
        var last = 0
        while (last !in memory) {
            memory.add(last)
            val frequency = iterator.next()
            last += frequency
        }
        return last
    }
}

fun main() {
    val day1 = Day1()
    println(day1.part1())
    println(day1.part2())
}