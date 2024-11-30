package y2023


object Day1 : Day {

    private val DIGITS = mapOf(
        "one" to "1",
        "two" to "2",
        "three" to "3",
        "four" to "4",
        "five" to "5",
        "six" to "6",
        "seven" to "7",
        "eight" to "8",
        "nine" to "9"
    )

    override fun day() = 1

    private fun getInput() = getInputAsList()

    private fun firstLastDigits(line: String): Pair<Char, Char> {
        val firstDigit = line.firstOrNull { it.isDigit() }!!
        val lastDigit = line.reversed().firstOrNull { it.isDigit() }!!
        return firstDigit to lastDigit
    }

    private fun replaceFirstDigit(input: String): String {
        val firstNumber = DIGITS.keys
            .map { it to input.indexOf(it) }
            .filter { it.second != -1 }
            .minByOrNull { it.second }
        return if (firstNumber != null) {
            val firstDigitIdx = input.indexOfFirst { it.isDigit() }
            if (firstDigitIdx != -1 && firstDigitIdx < firstNumber.second) {
                input // NOT worth to replace if digit goes before string number
            } else {
                input.replaceFirst(firstNumber.first, DIGITS[firstNumber.first]!!)
            }
        } else input
    }

    private fun replaceLastDigit(input: String): String {
        val lastNumber = DIGITS.keys
            .map { it to input.lastIndexOf(it) }
            .filter { it.second != -1 }
            .maxByOrNull { it.second }
        return if (lastNumber != null) {
            input.replaceRange(
                lastNumber.second ..< lastNumber.second + lastNumber.first.length,
                DIGITS[lastNumber.first]!!)
        } else input
    }

    private fun stringReplaceByMap(input: String): String {
        return input
            .let { replaceFirstDigit(it) }
            .let { replaceLastDigit(it) }
    }

    fun part1(): Int {
        return getInput()
            .map(::firstLastDigits)
            .map { (first, last) -> "$first$last" }
            .mapNotNull { it.toIntOrNull() }
            .sum()

    }

    fun part2(): Int {
        return getInput()
            .map(::stringReplaceByMap)
            .map(::firstLastDigits)
            .map { (first, last) -> "$first$last" }
            .mapNotNull { it.toIntOrNull() }
            .sum()
    }
}

fun main() {
    println(Day1.part1())
    println(Day1.part2())
}
