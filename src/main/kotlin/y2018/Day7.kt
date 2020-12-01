package y2018


class Day7 : Day {
    override fun day() = 7

    data class Vertex(val from: Char, val to: Char) {
        companion object {
            private val regex = Regex("""Step (\w) must be finished before step (\w) can begin.""")
            fun from(str: String): Vertex {
                val match = regex.matchEntire(str)
                val groups = match?.groups ?: throw IllegalArgumentException()
                return Vertex(groups[1]!!.value[0], groups[2]!!.value[0])
            }
        }
    }

    private fun vertexes(): List<Vertex> {
        return getInputAsList().map { Vertex.from(it) }
    }

    fun part1(): String {
        val vertexes = vertexes()
        val allNodes = vertexes.flatMap { listOf(it.from, it.to) }.distinct().toSet()

        val dependenciesMap = allNodes.map {
            val dependencies = vertexes.filter { v -> v.to == it }.map { v -> v.from }
            it to dependencies
        }.toMap()

        val picked = mutableListOf<Char>()

        while (picked.size < allNodes.size) {
            val candidates = dependenciesMap
                    .filter { it.key !in picked }
                    .filter { it.value.subtract(picked).isEmpty() }
                    .keys
            val best = candidates.minOrNull()!!

            picked.add(best)
        }

        return picked.joinToString("")
    }

    fun part2() {
        throw NotImplementedError()
    }
}

fun main() {
    val day7 = Day7()

    println(day7.part1())
//    println(day7.part2())
}