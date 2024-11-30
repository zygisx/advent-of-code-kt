package y2023


object Day7 : Day {
    override fun day() = 7

    private val CARDS_DEFAULT = listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2').reversed()
    private val CARDS_WITH_JOKER = listOf('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J').reversed()


    private fun getInput() = getInputAsList()

    private fun parseInput(): List<Pair<List<Char>, Int>> {
        return getInput().map {
            val (hand, bid) = it.split(" ")
            hand.toCharArray().toList() to bid.toInt()
        }
    }

    enum class Strength(val value: Int) {
        FIVE(7), FOUR(6), FH(5), THREE(4), TP(3), OP(2), HC(1)
    }

    private fun handStrength(hand: List<Char>, joker: Char?): Strength {
        val jokers = joker?.let { hand.count { it == joker } } ?: 0
        val nonJokersCounts = hand.filter { it != joker }.groupingBy { it }.eachCount().values.toList()

        return when {
            5 in nonJokersCounts -> Strength.FIVE
            4 in nonJokersCounts -> when {
                jokers > 0 -> Strength.FIVE
                else -> Strength.FOUR
            }
            2 in nonJokersCounts && 3 in nonJokersCounts -> Strength.FH
            3 in nonJokersCounts -> when {
                jokers == 2 -> Strength.FIVE
                jokers == 1 -> Strength.FOUR
                else -> Strength.THREE
            }

            nonJokersCounts.count { it == 2 } == 2 -> when {
                jokers > 0 -> Strength.FH
                else -> Strength.TP
            }
            2 in nonJokersCounts -> when {
                jokers == 3 -> Strength.FIVE
                jokers == 2 -> Strength.FOUR
                jokers == 1 -> Strength.THREE
                else -> Strength.OP
            }
            jokers == 5 -> Strength.FIVE
            jokers == 4 -> Strength.FIVE
            jokers == 3 -> Strength.FOUR
            jokers == 2 -> Strength.THREE
            jokers == 1 -> Strength.OP
            else -> Strength.HC
        }
    }

    data class Hand(val hand: List<Char>, val strength: Strength, val bid: Int)

    class HandComparator(private val cardsIndex: List<Char>) : Comparator<Hand> {
        override fun compare(thiz: Hand, other: Hand): Int {
            if (thiz.strength.value > other.strength.value) return 1
            if (thiz.strength.value < other.strength.value) return -1
            val cardsToCompare = thiz.hand.zip(other.hand).first { it.first != it.second }
            val thisCardStrength = cardsIndex.indexOf(cardsToCompare.first)
            val otherCardStrength = cardsIndex.indexOf(cardsToCompare.second)
            if (thisCardStrength > otherCardStrength) return 1
            if (thisCardStrength < otherCardStrength) return -1
            error("unreachable")
        }
    }

    private fun calcWinning(hands: List<Hand>): Long {
        return hands.mapIndexed { index, hand ->
            hand.bid * (index + 1L)
        }.sum()
    }

    fun part1(): Long {
        val sortedHands = parseInput().map { (hand, bid) ->
            Hand(hand, handStrength(hand, null), bid)
        }.sortedWith(HandComparator(CARDS_DEFAULT))

        return calcWinning(sortedHands)
    }

    fun part2(): Long {
        val sortedHands = parseInput().map { (hand, bid) ->
            Hand(hand, handStrength(hand, 'J'), bid)
        }.sortedWith(HandComparator(CARDS_WITH_JOKER))

        return calcWinning(sortedHands)
    }
}

fun main() {
    println(Day7.part1())
    println(Day7.part2())
}

// 247899149