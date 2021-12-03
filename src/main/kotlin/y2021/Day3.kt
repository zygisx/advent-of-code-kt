package y2021

import misc.Counter


object Day3 : Day {
    override fun day() = 3

    private fun getInput() = getInputAsList().map { it.map { if (it == '0') 0 else 1 } }

    private fun createCounter(length: Int): MutableMap<Int, Counter<Int>> {
        val counterByIndex = mutableMapOf<Int, Counter<Int>>()
        (0 until length).forEach { counterByIndex[it] = Counter() }
        return counterByIndex
    }

    private fun List<Int>.bitsToUInt() = this.joinToString("").toUInt(2)

    fun part1(): UInt {
        val bits = getInput()
        val consumptionNumLength = bits[0].size

        val counterByIndex = createCounter(consumptionNumLength)

        bits.forEach { powerConsumption ->
            powerConsumption.forEachIndexed { idx, bit ->
                counterByIndex[idx]!!.inc(bit)
            }
        }

        val (gamma, epsilon) = (0 until consumptionNumLength)
            .map { counterByIndex[it]!!.max()!!.first }
            .let { it.bitsToUInt() to it.map { it.xor(1) }.bitsToUInt() }

        return gamma * epsilon
    }

    fun part2(): UInt {
        val bits = getInput()
        val consumptionNumLength = bits[0].size

        fun findRating(bitCriteria: (Counter<Int>) -> Int): UInt {
            val candidates = bits.toMutableList()
            (0 until consumptionNumLength).takeWhile { y ->
                val counter = candidates
                    .map { it[y] }
                    .fold(Counter<Int>()) { acc, bit -> acc.inc(bit); acc }
                val dominant = bitCriteria(counter)
                candidates.removeIf { it[y] != dominant }
                candidates.size > 1
            }
            return candidates.first().bitsToUInt()
        }

        val oxygenRating = findRating { counter -> if (counter[0] > counter[1]) 0 else 1 }
        val co2Rating = findRating { counter -> if (counter[1] >= counter[0]) 0 else 1 }
        return oxygenRating * co2Rating
    }
}

fun main() {
    println(Day3.part1())
    println(Day3.part2())
}