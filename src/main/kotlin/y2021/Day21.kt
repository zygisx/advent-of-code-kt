package y2021

import kotlin.math.max


object Day21 : Day {
    override fun day() = 21

    private fun getInput() = getInputAsString()

    private fun parseInput(input: String): Pair<Int, Int> {
        val lines = input.lines()
        val player1 = lines[0].split(": ").last().toInt()
        val player2 = lines[1].split(": ").last().toInt()
        return player1 to player2
    }

    class DeterministicDice() {
        var score = 0
        var rolls = 0L
        fun roll(): Int {
            score = if (score >= 100) 1 else score + 1
            rolls += 1
            return score
        }
    }

    data class Player(val id: Int, var space: Int, var score: Long = 0) {
        fun move(moves: Int): Long {
            val newSpace = (space + moves) % 10
            space = if (newSpace == 0) 10 else newSpace
            score += space
            return score
        }
    }

    fun part1(): Long {
        val playersInput = getInput().let { parseInput(it) }

        var player1 = Player(1, playersInput.first)
        var player2 = Player(2, playersInput.second)

        val dice = DeterministicDice()
        while (player1.score < 1000 && player2.score < 1000) {
            val playerRolled = (0..2).fold(0) { acc, _ -> acc + dice.roll() }
            player1.move(playerRolled)
            // swap players
            player1 = player2.also { player2 = player1 }
        }
        val loserScore = minOf(player1.score, player2.score)
        return loserScore * dice.rolls
    }

    private fun merge(outcomes: List<Map<Int, Long>?>): Map<Int, Long> {
        return outcomes.fold(mutableMapOf()) { acc, res ->
            res!!.forEach { acc[it.key] = (acc[it.key] ?: 0L) + it.value }
            acc
        }
    }

    fun part2(): Long {
        val possibleRolls = (1..3).flatMap { a ->
            (1..3).flatMap { b ->
                (1..3).map { c ->
                    a + b + c
                }
            }
        }

        data class CacheKey(val player1: Player, val player2: Player)
        val cache = mutableMapOf<CacheKey, Map<Int, Long>>()

        fun winners(player1: Player, player2: Player): Map<Int, Long> {
            if (player1.score >= 21) return mapOf(player1.id to 1)
            if (player2.score >= 21) return mapOf(player2.id to 1)
            val listOfOutcomes = possibleRolls.map {
                val playerCopy = player1.copy()
                playerCopy.move(it)
                val key = CacheKey(playerCopy.copy(), player2)
                if (key in cache) cache[key] else winners(player2, playerCopy).also { cache[key] = it }
            }
            return merge(listOfOutcomes)
        }

        val playersInput = getInput().let { parseInput(it) }
        val player1 = Player(id = 1, playersInput.first)
        val player2 = Player(id = 2, playersInput.second)
        val outcomes = winners(player1, player2)

        return max(outcomes[1]!!, outcomes[2]!!)
    }
}

fun main() {
    println(Day21.part1())
    println(Day21.part2())
}