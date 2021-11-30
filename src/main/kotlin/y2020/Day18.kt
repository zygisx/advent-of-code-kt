package y2020

import misc.Collections.stack

object Day18 : Day {
    override fun day() = 18

    interface Operator {
        fun op(a: Token.Const, b: Token.Const): Token.Const
    }

    sealed class Token {
        data class Const(val num: Long): Token()
        object Add: Token(), Operator {
            override fun op(a: Const, b: Const) = Const(a.num + b.num)
        }
        object Mul: Token(), Operator {
            override fun op(a: Const, b: Const) = Const(a.num * b.num)
        }
        object Open: Token(), Operator {
            override fun op(a: Const, b: Const): Const { throw NotImplementedError() }
        }
        object Close: Token()
    }

    private fun getInput() = getInputAsList()

    fun tokenize(expression: String): List<Token> {
        val cleanExpression = expression.replace("\\s".toRegex(), "")
        val expressions = mutableListOf<Token>()
        var idx = 0
        while (idx < cleanExpression.length) {
            val number = cleanExpression.drop(idx).takeWhile { it.isDigit() }
            if (number.isNotEmpty()) {
                expressions.add(Token.Const(number.toLong()))
                idx += number.length
            } else {
                when(cleanExpression[idx]) {
                    '*' -> expressions.add(Token.Mul)
                    '+' -> expressions.add(Token.Add)
                    '(' -> expressions.add(Token.Open)
                    ')' -> expressions.add(Token.Close)
                }
                idx++
            }
        }
        return expressions
    }

    fun evaluate(expression: List<Token>, opsPriority: (Operator) -> Int): Long {
        val constStack = stack(emptyList<Token.Const>())
        val opsStack = stack(emptyList<Operator>())

        fun calculateWhileOp(predicate: (Operator) -> Boolean) {
            while (opsStack.peek() != null && predicate(opsStack.peek())) {
                val op = opsStack.pop()
                val a = constStack.pop()
                val b = constStack.pop()
                constStack.push(op.op(a, b))
            }
        }

        var idx = 0
        while (idx < expression.size) {
            when (expression[idx]) {
                is Token.Const -> constStack.push(expression[idx] as Token.Const)
                is Token.Mul -> {
                    calculateWhileOp { opsPriority(it) >= opsPriority(Token.Mul) }
                    opsStack.push(expression[idx] as Operator)
                }
                is Token.Add -> {
                    calculateWhileOp { opsPriority(it) >= opsPriority(Token.Add) }
                    opsStack.push(expression[idx] as Operator)
                }
                is Token.Open -> opsStack.push(expression[idx] as Operator)
                is Token.Close -> calculateWhileOp { it !is Token.Open }.also { opsStack.pop() }
            }
            idx++
        }
        while (opsStack.peek() != null) {
            val op = opsStack.pop()
            val a = constStack.pop()
            val b = constStack.pop()
            constStack.push(op.op(a, b))
        }

        return constStack.first.num
    }

    fun part1(): Long {
        val opsPriority = fun (op: Operator) = when (op) {
            is Token.Add -> 1
            is Token.Mul -> 1
            else -> 0
        }
        return getInput()
            .map { tokenize(it) }
            .map { evaluate(it, opsPriority) }
            .sum()
    }

    fun part2(): Long {
        val opsPriority = fun (op: Operator) = when (op) {
            is Token.Add -> 2
            is Token.Mul -> 1
            else -> 0
        }
        return getInput()
            .map { tokenize(it) }
            .map { evaluate(it, opsPriority) }
            .sum()
    }
}

fun main() {
    println(Day18.part1())
    println(Day18.part2())
}