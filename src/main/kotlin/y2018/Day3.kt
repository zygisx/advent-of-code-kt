package y2018

import kotlin.IllegalArgumentException

class Day3 : Day {
    override fun day() = 3

    private fun getInput() = getInputAsList()

    data class Space(val x: Int, val y: Int, val wide: Int, val height: Int) {
        fun allPoints(): List<Point> =
            (x until x+wide).map { x1 ->
                (y until y+height).map { y1 -> Point(x1, y1) }
            }.flatten()
    }

    data class Claim(val id: Int, val space: Space) {
        companion object {
            private val regex = Regex("""#(\d+) @ (\d+),(\d+): (\d+)x(\d+)""")
            fun parse(str: String): Claim {
                val match = regex.matchEntire(str)
                val groups = match?.groups ?: throw IllegalArgumentException()
                return Claim(
                    id = groups[1]!!.value.toInt(),
                    Space(
                        x = groups[2]!!.value.toInt(),
                        y = groups[3]!!.value.toInt(),
                        wide = groups[4]!!.value.toInt(),
                        height = groups[5]!!.value.toInt()
                    )
                )
            }
        }
    }

    data class Point(val x: Int, val y: Int)

    data class Suit(private val map: MutableMap<Point, MutableList<Int>> = mutableMapOf()) {
        fun add(point: Point, id: Int) {
            val ids = map.getOrDefault(point, mutableListOf())
            ids.add(id)
            map[point] = ids
        }

        fun allIds(): List<List<Int>> {
            return map.values.toList()
        }
    }

    private fun makeSuit(): Suit {
        val claims = getInput().map { Claim.parse(it) }
        val suit = Suit()
        claims.forEach { claim ->
            claim.space.allPoints().forEach { point ->
                suit.add(point, claim.id)
            }
        }
        return suit
    }

    fun part1(): Int {
        val suit = makeSuit()
        return suit.allIds().count { it.size >= 2 }

    }

    fun part2(): Int {
        val suit = makeSuit()

        val whiteList = mutableSetOf<Int>()
        val blackList = mutableSetOf<Int>()
        val allIds = suit.allIds()
        allIds.forEach {
            if (it.size == 1) whiteList.add(it.first())
            if (it.size > 1) it.forEach { badId -> blackList.add(badId) }
        }
        val diff = whiteList.subtract(blackList)
        return diff.first()
    }
}

fun main() {
    val day3 = Day3()

    println(day3.part1())
    println(day3.part2())
}