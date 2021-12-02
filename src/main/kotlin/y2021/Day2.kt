package y2021


object Day2 : Day {
    override fun day() = 2

    enum class Direction { UP, DOWN, FORWARD }
    data class Instruction(val direction: Direction, val amount: Int)

    data class Position(val horizontal: Int, val depth: Int, val aim: Int = 1) {
        fun add(instruction: Instruction): Position {
            return when (instruction.direction) {
                Direction.UP -> this.copy(depth = this.depth - instruction.amount)
                Direction.DOWN -> this.copy(depth = this.depth + instruction.amount)
                Direction.FORWARD -> this.copy(horizontal = this.horizontal + instruction.amount)
            }
        }

        fun addWithAim(instruction: Instruction): Position {
            return when (instruction.direction) {
                Direction.UP -> this.copy(aim = this.aim - instruction.amount)
                Direction.DOWN -> this.copy(aim = this.aim + instruction.amount)
                Direction.FORWARD -> this.copy(
                    horizontal = this.horizontal + instruction.amount,
                    depth = this.depth + this.aim * instruction.amount)
            }
        }
    }

    private fun getInput() = getInputAsList()

    private fun parseInstruction(line: String): Instruction {
        val parts = line.split(" ")
        return Instruction(Direction.valueOf(parts[0].uppercase()), parts[1].toInt())
    }

    private fun accumulateInstructions(
        instructions: List<Instruction>,
        accFn: Position.(instruction: Instruction) -> Position
    ): Position {
        val initalPosition = Position(0, 0, 0)
        return instructions.fold(initalPosition) { accPosition, instruction ->
            accPosition.accFn(instruction)
        }
    }

    fun part1(): Int {
        val instructions = getInputAsList().map { parseInstruction(it) }
        val endPosition = accumulateInstructions(instructions) { this.add(it) }
        return endPosition.depth * endPosition.horizontal
    }

    fun part2(): Int {
        val instructions = getInputAsList().map { parseInstruction(it) }
        val endPosition = accumulateInstructions(instructions) { this.addWithAim(it) }
        return endPosition.depth * endPosition.horizontal
    }
}

fun main() {
    println(Day2.part1())
    println(Day2.part2())
}