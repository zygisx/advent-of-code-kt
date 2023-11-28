package y2022

import misc.Point3d


object Day18 : Day {
    override fun day() = 18

    private fun getInput() = getInputAsList()

    fun parseCube(line: String): Point3d {
        val parts = line.split(",").map { it.trim() }
        return Point3d(parts[0].toInt(), parts[1].toInt(), parts[2].toInt())
    }

    private fun Point3d.sides(): List<Point3d> {
        return listOf(
            this.copy(x-1, y, z),
            this.copy(x+1, y, z),
            this.copy(x, y-1, z),
            this.copy(x, y+1, z),
            this.copy(x, y, z-1),
            this.copy(x, y, z+1),
        )
    }

    fun part1(): Int {
        val cubes = getInput().map { parseCube(it) }
        val sidesMap = cubes.associateWith { it.sides() }
        return cubes.sumOf { sidesMap[it]!!.count { it !in cubes } }
    }

    fun part2(): Int {
        throw NotImplementedError()
    }
}

fun main() {
    println(Day18.part1())
    println(Day18.part2())
}