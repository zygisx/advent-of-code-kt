package y2022

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day14Test {

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    val testInput = """
    498,4 -> 498,6 -> 496,6
    503,4 -> 502,4 -> 502,9 -> 494,9
    """.trimIndent().lines()

    @Test
    fun `part1 should work as in example`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day14.part1()
        assertEquals(24, answer)
    }

    @Test
    fun `part2 should work as in example`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day14.part2()
        assertEquals(93, answer)
    }
}