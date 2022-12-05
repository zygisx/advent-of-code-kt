package y2022


object Day3 : Day {
    override fun day() = 3

    private fun getInput() = getInputAsList()

    private val priorities = ('a'..'z').mapIndexed { idx, ch -> ch to idx + 1 }.toMap() +
            ('A'..'Z').mapIndexed { idx, ch -> ch to idx + 27 }.toMap()

    fun part1(): Int {
        val rucksacks = getInput()
        val priorities = rucksacks.map {
            val parts = it.chunked(it.length / 2)
            val intersect = parts[0].toSet().intersect(parts[1].toSet())
            intersect.sumOf { priorities[it]!! }
        }
        return priorities.sum()
    }

    fun part2(): Int {
        val rucksacks = getInput()
        val priorities = rucksacks.chunked(3).map {
            val commonItems = it[0].toSet().intersect(it[1].toSet()).intersect(it[2].toSet())
            priorities[commonItems.first()]!!
        }
        return priorities.sum()
    }
}

fun main() {
    println(Day3.part1())
    println(Day3.part2())
}