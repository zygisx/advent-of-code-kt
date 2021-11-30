package y2020


class Day9(private val preamble: Int) : Day {
    override fun day() = 9

    private fun getInput() = getInputAsList().map { it.toLong() }

    private fun isSum(target: Long, list: List<Long>): Boolean {
        return list.withIndex().any { (idx, candidate) ->
            (idx+1 until list.size).any { candidate + list[it] == target }
        }
    }

    private fun firstBreakingRule(input: List<Long>): Long {
        return input
            .drop(preamble)
            .withIndex()
            .firstOrNull { (index, num) ->
                val itemsBefore = input.slice(index until (index + preamble))
                !isSum(num, itemsBefore)
            }!!.value
    }

    fun part1(): Long {
        val input = getInput()
        return firstBreakingRule(input)

    }

    fun part2(): Long {
        val input = getInput()
        val desiredSum = firstBreakingRule(input)

        for (idx in input.indices) {
            var sum = 0L
            val seq = input
                .drop(idx)
                .takeWhile {
                    sum += it
                    sum <= desiredSum
                }
            if (seq.sum() == desiredSum) {
                return seq.minOrNull()!! + seq.maxOrNull()!!
            }
        }
        return 0L
    }
}

fun main() {
    val day9 = Day9(25)

    println(day9.part1())
    println(day9.part2())
}