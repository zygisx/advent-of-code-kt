package y2018

import java.util.Stack

class Day5 : Day {
    override fun day() = 5

    private fun getInput() = getInputAsString()

    private fun doReact(a: Char, b: Char): Boolean {
        return (a != b && a.lowercaseChar() == b.lowercaseChar())
    }

    private fun fullReaction(polymers: String): Int {
        val stack = Stack<Char>()
        stack.push(polymers[0])

        var i = 1
        while (i < polymers.length) {
            val previous = if (stack.isEmpty()) ' ' else stack.peek()!!
            val current = polymers[i]
            if (doReact(previous, current)) {
                stack.pop()
            } else {
                stack.push(current)
            }
            i += 1
        }
        return stack.size
    }

    val fromAtoZ = sequence {
        var c = 'A'
        while (c <= 'Z') {
            yield(c)
            c += 1
        }
    }

    fun part1(): Int {
        val polymers = getInput()
        return fullReaction(polymers)

    }

    fun part2(): Int {
        val polymers = getInput()
        val minimumSequence = fromAtoZ.map {
            val cleanedPolymers = polymers.filter { p -> p != it && p != it.lowercaseChar() }
            val res = fullReaction(cleanedPolymers)
            res
        }.minOrNull()
        return minimumSequence!!
    }
}

fun main() {
    val day5 = Day5()

    println(day5.part1())
    println(day5.part2())
}