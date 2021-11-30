package y2020

import misc.Counter
import misc.Point


object Day20 : Day {
    override fun day() = 20

    data class Tile(val id: Long, val map: Map<Point, Char>, val edges: List<String>)

    private fun getInput() = getInputAsString().let { parseTiles(it) }

    private fun parseTiles(input: String): List<Tile> {
        return input
            .split("\n\n")
            .filter { it.isNotBlank() }
            .map {
                val lines = it.lines()

                val id = lines[0].replace("Tile ", "").replace(":", "").toLong()
                val map = lines.drop(1).flatMapIndexed { y, line ->
                    line.mapIndexed { x, sym -> Point(x, y) to sym }
                }.toMap()
                Tile(id, map, getALlEdges(map))
            }
    }

    private fun getALlEdges(map: Map<Point, Char>): List<String> {
        val maxX = map.keys.map { it.x }.maxOrNull()!!
        val maxY = map.keys.map { it.y }.maxOrNull()!!
        // up (x, 0)
        val up = (0..maxX).map { map[Point(it, 0)]!! }.joinToString("")
        // down (x, maxY)
        val down = (0..maxX).map { map[Point(it, maxY)]!! }.joinToString("")
        // left (0, y)
        val left = (0..maxY).map { map[Point(0, it)]!! }.joinToString("")
        // right (maxX, y)
        val right = (0..maxY).map { map[Point(maxX, it)]!! }.joinToString("")

        return listOf(up, up.reversed(), down, down.reversed(), left, left.reversed(), right, right.reversed())
    }

    private fun getCornerTiles(tiles: List<Tile>): List<Tile> {
        val edgesCounter = Counter<String>()
        tiles.flatMap { it.edges }.forEach { edgesCounter.inc(it) }

        return tiles.filter {
            println("${it.id}: ${it.edges.count { e -> edgesCounter[e] == 2 }}")
            it.edges.forEach {
                println("${it} ${edgesCounter[it]}")
            }
            val edgeCount = it.edges.count { e -> edgesCounter[e] == 2 }
            edgeCount <= 4
        }
    }

    fun part1(): Long {
        val tiles = getInput()
        val cornerTiles = getCornerTiles(tiles)

        return cornerTiles.map { it.id }.fold(1) { acc, it -> acc * it }
    }

    fun part2(): Int {
        val tiles = getInput()
        val cornerTiles = getCornerTiles(tiles)

        // take one corner
        val c1 = cornerTiles.first()

        // find line
//        c1.edges.filter { it }
        throw NotImplementedError()
    }
}

fun main() {
    println(Day20.part1())
//    println(Day20.part2())
}