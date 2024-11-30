package y2023

import misc.Collections
import misc.Debug
import misc.Point

enum class Pipe(val symbol: Char, val alt: Char) {
    VERTICAL('|', '|'),
    HORIZONTAL('-', '-'),
    NORTH_EAST('L', '└'),
    NORTH_WEST('J', '┘'),
    SOUTH_WEST('7', '┐'),
    SOUTH_EAST('F', '┌'),
    GROUND('.', '.'),
    START('S', 'S'),

    OTHER('#', '#'),
    FILL('■', '■'),
    EMPTY(' ', ' '),
    ;

    companion object {
        val bySymbol = values().associateBy { it.symbol }
        val byAlt = values().associateBy { it.alt }
        fun valueOf(char: Char) = bySymbol[char] ?: error("Dont know symbol $char")
        fun valueOfAlt(char: Char) = byAlt[char] ?: error("Dont know symbol $char")

        fun fromRowReplacement(twoPipes: List<Pipe>): List<Pipe> {
            return ROW_REPLACEMENTS_PIPES[twoPipes] ?: listOf(twoPipes[0], GROUND, twoPipes[1])
        }

        fun fromColumnReplacement(twoPipes: List<Pipe>): List<Pipe> {
            return COLUMN_REPLACEMENTS_PIPES[twoPipes] ?: listOf(twoPipes[0], GROUND, twoPipes[1])
        }

        private val ROW_REPLACEMENTS = mapOf(
            "-┐" to "--┐",
            "--" to "---",
            "-┘" to "--┘",
            "-S" to "--S",

            "┌-" to "┌--",
            "┌┐" to "┌-┐",
            "┌┘" to "┌-┘",
            "┌S" to "┌-S",

            "└-" to "└--",
            "└┐" to "└-┐",
            "└┘" to "└-┘",
            "└S" to "└-S",

            "S┐" to "S-┐",
            "S┘" to "S-┘",
            "S-" to "S--",
        )
        private val ROW_REPLACEMENTS_PIPES = ROW_REPLACEMENTS.map {
            it.key.map { Pipe.valueOfAlt(it) } to it.value.map { Pipe.valueOfAlt(it) }
        }.toMap()

        private val COLUMN_REPLACEMENTS = mapOf(
            "|┘" to "||┘",
            "||" to "|||",
            "|└" to "||└",
            "|S" to "||S",

            "┌|" to "┌||",
            "┌┘" to "┌|┘",
            "┌└" to "┌|└",
            "┌S" to "┌|S",

            "┐|" to "┐||",
            "┐┘" to "┐|┘",
            "┐└" to "┐|└",
            "┐S" to "┐|S",

            "S|" to "S||",
            "S┘" to "S|┘",
            "S└" to "S|└",
        )
        private val COLUMN_REPLACEMENTS_PIPES = COLUMN_REPLACEMENTS.map {
            it.key.map { Pipe.valueOfAlt(it) } to it.value.map { Pipe.valueOfAlt(it) }
        }.toMap()
    }
}

typealias Maze = Map<Point, Pipe>

object Day10 : Day {
    override fun day() = 10

    fun Maze.startPoint() = this.filter { it.value == Pipe.START }.keys.first()
    fun Maze.xMax() = this.maxOf { it.key.x }
    fun Maze.yMax() = this.maxOf { it.key.y }

    fun Maze.print() = println(Debug.visualizeMap(this) { it?.alt.toString() })

    private fun getInput() = getInputAsList()
        .mapIndexed { y, line ->
            line.mapIndexed { x, c ->
                Point(x, y) to Pipe.valueOf(c)
            }
        }.flatten().toMap()


    private val NORTH_PIPES = setOf(Pipe.VERTICAL, Pipe.SOUTH_WEST, Pipe.SOUTH_EAST)
    private val EAST_PIPES = setOf(Pipe.HORIZONTAL, Pipe.NORTH_WEST, Pipe.SOUTH_WEST)
    private val SOUTH_PIPES = setOf(Pipe.VERTICAL, Pipe.NORTH_WEST, Pipe.NORTH_EAST)
    private val WEST_PIPES = setOf(Pipe.HORIZONTAL, Pipe.NORTH_EAST, Pipe.SOUTH_EAST)

    private fun checkPipeGood(
        map: Maze,
        candidate: Point,
        possiblePipes: Set<Pipe>,
    ): Point? {
        return if (map[candidate] in possiblePipes) candidate else null
    }

    private fun nextFromStart(maze: Maze, start: Point): Point? {
        val candidates = listOfNotNull(
            checkPipeGood(maze, start.south(), NORTH_PIPES),
            checkPipeGood(maze, start.west(), WEST_PIPES),
            checkPipeGood(maze, start.north(), SOUTH_PIPES),
            checkPipeGood(maze, start.east(), EAST_PIPES),
        )
        return candidates.firstOrNull()
    }


