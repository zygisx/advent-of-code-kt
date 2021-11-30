package y2020


class Day6 : Day {
    override fun day() = 6

    private fun getInput() = getInputAsString()

    private fun parseInput(input: String): List<List<Set<Char>>> {
        val rawAnswers = input.split("\n\n")

        return rawAnswers.map {
            val eachPerson = it.lines()
            eachPerson.map {
                it.toCharArray().toSet()
            }
        }
    }

    fun part1(): Int {
        val answers = parseInput(getInput())
        return answers.map { it.flatten().distinct().count() }.sum()
    }

    fun part2(): Int {
        val answers = parseInput(getInput())
        val everyoneAnsweredSame = answers.map { group ->
            val tail = group.drop(0)
            tail.fold(group.first()) { acc, answ ->
                acc intersect answ
            }
        }

        println(everyoneAnsweredSame)
        return everyoneAnsweredSame.map { it.count() }.sum()
    }
}

fun main() {
    val day6 = Day6()

    println(day6.part1())
    println(day6.part2())
}