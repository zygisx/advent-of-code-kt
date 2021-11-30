package y2020

import misc.Point


object Day24 : Day {
    override fun day() = 24

    enum class Direction {
        // e, se, sw, w, nw, and ne
        E, W, SE, SW, NE, NW;
    }

    enum class Color {
        WHITE, BLACK;
    }

    private fun getInput() = getInputAsList().map { parseDirections(it) }

    private fun parseDirections(line: String): List<Direction> {
        var idx = 0
        val directions = mutableListOf<Direction>()
        while (idx < line.length) {
            when (line[idx++]) {
                'e' -> directions.add(Direction.E)
                'w' -> directions.add(Direction.W)
                's' -> when (line[idx++]) {
                    'e' -> directions.add(Direction.SE)
                    'w' -> directions.add(Direction.SW)
                    else -> throw IllegalArgumentException("Invalid input")
                }
                'n' -> when (line[idx++]) {
                    'e' -> directions.add(Direction.NE)
                    'w' -> directions.add(Direction.NW)
                    else -> throw IllegalArgumentException("Invalid input")
                }
                else -> throw IllegalArgumentException("Invalid input")
            }
        }
        return directions
    }

    private fun move(start: Point, direction: Direction): Point {
        return when (direction) {
            Direction.E -> start.east(2)
            Direction.W -> start.west(2)
            Direction.SW -> start.southWest()
            Direction.SE -> start.southEast()
            Direction.NW -> start.northWest()
            Direction.NE -> start.northEast()
        }
    }

    private fun flipColor(point: Point, tiles: MutableMap<Point, Color>) {
        val currentColor = getColor(point, tiles)
        val newColor = if (currentColor == Color.WHITE) Color.BLACK else Color.WHITE
        println("Flipping $point to $newColor")
        tiles[point] = newColor
    }

    private fun createMap(directions: List<List<Direction>>): Map<Point, Color> {
        val tiles = mutableMapOf<Point, Color>()

        directions.forEach { instruction ->
            val start = Point(0, 0)

            val endPoint = instruction.fold(start) { point, direction -> move(point, direction) }
            flipColor(endPoint, tiles)
        }
        return tiles
    }

    private fun allAdjacent(point: Point): List<Point> {
        return listOf(point.west(2), point.east(2), point.southEast(), point.southWest(), point.northEast(), point.northWest())
    }

    private fun getColor(point: Point, map: Map<Point, Color>): Color {
        return map.getOrDefault(point, Color.WHITE)
    }

    private fun newColor(point: Point, tiles: Map<Point, Color>): Color {
        val adjacent = allAdjacent(point)
        val blackAdjacent = adjacent.count { getColor(it, tiles) == Color.BLACK }
        return when (getColor(point, tiles)) {
            Color.BLACK -> if (blackAdjacent == 0 || blackAdjacent > 2) Color.WHITE else Color.BLACK
            Color.WHITE -> if (blackAdjacent == 2) Color.BLACK else Color.WHITE
        }
    }

    fun part1(): Int {
        val directions = getInput()
        val tiles = createMap(directions)
        return tiles.values.count { it == Color.BLACK }
    }


    fun part2(): Int {
        val directions = getInput()
        val initialTiles = createMap(directions)

        val resultTiles = (1..100).fold(initialTiles) { tiles, _ ->
            tiles.flatMap { (point, color) ->
                val currentPoint = point to newColor(point, tiles)
                val neighbourPairs = allAdjacent(point).map {
                    it to newColor(it, tiles)
                }
                neighbourPairs + currentPoint
            }.toMap()
        }

        return resultTiles.values.count { it == Color.BLACK }
    }
}

fun main() {
    println(Day24.part1())
    println(Day24.part2())
}