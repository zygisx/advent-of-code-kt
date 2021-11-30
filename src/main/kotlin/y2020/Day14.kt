package y2020

import kotlin.math.pow


object Day14 : Day {

    data class Program(val mask: String, val ops: List<Op>)
    data class Op(val address: Long, val value: Long)

    private val opRegex = Regex("""mem\[(\d+)\] = (\d+)""")
    private fun parsePrograms(input: String): List<Program> {
        val programsStr = input.split("mask = ")
        return programsStr
                .filter { it.isNotBlank() }
                .map {
                    val lines = it.lines()
                    val ops = lines.drop(1)
                        .filter { l -> l.isNotBlank() }
                        .map { op ->
                            val match = opRegex.matchEntire(op)!!
                            Op(match.groups[1]!!.value.toLong(), match.groups[2]!!.value.toLong())
                        }
                    Program(lines.first(), ops)
                }
    }

    override fun day() = 14

    private fun getInput() = getInputAsString().let { parsePrograms(it) }

    fun part1(): Long {
        val programs = getInput()
        val memory = mutableMapOf<Long, Long>()

        programs.forEach { program ->
            program.ops.forEach { op ->
                val bitValue = op.value.toString(radix = 2).padStart(36, '0')
                val valueAfterMask = program.mask.zip(bitValue) { maskBit, valueBit ->
                    when (maskBit) {
                        'X' -> valueBit
                        else -> maskBit
                    }
                }.joinToString("").toLong(radix = 2)
                memory[op.address] = valueAfterMask
            }
        }

        return memory.values.sum()
    }

    fun allAddressCombos(address: String): List<Long> {
        val xCount = address.count { it == 'X' }
        val totalCombos = 2.0.pow(xCount.toDouble()).toLong()
        return (0 until totalCombos)
                .map {
                    val replacement = it.toString(radix = 2).padStart(xCount, '0')
                    replacement.toCharArray().fold(address) { acc, char ->
                        acc.replaceFirst('X', char)
                    }
                }.map { it.toLong(radix = 2) }
    }

    fun part2(): Long {
        val programs = getInput()
        val memory = mutableMapOf<Long, Long>()

        programs.forEach { program ->
            program.ops.forEach { op ->
                val bitAddress = op.address.toString(radix = 2).padStart(36, '0')
                val addressAfterMask = program.mask.zip(bitAddress) { maskBit, addressBit ->
                    when (maskBit) {
                        'X' -> 'X'
                        '1' -> '1'
                        else -> addressBit
                    }
                }.joinToString("")
                val allVariants = allAddressCombos(addressAfterMask)
                allVariants.forEach { memory[it] = op.value }
            }
        }

        return memory.values.sum()
    }
}

fun main() {
    println(Day14.part1())
    println(Day14.part2())
}