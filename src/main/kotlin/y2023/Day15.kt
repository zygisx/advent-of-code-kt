package y2023

import kotlin.time.measureTimedValue


object Day15 : Day {
    override fun day() = 15

    private fun getInput() = getInputAsList().first().let {
        it.split(",")
    }

    private fun charHash(init: Int, char: Char): Int {
        val ascii = char.code.toByte()
        return ((init + ascii) * 17) % 256
    }

    private fun labelHash(label: String): Int {
        return label.fold(0) { acc, char -> charHash(acc, char) }
    }

    fun part1(): Int {
        val initSequence = getInput()
        return initSequence.sumOf { labelHash(it) }
    }

    sealed interface Lens {
        val label: String
    }
    data class Dash(override val label: String) : Lens
    data class Equals(override val label: String, val focal: Int): Lens

    private fun parseLens(string: String): Lens {
        return if ('=' in string) {
            val (label, focal) = string.split("=")
            Equals(label, focal.toInt())
        } else Dash(string.replace("-", ""))
    }

    fun part2(): Int {
        val initSequence = getInput()
        val boxes = initSequence
            .map { parseLens(it) }
            .fold(mutableMapOf<Int, MutableList<Equals>>()) { boxes, lens ->
                val lensHash = labelHash(lens.label)
                val box = boxes.getOrPut(lensHash) { mutableListOf() }
                when (lens) {
                    is Dash -> box.removeIf { it.label == lens.label }
                    is Equals -> {
                        box.find { it.label == lens.label }
                            ?.let { box.set(box.indexOf(it), lens) }
                            ?: box.add(lens)
                    }
                }
                boxes
            }

        return boxes
            .map { (key, value) ->
                value.mapIndexed { idx, lens -> (key+1) *  (idx+1) * lens.focal }.sum()
            }.sum()
    }
}

fun main() {
    val (part1Answer, part1Duration) = measureTimedValue { Day15.part1() }
    println("$part1Answer in ${part1Duration.inWholeMilliseconds} ms")

    val (part2Answer, part2Duration) = measureTimedValue { Day15.part2() }
    println("$part2Answer in ${part2Duration.inWholeMilliseconds} ms")


}