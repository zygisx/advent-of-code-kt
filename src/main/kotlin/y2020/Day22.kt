package y2020



object Day22 : Day {
    override fun day() = 22

    data class Game(val player1: ArrayDeque<Int>, val player2: ArrayDeque<Int>)

    private fun getInput() = getInputAsList().let { parseInput(it) }

    private fun parseInput(lines: List<String>): Game {
        val player1 = lines.drop(1)
                .takeWhile { it.isNotBlank() }
                .map { it.toInt() }
        val player2 = lines
                .dropWhile { !it.startsWith("Player 2:") }
                .drop(1)
                .map { it.toInt() }

        return Game(ArrayDeque(player1), ArrayDeque(player2))
    }

    fun gameOfLife(game: Game): Game {
        val card1 = game.player1.removeFirst()
        val card2 = game.player2.removeFirst()
        if (card1 > card2) {
            game.player1.addLast(card1)
            game.player1.addLast(card2)
        } else {
            game.player2.addLast(card2)
            game.player2.addLast(card1)
        }
        return game
    }

    fun part1(): Int {
        val game = getInput()

        while (game.player1.isNotEmpty() && game.player2.isNotEmpty()) {
            gameOfLife(game)
        }
        val winnerDeck = if (game.player1.isEmpty()) game.player2 else game.player1
        return (winnerDeck.size downTo 1).zip(winnerDeck).map { (a, b) -> a * b }.sum()
    }

    fun part2(): Int {
        val startGame = getInput()
        val memory = mutableSetOf<Game>()

        fun recursiveCombat(game: Game): Game {
            while (game.player1.isNotEmpty() && game.player2.isNotEmpty()) {
                if (game in memory) return game.copy(player1 = ArrayDeque(game.player1 + game.player2), player2 = ArrayDeque())
                else memory.add(game)

                val card1 = game.player1.removeFirst()
                val card2 = game.player2.removeFirst()
                val remaining1 = game.player1.size
                val remaining2 = game.player2.size

                if (remaining1 >= card1 && remaining2 >= card2) {
                    val recursiveCombaResult = recursiveCombat(game.copy(
                            player1 = ArrayDeque(game.player1.take(card1)),
                            player2 = ArrayDeque(game.player2.take(card2))
                    ))
                    assert(recursiveCombaResult.player1.isEmpty() || recursiveCombaResult.player2.isEmpty())
                    val isWinnerPlayer1 = recursiveCombaResult.player1.isNotEmpty()
                    if (isWinnerPlayer1) {
                        game.player1.addLast(card1)
                        game.player1.addLast(card2)
                    } else {
                        game.player2.addLast(card2)
                        game.player2.addLast(card1)
                    }
                } else {
                    if (card1 > card2) {
                        game.player1.addLast(card1)
                        game.player1.addLast(card2)
                    } else {
                        game.player2.addLast(card2)
                        game.player2.addLast(card1)
                    }
                }
            }
            return game
        }

        val recCombatResult = recursiveCombat(startGame)
        val winnerDeck = if (recCombatResult.player1.isEmpty()) recCombatResult.player2 else recCombatResult.player1
        return (winnerDeck.size downTo 1).zip(winnerDeck).map { (a, b) -> a * b }.sum()
    }
}

fun main() {
    println(Day22.part1())
    println(Day22.part2())
}