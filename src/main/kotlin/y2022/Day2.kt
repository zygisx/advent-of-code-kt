package y2022

import y2022.Day2.Outcome.*

object Day2 : Day {
    override fun day() = 2

    private fun getInput() = getInputAsList()

    sealed interface Play {
        val symbols: Set<String>
        val score: Int
        val winAgainst: Play
        val loseAgainst: Play
    }

    object Rock : Play {
        override val symbols = setOf("A", "X")
        override val score = 1
        override val winAgainst = Scissors
        override val loseAgainst = Paper
    }

    object Paper : Play {
        override val symbols = setOf("B", "Y")
        override val score = 2
        override val winAgainst = Rock
        override val loseAgainst = Scissors
    }

    object Scissors : Play {
        override val symbols = setOf("C", "Z")
        override val score = 3
        override val winAgainst = Paper
        override val loseAgainst = Rock
    }

    enum class Outcome(val score: Int) {
        WIN(6), LOSE(0), DRAW(3);
        companion object {
            fun fromPlay(play: Play) = when(play) {
                Rock -> LOSE
                Paper -> DRAW
                Scissors -> WIN
            }
        }
    }


    private fun parse(line: String): Pair<Play, Play> {
        val plays = line.split(" ").map {
            when (it) {
                in Rock.symbols -> Rock
                in Paper.symbols -> Paper
                in Scissors.symbols -> Scissors
                else -> throw RuntimeException("$it unknown")
            }
        }.take(2)
        return Pair(plays[0], plays[1])
    }

    private fun playGame(play: Pair<Play, Play>): Int {
        val outcome = when {
            play.first == play.second -> DRAW
            play.first.winAgainst == play.second -> LOSE
            else -> WIN
        }
        return outcome.score + play.second.score
    }

    private fun choosePlayByOutcome(outcome: Pair<Play, Outcome>): Play {
        return when(outcome.second) {
            DRAW -> outcome.first
            LOSE -> outcome.first.winAgainst
            WIN -> outcome.first.loseAgainst
        }
    }

    fun part1(): Int {
        val plays = getInput().map { parse(it) }
        return plays.sumOf { playGame(it) }
    }

    fun part2(): Int {
        return getInput().map { parse(it) }
            .map { it.first to Outcome.fromPlay(it.second) }
            .map { it.first to choosePlayByOutcome(it) }
            .sumOf { playGame(it) }
    }
}

fun main() {
    println(Day2.part1())
    println(Day2.part2())
}