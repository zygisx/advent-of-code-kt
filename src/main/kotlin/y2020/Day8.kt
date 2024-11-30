package y2020

import java.util.*


enum class Op {
    NOP,
    ACC,
    JMP;

    fun isChangeable(): Boolean {
        return this == NOP || this == JMP
    }

    fun change(): Op {
        return when(this) {
            NOP -> JMP
            JMP -> NOP
            ACC -> ACC
        }
    }

    companion object {
        fun parse(str: String): Op {
            return valueOf(str.uppercase(Locale.getDefault()))
        }
    }
}

data class Instruction(val op: Op, val arg: Int) {
    companion object {
        fun parse(strInstruction: String): Instruction {
            val parts = strInstruction.split(" ")
            return Instruction(Op.parse(parts[0]), parts[1].toInt())
        }
    }
}

data class ProgramResult(val result: Int, val infiniteLoop: Boolean)

class Day8 : Day {
    override fun day() = 8

    private fun getInput() = getInputAsList().map { Instruction.parse(it) }

    fun executeProgram(instructions: List<Instruction>): ProgramResult {
        val executed = mutableSetOf<Int>()

        var acc = 0
        var idx = 0

        while (idx < instructions.size) {
            val ins = instructions[idx]
            executed.add(idx)
            when(ins.op) {
                Op.ACC -> {
                    acc += ins.arg
                    idx += 1
                }
                Op.JMP -> {
                    idx += ins.arg
                }
                else -> idx += 1
            }
            if (idx in executed) {
                return ProgramResult(acc, true)
            }
        }

        return ProgramResult(acc, false)
    }

    fun part1(): Int {
        val instructions = getInput()
        val res = executeProgram(instructions)
        return res.result
    }

    fun part2(): Int {
        val instructions = getInput()

        for ((idx, instruction) in instructions.withIndex()) {
            if (!instruction.op.isChangeable()) {
                continue
            }
            val changedInstruction = instruction.copy(op = instruction.op.change())
            val newProgram = instructions.toMutableList()
            newProgram[idx] = changedInstruction
            val res = executeProgram(newProgram)
            if (!res.infiniteLoop) {
                return res.result
            }
        }
        return 0
    }
}

fun main() {
    val day8 = Day8()

    println(day8.part1())
    println(day8.part2())
}