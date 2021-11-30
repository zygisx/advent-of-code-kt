package y2020


object Day23 : Day {
    override fun day() = 23

    private fun getInput() = getInputAsList().let { parse(it) }

    private fun parse(lines: List<String>) = lines.first().map { it.toString().toInt() }

    private fun minus1(cup: Int): Int = if (cup == 1) 9 else cup-1

    private fun doMoveSeq(cups: Sequence<Int>): Sequence<Int> {
        val currentCup = cups.first()
        val threeCups = cups.drop(1).take(3).toList()
        val destinationCup = generateSequence(minus1(currentCup)) { minus1(it) }.dropWhile { it in threeCups }.first()
        val untilDestination = cups.drop(4)
                .takeWhile { it != destinationCup }
                .toList() + destinationCup
        val afterDestination = cups.drop(4 + untilDestination.size)
                .takeWhile { it != currentCup }
                .toList() + currentCup
        return sequence {
            while (true) {
                yieldAll(untilDestination)
                yieldAll(threeCups)
                yieldAll(afterDestination)
            }
        }
    }

    fun doMoveMap(cups: MutableMap<Int, Int>, cup: Int, max: Int): Int {
        val pick1 = cups[cup]!!
        val pick2 = cups[pick1]!!
        val pick3 = cups[pick2]!!
        val afterPicks = cups[pick3]!!
        val threeCupsSet = setOf(pick1, pick2, pick3)
        var destinationCup = cup
        do {
            destinationCup = if (destinationCup > 1) destinationCup - 1 else max
        } while (destinationCup in threeCupsSet)

        // move 3 picks after destination
        val oldDestinationNext = cups[destinationCup]!!
        cups[cup] = afterPicks
        cups[destinationCup] = pick1
        cups[pick1] = pick2
        cups[pick2] = pick3
        cups[pick3] = oldDestinationNext

        return afterPicks
    }

    fun part1(): String {
        val initialCups = getInput()
        var seq = sequence {
            while (true) { yieldAll(initialCups) }
        }

        (0 until 100).forEach {
            seq = doMoveSeq(seq)
        }

        return seq.dropWhile { it != 1 }.drop(1).takeWhile { it != 1 }.toList().joinToString("")
    }

    fun part2(): Long {
        val initialCups = getInput()
        val N = 1_000_000

        val cups = mutableMapOf<Int, Int>() // cup -> next cup
        initialCups.forEachIndexed { idx, cup -> cups[cup] = initialCups.getOrElse(idx+1) { 10 } }
        (10..N).forEach { cups[it] = it+1 }
        cups[N] = initialCups[0]

        var cup = initialCups[0]
        (1..10_000_000).forEach {
            cup = doMoveMap(cups, cup, N)
        }

        val first = cups[1]!!
        val second = cups[first]!!

        return first.toLong() * second.toLong()
    }
}

fun main() {
//    println(Day23.part1())
    println(Day23.part2())
}