package y2023

import misc.Counter
import kotlin.math.pow
import kotlin.time.measureTimedValue


object Day4 : Day {
    override fun day() = 4

    data class Card(val luckyNumbers: List<Int>, val yourNumbers: List<Int>)

    private fun parseNumbers(line: String) = line.split(" ").filter { it.isNotBlank() }.map { it.toInt() }
    private fun parseCard(line: String): Card {
        val numbersSplit = line.dropWhile { it != ':' }.drop(1).trim().split("|")
        return Card(parseNumbers(numbersSplit[0]), parseNumbers(numbersSplit[1]))
    }

    private fun getInput() = getInputAsList().map(::parseCard)

    fun part1(): Int {
        val cards = getInput()

        return cards
            .map { it.luckyNumbers.intersect(it.yourNumbers.toSet()).size }
            .filter { it > 0 }
            .sumOf { 2.0.pow(it - 1).toInt() }
    }

    fun part2(): Int {
        val cards = getInput()
        val counter = Counter<Int>()
        cards
            .forEachIndexed { idx, card ->
                counter.inc(idx)
                val wins = card.luckyNumbers.intersect(card.yourNumbers.toSet()).size
                if (wins > 0) {
                    val holdsTickets = counter[idx]
                    (idx+1..idx+wins).forEach { counter.add(it, holdsTickets) }
                }
            }
        return counter.getMap().values.sum()
    }
}

fun main() {
    println(Day4.part1())
    val (value, time) = measureTimedValue {
        Day4.part2()
    }
    println(value)
    println("In $time ")
}