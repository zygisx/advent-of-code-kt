package y2022


object Day10 : Day {
    override fun day() = 10

    private fun getInput() = getInputAsList()

    sealed interface Instruction
    object NoOp : Instruction
    data class Add(val amount: Int) : Instruction

    private fun parseInstructions(line: String): Instruction {
        return when {
            line == "noop" -> NoOp
            else -> line.split(" ").let { Add(it[1].toInt()) }
        }
    }

    class Cpu(instructions: List<Instruction>) {

        private val instructions = instructions.flatMap {
            when (it) {
                NoOp -> listOf(NoOp)
                else -> listOf(NoOp, it)
            }
        }
        private var reg = 1
        private var cycle = 0

        fun getReg(): Int {
            return reg
        }

        fun tick() {
            if (cycle >= instructions.size) {
                println("Program done")
            }
            when (val currentInstruction = instructions[cycle]) {
                is Add -> {
                    reg += currentInstruction.amount
                }
                is NoOp -> {}
            }
            cycle += 1

        }
    }

    fun part1(): Int {
        val instructions = getInput().map { parseInstructions(it) }
        val cpu = Cpu(instructions)

        val signals = listOf(20 to 20, 60 to 40, 100 to 40, 140 to 40, 180 to 40, 220 to 40).map {
            val lastSignal = (0 until it.second).map {
                cpu.getReg().also { cpu.tick() }
            }.last()
            it.first * lastSignal
        }
        return signals.sum()
    }

    fun part2(): String {
        val instructions = getInput().map { parseInstructions(it) }
        val cpu = Cpu(instructions)

        val crt = generateSequence { 40 }.take(6).joinToString("\n") {
            (0 until it).map {
                val before = cpu.getReg()
                cpu.tick()
                if (before in (it - 1..it + 1)) '#' else '.'
            }.joinToString("")
        }
        return crt
    }
}

fun main() {
    println(Day10.part1())
    println(Day10.part2())
}