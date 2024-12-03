package y2024

object Day3 : Day {
    override fun day() = 3

    private fun getInput() = getInputAsString()

    private val MUL_REGEX = """mul\((\d{1,3}),(\d{1,3})\)"""

    private val TRI_OP_REGEX = """$MUL_REGEX|do\(\)|don't\(\)"""

    fun part1(): Long {
        val line =  getInput()
        return MUL_REGEX.toRegex().findAll(line).map {
            val (a, b) = it.destructured
            a.toLong() * b.toLong()
        }.sum()
    }

    fun part2(): Long {
        val lines = getInput()
        val regEx = TRI_OP_REGEX.toRegex()
        val matches = regEx.findAll(lines)
        var enabled = true
        var sum = 0L
        for (match in matches) {
            when {
                match.value == "do()" -> enabled = true
                match.value == "don't()" -> enabled = false
                else -> {
                    if (enabled) {
                        val (a, b) = match.destructured
                        sum += a.toLong() * b.toLong()
                    }
                }
            }
        }
        return sum
    }
}

fun main() {
    println(Day3.part1())
    println(Day3.part2())
}
