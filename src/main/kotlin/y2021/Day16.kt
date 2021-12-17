package y2021


object Day16 : Day {
    override fun day() = 16

    private fun getInput() = getInputAsString()

    private fun hexToBinaryString(hexStr: String): String {
        return hexStr
            .map { it.digitToInt(16).toString(2).padStart(4, '0') }
            .joinToString("")
    }

    sealed interface Packet {
        val version: Int
        val type: Int

        fun versionSum(): Long
        fun calculate(): Long
    }

    data class Literal(
        override val version: Int,
        override val type: Int,
        val value: Long
    ): Packet {
        override fun versionSum() = version.toLong()
        override fun calculate() = value
    }

    data class Operator(
        override val version: Int,
        override val type: Int,
        val innerPackets: List<Packet>
    ): Packet {
        override fun versionSum(): Long {
            return version + innerPackets.sumOf { it.versionSum() }
        }

        override fun calculate(): Long {
            return when (type) {
                0 -> innerPackets.sumOf { it.calculate() }
                1 -> innerPackets.fold(1) { acc, it -> acc * it.calculate() }
                2 -> innerPackets.minOf { it.calculate() }
                3 -> innerPackets.maxOf { it.calculate() }
                5 -> if (innerPackets[0].calculate() > innerPackets[1].calculate()) 1 else 0
                6 -> if (innerPackets[0].calculate() < innerPackets[1].calculate()) 1 else 0
                7 -> if (innerPackets[0].calculate() == innerPackets[1].calculate()) 1 else 0
                else -> throw IllegalArgumentException("Unknown type $type")
            }
        }
    }

    private fun parseLiteral(binaryString: String): Pair<Long, String> {
        var bin = binaryString
        var number = ""
        do {
            val slice = bin.slice(0 until 5)
            val last = slice.first() == '0'
            number += slice.substring(1)
            bin = bin.substring(5)
        } while (!last)
        return number.toLong(2) to bin
    }

    private fun parseOperatorInnerPackages(binaryString: String, type: Int): Pair<List<Packet>, String> {
        var bin = binaryString
        val lengthType = bin.first()
        bin = bin.substring(1)
        if (lengthType == '0') {
            val size = bin.slice(0 until 15).toInt(2)
            bin = bin.substring(15)
            println("$size $bin ")
            var binToParseFurther = bin.substring(0 until size)
            bin = bin.substring(size)
            val subPackets = mutableListOf<Packet>()
            while (binToParseFurther.length > 6) {
                val (packet, newBin) = parsePackets(binToParseFurther)
                println("$packet $newBin")
                binToParseFurther = newBin
                subPackets.add(packet)
            }
            return subPackets to bin
        } else {
            val packetsNum = bin.slice(0 until 11).toInt(2)
            bin = bin.substring(11)
            val subPackets = mutableListOf<Packet>()
            while (subPackets.size < packetsNum) {
                val (packet, newBin) = parsePackets(bin)
                subPackets.add(packet)
                bin = newBin
            }
            return subPackets to bin
        }
    }


    private fun parsePackets(binaryString: String): Pair<Packet, String> {
        var bin = binaryString
        val version = bin.slice(0 until 3).toInt(2)
        bin = bin.substring(3)
        val type = bin.slice(0 until 3).toInt(2)
        bin = bin.substring(3)

        if (type == 4) {
            val (literal, remainingBin) = parseLiteral(bin)
            return Literal(version, type, literal) to remainingBin
        } else {
            val (subPackets, remainingBin) = parseOperatorInnerPackages(bin, type)
            return Operator(version, type, subPackets) to remainingBin
        }
    }

    fun part1(): Long {
        val hexLine = getInput()
        val binaryString = hexToBinaryString(hexLine)
        val (packet, _) = parsePackets(binaryString)

        return packet.versionSum()
    }

    fun part2(): Long {
        val hexLine = getInput()
        val binaryString = hexToBinaryString(hexLine)
        val (packet, _) = parsePackets(binaryString)

        return packet.calculate()
    }
}

fun main() {
    println(Day16.part1())
    println(Day16.part2())
}