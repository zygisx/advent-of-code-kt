package y2023

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day1Test {

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    val testInput1 = """
    1abc2
    pqr3stu8vwx
    a1b2c3d4e5f
    treb7uchet
    """.trimIndent().lines()

    @Test
    fun `part1 should work as in example`() {
        every { InputReader.getInputAsList(any()) } returns testInput1
        val answer = Day1.part1()
        assertEquals(142, answer)
    }

    val testInput2 = """
    two1nine
    eightwothree
    abcone2threexyz
    xtwone3four
    4nineeightseven2
    zoneight234
    7pqrstsixteen
    """.trimIndent().lines()

    @Test
    fun `part2 should work as in example`() {
        every { InputReader.getInputAsList(any()) } returns testInput2
        val answer = Day1.part2()
        assertEquals(281, answer)
    }

    @Test
    fun `edge case 1`() {
        every { InputReader.getInputAsList(any()) } returns listOf("1twone")
        val answer = Day1.part2()
        assertEquals(11, answer)
    }

    @Test
    fun `edge case 2`() {
        every { InputReader.getInputAsList(any()) } returns listOf("five1oneight")
        val answer = Day1.part2()
        assertEquals(58, answer)
    }
}