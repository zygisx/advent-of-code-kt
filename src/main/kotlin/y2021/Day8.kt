package y2021


object Day8 : Day {
    override fun day() = 8

    private fun getInput() = getInputAsList().map { toNote(it) }

    private fun toNote(line: String): Note {
        val parts = line.split(" | ")
        val signals = parts[0].split(" ")
        val output = parts[1].split(" ")
        return Note(signals, output)
    }

    data class Note(val signals: List<String>, val output: List<String>)
    private val easyDigitsToLength = mapOf(1 to 2, 7 to 3, 4 to 4, 8 to 7)
    private val allLetters = setOf('a', 'b', 'c', 'd', 'e', 'f', 'g')

    fun part1(): Int {
        val notes = getInput()
        val easyDigitsLength = easyDigitsToLength.values
        return notes.map { it.output }.map { it.count { it.length in easyDigitsLength } }.sum()
    }

    private fun decodeNote(note: Note): Int {
        val signalsByCount = note.signals.map { it.toSet() }.groupBy { it.size }

        val numbers = mutableMapOf<Int, Set<Char>>()
        easyDigitsToLength.forEach { numbers[it.key] = signalsByCount[it.value]!!.first() }

        val c_d_e = signalsByCount[6]!!.flatMap { allLetters - it } // c or d or e
        val c_f = numbers[1]!!.intersect(numbers[4]!!).intersect(numbers[7]!!)
        val c = c_d_e.intersect(c_f).first()
        val f = (c_f - c).first()

        numbers[6] = signalsByCount[6]!!.first { c !in it }
        numbers[2] = signalsByCount[5]!!.first { f !in it }
        val b = (allLetters - numbers[2]!! - f).first()
        val d = (numbers[4]!! - b - c - f).first()
        numbers[5] = signalsByCount[5]!!.first { b in it }
        numbers[3] = signalsByCount[5]!!.first { it != numbers[5] && it != numbers[2] }
        numbers[0] = signalsByCount[6]!!.first { d !in it }
        numbers[9] = signalsByCount[6]!!.first { it != numbers[0] && it != numbers[6] }

        val numbersBySymbols = numbers.entries.associate{ (k,v) -> v to k }
        return note.output
            .map { it.toSet() }
            .map { numbersBySymbols[it]!! }
            .joinToString("").toInt()
    }

    fun part2(): Int {
        val notes = getInput()
        return notes.sumOf { decodeNote(it) }
    }
}

fun main() {
    println(Day8.part1())
    println(Day8.part2())
}