    private fun nextInLoop(maze: Maze, current: Point, previous: Point): Point {
        fun oneOrOther(point1: Point, point2: Point, minus: Point): Point {
            return (setOf(point1, point2) - minus).first()
        }
        return when (maze[current]!!) {
            Pipe.VERTICAL -> oneOrOther(current.north(), current.south(), previous)
            Pipe.HORIZONTAL -> oneOrOther(current.west(), current.east(), previous)
            Pipe.NORTH_EAST -> oneOrOther(current.south(), current.east(), previous)
            Pipe.NORTH_WEST -> oneOrOther(current.south(), current.west(), previous)
            Pipe.SOUTH_WEST -> oneOrOther(current.north(), current.west(), previous)
            Pipe.SOUTH_EAST -> oneOrOther(current.north(), current.east(), previous)
            else -> error("Unreachable")
        }
    }

    private fun getPipesList(map: Map<Point, Pipe>): List<Point> {
        val start = map.startPoint()
        var current = nextFromStart(map, start)!!
        val visited = mutableListOf(start, current)
        do {
            current = nextInLoop(map, current, visited.elementAt(visited.size-2))
            if (current !in visited) {
                visited.add(current)
            }
        } while (current != start)
        return visited
    }

    fun part1(): Int {
        val map = getInput()
        val visited = getPipesList(map)
        return visited.size / 2 + (visited.size % 2)
    }

    private fun makeRowExpandedMap(maze: Maze): Maze {
        val expandedMap = mutableMapOf<Point, Pipe>()
        (0..maze.yMax()).forEach { y ->
            (0..<maze.xMax()).forEach { x ->
                val current = maze[Point(x, y)]!!
                val next = maze[Point(x+1, y)]!!
                val (first, middle, last) = Pipe.fromRowReplacement(listOf(current, next))
                expandedMap[Point(x+x, y)] = first
                expandedMap[Point(x+x+1, y)] = middle
            }
        }
        return expandedMap
    }

    private fun makeColumnExpandedMap(maze: Maze): Maze {
        val expandedMap = mutableMapOf<Point, Pipe>()
        (0..maze.xMax()).forEach { x ->
            (0..<maze.yMax()).forEach { y ->
                val current = maze[Point(x, y)]!!
                val next = maze[Point(x, y+1)]!!
                val (first, middle, last) = Pipe.fromColumnReplacement(listOf(current, next))
                expandedMap[Point(x, y+y)] = first
                expandedMap[Point(x, y+y+1)] = middle
            }
        }
        return expandedMap
    }

    private fun expandMap(
        maze: Maze,
        visited: List<Point>
    ): Map<Point, Pipe> {
        val yMax = maze.yMax()
        val xMax = maze.xMax()

        val preparedMap = maze.mapValues {
            when (it.key) {
                in visited -> it.value
                else -> Pipe.GROUND
            }
        //extend with extra row and column at the end
        } + (0..xMax+1).associate { Point(it, yMax + 1) to Pipe.GROUND } +
                (0..yMax+1).associate { Point(xMax + 1, it) to Pipe.GROUND }

        return preparedMap
            .let { makeRowExpandedMap(it) }
            .let { makeColumnExpandedMap(it) }
    }

    fun part2(): Int {
        val maze = getInput()
        val pipesList = getPipesList(maze)
        val expandedMaze = expandMap(maze, pipesList)

        // flood
        val xMax = maze.xMax()
        val yMax = maze.yMax()
        val walls = getPipesList(maze).toSet() + maze.startPoint()
        val flooded = mutableSetOf<Point>()
        val neighboursToVisit = Collections.stack(listOf(Point(0, 0), Point(0, 150)))
        do {
            val current = neighboursToVisit.pop()
            flooded.add(current)
            current.neighbours()
                .filter { it.x in 0..xMax && it.y in 0..yMax }
                .filter { it !in walls }
                .filter { it !in flooded }
                .filter { it !in neighboursToVisit }
                .forEach { neighboursToVisit.push(it) }
        } while (neighboursToVisit.isNotEmpty())

        fun isCandidate(point: Point): Boolean {
            return point.x in 0..xMax
                    && point.y in 0..yMax
                    && point !in flooded
                    && point !in walls
        }
        val calculated = mutableSetOf<Point>()
        val matches = expandedMaze.keys
            .filter { it !in flooded }
            .filter { it !in walls }
            .filter {
                val quadrats = listOf(
                    setOf(it, it.west(), it.southWest(), it.south()),
                    setOf(it, it.south(), it.southEast(), it.east()),
                    setOf(it, it.east(), it.northEast(), it.north()),
                    setOf(it, it.north(), it.northWest(), it.west()),
                )
                val match = quadrats
                    .firstOrNull { points ->
                        points.all { point -> isCandidate(point) && point !in calculated }
                    }
                    ?.also { points -> calculated.addAll(points) }
                match != null
            }

//        expandedMaze.mapValues {
//            when {
//                it.key in flooded -> Pipe.GROUND
//                it.key in calculated -> Pipe.FILL
//                else -> it.value
//            }
//        }.print()
        return matches.size
    }
}


fun main() {
    println(Day10.part1())
    println(Day10.part2())
}

// 7102
// 363

