package y2022

import misc.Counter


object Day11 : Day {
    override fun day() = 11

    private fun getInput() = getInputAsString()

    sealed interface Op {
        fun calcNew(old: Long): Long
    }
    class Add(private val operand: Int) : Op {
        override fun calcNew(old: Long) = old + operand
    }
    class Mul(private val operand: Int) : Op {
        override fun calcNew(old: Long) = old * operand
    }
    object Square : Op {
        override fun calcNew(old: Long) = old * old
    }

    data class Monkey(
        val idx: Int,
        var items: MutableList<Long>,
        val op: Op,
        val testDiv: Int,
        val trueTarget: Int,
        val falseTarget: Int
    )

    fun parse(input: String): List<Monkey> {
        return input.split("""Monkey \d+:\n""".toRegex()).drop(1).mapIndexed { idx, str ->
            val lines = str.lines()
            val items = lines[0].split(":")[1].split(",").map { it.trim().toLong() }
            val op = lines[1].let {
                val operand by lazy { it.split(" ").last().trim().toInt() }
                when {
                    it.contains("old * old") -> Square
                    it.contains("*") -> Mul(operand)
                    it.contains("+") -> Add(operand)
                    else -> throw IllegalArgumentException("unknown line $it")
                }
            }
            val testDiv = lines[2].split(" ").last().trim().toInt()
            val trueTarget = lines[3].split(" ").last().trim().toInt()
            val falseTarget = lines[4].split(" ").last().trim().toInt()

            Monkey(idx, items.toMutableList(), op, testDiv, trueTarget, falseTarget)
        }
    }

    private fun repeatRound(monkeys: List<Monkey> , rounds: Int, worryLevelReduce: (worry: Long) -> Long): Long {
        val inspectionCounter = Counter<Int>()
        repeat(rounds) {
            monkeys.forEach { monkey ->

                monkey.items.map { item ->
                    val newWorry = worryLevelReduce(monkey.op.calcNew(item))
                    val targetMonkey = if (newWorry % monkey.testDiv == 0L) monkey.trueTarget else monkey.falseTarget
                    monkeys[targetMonkey].items.add(newWorry)
                    inspectionCounter.inc(monkey.idx)
                }
                monkey.items.clear()
            }
        }

        return inspectionCounter.getMap().values.sortedDescending().take(2).let { 1L * it[0] * it[1] }
    }

    fun part1() : Long {
        val monkeys = parse(getInput())

        return repeatRound(monkeys, 20) { it / 3 }
    }

    fun part2() : Long {
        val monkeys = parse(getInput())
        val allTestDivMul = monkeys.map { it.testDiv }.fold(1L) { a, b -> a * b }
        return repeatRound(monkeys, 10_000) { it % allTestDivMul }
    }
}

fun main() {
    println(Day11.part1())
    println(Day11.part2())
}