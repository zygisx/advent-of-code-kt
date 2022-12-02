package y2022

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day2Test {

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    val testInput = """
    A Y
    B X
    C Z
    """.trimIndent().lines()

    @Test
    fun `part1 should return 15`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day2.part1()
        assertEquals(15, answer)
    }

    @Test
    fun `part2 should return 12`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day2.part2()
        assertEquals(12, answer)
    }
}