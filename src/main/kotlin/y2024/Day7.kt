package y2024

import misc.Console.printWithTime
import misc.splitToLongs

object Day7 : Day {
    override fun day() = 7

    data class Expression(val result: Long, val operands: List<Long>)

    private fun getInput() = getInputAsList().map {
        val parts = it.split(":")
        val result = parts.first().toLong()
        val operands = parts[1].splitToLongs()
        Expression(result, operands)
    }

    enum class Op(val exec: (Long, Long) -> Long) {
        ADD({a, b -> a + b}),
        MUL({a, b -> a * b }),
        CONCAT({a, b -> "$a$b".toLong()});
    }

    fun canBeSolved(expression: Expression, operators: List<Op>): Boolean {
        val resultsNeeded = expression.result

        fun solveRec(operands: List<Long>, currentResult: Long): Boolean {
            if (currentResult == resultsNeeded && operands.isEmpty()) {
                return true
            }
            if (operands.isEmpty()) {
                return false
            }

            return operators.any { op ->
                solveRec(operands.drop(1), op.exec(currentResult, operands.first()))
            }
        }

        return solveRec(expression.operands.drop(1), expression.operands.first())
    }

    fun part1(): Long {
        val expressions = getInput()
        return expressions.filter { canBeSolved(it, listOf(Op.ADD, Op.MUL)) }.sumOf { it.result }
    }

    fun part2(): Long {
        val expressions = getInput()
        return expressions.filter { canBeSolved(it, listOf(Op.ADD, Op.MUL, Op.CONCAT)) }.sumOf { it.result }
    }
}

fun main() {
    printWithTime { Day7.part1() }
    printWithTime { Day7.part2() }
}
