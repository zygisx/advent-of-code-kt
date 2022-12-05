package y2022

import java.util.ArrayDeque

object Day5 : Day {
    override fun day() = 5

    private fun getInput() = getInputAsList()

    data class Instruction(val what: Int, val from: Int, val to: Int) {
        companion object {
            private val instructionRegex = "move (\\d+) from (\\d+) to (\\d+)".toRegex()
            fun parse(line: String): Instruction {
                val match = instructionRegex.matchEntire(line)!!.groups
                return Instruction(
                    what = match[1]!!.value.toInt(),
                    from = match[2]!!.value.toInt(),
                    to = match[3]!!.value.toInt(),
                )
            }
        }
    }

    private fun parseInput(lines: List<String>): Pair<CrateStacks, List<Instruction>> {
        val stackLines = lines.takeWhile { it.isNotBlank() }.reversed()
        val instructionLines = lines.dropWhile { it.isNotBlank() }.drop(1)
        val stacks = parseCrateStacks(stackLines)
        return stacks to instructionLines.map { Instruction.parse(it) }
    }

    private fun parseCrateStacks(lines: List<String>): CrateStacks {
        val numLine = lines.first()
        val stacksNum = numLine.chunked(4).last().trim().toInt()
        val stacks = (0 until stacksNum).map { ArrayDeque<String>() }
        lines.drop(1).map {
            it.chunked(4).mapIndexed { idx, str  ->
                val crate = str.trim().replace("[", "").replace("]", "")
                if (crate.isNotBlank()) stacks[idx].push(crate)
            }
        }
        return stacks
    }

    private fun executeInstructions(stacks: CrateStacks, instructions: List<Instruction>): CrateStacks {
        instructions.forEach {
            val source = stacks[it.from - 1]
            val dest = stacks[it.to - 1]
            repeat(it.what) { dest.push(source.pop()) }
        }
        return stacks
    }

    private fun executeCrateMover9001Instructions(stacks: CrateStacks, instructions: List<Instruction>): CrateStacks {
        instructions.forEach {
            val source = stacks[it.from - 1]
            val dest = stacks[it.to - 1]

            val toTake = (0 until it.what).map { source.pop() }
            toTake.reversed().forEach { dest.push(it) }
        }
        return stacks
    }

    fun part1(): String {
        val (stacks, instructions) = parseInput(getInput())
        val updatedStacks = executeInstructions(stacks, instructions)
        return updatedStacks.mapNotNull { it.peek() }.joinToString("")
    }

    fun part2(): String {
        val (stacks, instructions) = parseInput(getInput())
        val updatedStacks = executeCrateMover9001Instructions(stacks, instructions)
        return updatedStacks.mapNotNull { it.peek() }.joinToString("")
    }
}

typealias CrateStacks = List<ArrayDeque<String>>

fun main() {
    println(Day5.part1())
    println(Day5.part2())
}