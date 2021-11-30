package y2020

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day23Test {

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    val testInput = """
    389125467
    """.trimIndent().lines()

    @Test
    fun `part1 should return 67384529`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day23.part1()
        assertEquals("67384529", answer)
    }

    @Test
    fun `part2 should return 149245887792`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day23.part2()
        assertEquals(149245887792, answer)
    }
}