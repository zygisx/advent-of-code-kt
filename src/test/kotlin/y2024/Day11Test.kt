package y2024

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day11Test {

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    val testInput = """
        125 17
    """.trimIndent().lines()

    @Test
    fun `part1 should work as in example`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day11.part1()
        assertEquals(55312, answer)
    }

    @Test
    fun `part2 should work as in example`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day11.part2()
        assertEquals(TODO(), answer)
    }
}