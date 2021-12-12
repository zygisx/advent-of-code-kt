package y2021


object Day12 : Day {
    override fun day() = 12

    private fun getInput(keyFn: (List<String>) -> String, valueFn: (List<String>) -> String) = getInputAsList()
        .map { it.split("-").take(2) }
        .groupBy(keyFn, valueFn).mapValues { it.value.toSet() }

    private fun getInputPaths(): Map<String, Set<String>> {
        val input = getInput({ it[0] }, { it[1] })
        val inverted = getInput({ it[1] }, { it[0] })
        return (input.keys + inverted.keys)
            .associateWith { (input[it] ?: emptySet()) + (inverted[it] ?: emptySet()) }
    }

    private fun String.isBigCave() = this.first().isUpperCase()
    private fun String.isSmallCave() = this.first().isLowerCase() && this != "start" && this != "end"

    private fun findAllPaths(
        paths: Map<String, Set<String>>,
        canVisitCave: (cave: String, currentPath: List<String>) -> Boolean
    ): Set<List<String>> {
        val existingPaths = mutableSetOf<List<String>>()

        fun allPathsRec(current: List<String>): Set<List<String>> {
            val lastVisited = current.last()
            if (lastVisited == "end") return setOf(current)
            val possibleCaves = paths[lastVisited]?.filter { canVisitCave(it, current) } ?: emptySet()
            val newPaths = possibleCaves.map { current + it }.filter { it !in existingPaths }
            existingPaths.addAll(newPaths)

            return newPaths.flatMap { allPathsRec(it) }.toSet()
        }

        return allPathsRec(listOf("start"))
    }

    fun part1(): Int {
        val paths = getInputPaths()
        return findAllPaths(paths) { cave, current -> cave.isBigCave() || cave !in current }.size
    }

    fun part2(): Int {
        val paths = getInputPaths()

        fun canBeVisited(cave: String, currentPath: List<String>): Boolean {
            return when {
                cave.isSmallCave() -> {
                    val smallCounts = currentPath.filter { it.isSmallCave() }.groupingBy { it }.eachCount()
                    val quotaUsed = smallCounts.any { it.value >= 2 }
                    val caveCount = smallCounts[cave] ?: 0
                    if (quotaUsed) caveCount == 0 else caveCount < 2
                }
                cave == "start" -> false
                cave.isBigCave() -> true
                else -> true
            }
        }

        val allPaths = findAllPaths(paths, ::canBeVisited)
        return allPaths.size
    }
}

fun main() {
    println(Day12.part1())
    println(Day12.part2())
}