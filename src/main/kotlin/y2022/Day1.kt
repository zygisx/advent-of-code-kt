package y2022


object Day1 : Day {
    override fun day() = 1

    private fun getInput() = getInputAsString()

    private fun parseElfEnergy(): List<Int> {
        return getInput().split("\n\n")
            .map { it.lines().filterNot { it.isBlank() }.map { it.toInt() }.sum() }
    }

    fun part1(): Int {
        val elfEnergy = parseElfEnergy()

        return elfEnergy.max()
    }

    fun part2(): Int {
        val elfEnergy = parseElfEnergy()
        return elfEnergy.sortedDescending().take(3).sum()
    }
}

fun main() {
    println(Day1.part1())
    println(Day1.part2())
}