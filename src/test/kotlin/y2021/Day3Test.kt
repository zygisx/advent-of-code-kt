package y2021

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day3Test {

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    val testInput = """
    00100
    11110
    10110
    10111
    10101
    01111
    00111
    11100
    10000
    11001
    00010
    01010
    """.trimIndent().lines()

    @Test
    fun `part1 should return 198`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day3.part1()
        assertEquals(198u, answer)
    }

    @Test
    fun `part2 should return ???`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day3.part2()
        assertEquals(230u, answer)
    }
}