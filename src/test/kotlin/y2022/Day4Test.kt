package y2022

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day4Test {

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    val testInput = """
    2-4,6-8
    2-3,4-5
    5-7,7-9
    2-8,3-7
    6-6,4-6
    2-6,4-8
    """.trimIndent().lines()

    @Test
    fun `part1 should return 2`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day4.part1()
        assertEquals(2, answer)
    }

    @Test
    fun `part2 should return 4`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day4.part2()
        assertEquals(4, answer)
    }
}