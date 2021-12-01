package y2021

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

    val testInput = """
    199
    200
    208
    210
    200
    207
    240
    269
    260
    263
    """.trimIndent().lines()

    @Test
    fun `part1 should return 7`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day1.part1()
        assertEquals(7, answer)
    }

    @Test
    fun `part2 should return 5`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day1.part2()
        assertEquals(5, answer)
    }
}