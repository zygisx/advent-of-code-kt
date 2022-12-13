package y2022

object Day13 : Day {
    override fun day() = 13

    private fun getInput() = getInputAsList()

    sealed interface Item
    data class Lst(val list: MutableList<Item>) : Item {
        override fun toString(): String {
            return "$list"
        }
        companion object {
            fun of(vararg items: Item) = Lst(items.toMutableList())
            fun empty() = Lst(mutableListOf())
        }
    }
    data class Num(val value: Int) : Item {
        override fun toString(): String {
            return "$value"
        }
    }

    private fun parseLine(start: Int, line: String, result: Lst): Pair<Lst, Int> {
        var idx = start
        while (idx < line.length) {
            when (line[idx]) {
                ',' -> {
                    idx += 1
                    continue
                }
                '[' -> {
                    val (nextLst, nextIdx) = parseLine(idx+1, line, Lst.empty())
                    idx = nextIdx
                    result.list.add(nextLst)
                }
                ']' -> {
                    return result to idx+1
                }
                else -> {
                    var number = ""
                    while (line[idx].isDigit()) {
                        number += line[idx]
                        idx += 1
                    }
                    result.list.add(Num(number.toInt()))
                }
            }
        }
        error("Shouldn't get here")
    }

    private fun isRightOrder(left: Item, right: Item): Boolean? {
        return when {
            left is Num && right is Num -> isRightOrder(left, right)
            left is Num && right is Lst -> isRightOrder(Lst.of(left), right)
            left is Lst && right is Num -> isRightOrder(left, Lst.of(right))
            left is Lst && right is Lst -> isRightOrder(left, right)
            else -> error("Unknown items $left, $right")
        }
    }

    private fun isRightOrder(left: Num, right: Num): Boolean? {
        return if (left.value < right.value) true else if (left.value == right.value) null else false
    }

    private fun isRightOrder(left: Lst, right: Lst): Boolean? {
        val nonNull = left.list.zip(right.list).firstOrNull { isRightOrder(it.first, it.second) != null }
            ?: return when {
                left.list.size < right.list.size -> true
                left.list.size == right.list.size -> null
                else -> false
            }
        return isRightOrder(nonNull.first, nonNull.second)
    }

    fun part1(): Int {
        val linePairs = getInput()
            .filter { it.isNotBlank() }
            .map { parseLine(1, it, Lst.empty()).first }
            .chunked(2)

        return linePairs.foldIndexed(0) { idx, acc, pair ->
            val left = pair[0]
            val right = pair[1]
            val isRight = isRightOrder(left, right) ?: false
            if (isRight) acc + idx + 1 else acc
        }
    }

    fun part2(): Int {
        val dividerPackets = listOf(
            parseLine(1, "[[2]]", Lst.empty()).first,
            parseLine(1, "[[6]]", Lst.empty()).first,
        )
        val packets = getInput()
            .filter { it.isNotBlank() }
            .map { parseLine(1, it, Lst.empty()).first } + dividerPackets

        val sorted = mutableListOf(packets.first())

        packets.drop(1).forEach { packet ->
            val idx = sorted.indexOfFirst { isRightOrder(packet, it) == true }
            if (idx < 0) {
                sorted.add(packet)
            } else {
                sorted.add(idx, packet)
            }
        }

        return sorted.foldIndexed(1) { idx, acc, packet ->
            if (packet in dividerPackets) acc * (idx+1) else acc
        }
    }
}

fun main() {
    println(Day13.part1())
    println(Day13.part2())
}