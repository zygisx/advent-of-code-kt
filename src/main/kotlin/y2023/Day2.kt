package y2023


object Day2 : Day {
    override fun day() = 2

    data class Game(val id: Int, val cubesSubsets: List<Cubes>)
    data class Cubes(val red: Int, val green: Int, val blue: Int)

    private val GAME_ID_REGEX = """Game (\d+):""".toRegex()
    private fun parseLine(line: String): Game {
        val gameId = GAME_ID_REGEX.find(line)?.groups!![1]!!.value.toInt()
        val gamesList = line.dropWhile { it != ':' }.drop(1).split(";")
        val cubes = gamesList.map {
            val records = it.split(",").map { it.trim() }
            println(records)
            Cubes(
                red = parseCube(records, "red"),
                green = parseCube(records, "green"),
                blue = parseCube(records, "blue"),
            )
        }
        return Game(gameId, cubes)
    }

    private fun parseCube(records: List<String>, color: String): Int {
        return records.firstOrNull { it.endsWith(color) }?.split(" ")?.first()?.toInt() ?: 0
    }

    private fun getInput(): List<Game> {
        return getInputAsList().map { parseLine(it) }
    }

    fun isCubesSequenceAllowed(cubes: Cubes, allowed: Cubes): Boolean {
        return cubes.red <= allowed.red && cubes.green <= allowed.green && cubes.blue <= allowed.blue
    }

    fun part1(): Int {
        val games = getInput()
        val gameSetup = Cubes(red = 12, green = 13, blue = 14)
        return games.filter {
            it.cubesSubsets.all { isCubesSequenceAllowed(it, gameSetup) }
        }.sumOf { it.id }
    }

    fun part2(): Int {
        val games = getInput()
        return games.map {
            val reds = it.cubesSubsets.map { it.red }.max()
            val greens = it.cubesSubsets.map { it.green }.max()
            val blues = it.cubesSubsets.map { it.blue }.max()
            reds * greens * blues
        }.sum()
    }
}

fun main() {
    println(Day2.part1())
    println(Day2.part2())
}