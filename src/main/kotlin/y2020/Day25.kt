package y2020


object Day25 : Day {
    override fun day() = 25

    private fun getInput() = getInputAsList().map { it.toLong() }

    const val DIVIDER = 20201227
    const val SUBJECT = 7

    private fun loopSize(publicKey: Long): Long {
        var value = 1L
        var loopSize = 0L
        while (value != publicKey) {
            value *= SUBJECT
            value %= DIVIDER
            loopSize++
        }

        return loopSize
    }

    private fun encryptionKey(loopSize: Long, subject: Long): Long {
        var value = 1L
        (1..loopSize).forEach {
            value *= subject
            value %= DIVIDER
        }
        return value
    }

    fun part1(): Long {
        val keys = getInput()

        val loopSize1 = loopSize(keys[0])
        val loopSize2 = loopSize(keys[1])

        println(loopSize1)
        println(loopSize2)
        println(encryptionKey(loopSize1, keys[1]))
        println(encryptionKey(loopSize2, keys[0]))

        return encryptionKey(loopSize1, keys[1])
    }

    fun part2() {
        throw NotImplementedError()
    }
}

fun main() {
    println(Day25.part1())
    println(Day25.part2())
}