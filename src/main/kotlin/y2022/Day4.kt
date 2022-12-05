package y2022


object Day4 : Day {
    override fun day() = 4

    private fun getInput() = getInputAsList()
        .map { it.split(",") }
        .map {
            val ranges = it.map {
                val parts = it.split('-')
                IntRange(parts[0].toInt(), parts[1].toInt())
            }
            ranges[0] to ranges[1]
        }

    fun part1(): Int {
        val ranges = getInput()
        return ranges.count {
            it.first.contains(it.second.first) && it.first.contains(it.second.last) ||
                    it.second.contains(it.first.first) && it.second.contains(it.first.last)
        }
    }

    fun part2(): Int {
        val ranges = getInput()
        return ranges.count {
            it.first.intersect(it.second).isNotEmpty()
        }
    }
}

fun main() {
    println(Day4.part1())
    println(Day4.part2())
}