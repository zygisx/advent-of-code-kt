package y2020

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day6Test {

    val day6 = Day6()

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    val testInput = """
    abc

    a
    b
    c

    ab
    ac

    a
    a
    a
    a

    b
    """.trimIndent()

    @Test
    fun `part1 should return 11`() {
        every { InputReader.getInputAsString(any()) } returns testInput
        val answer = day6.part1()
        assertEquals(11, answer)
    }

    @Test
    fun `part2 should return 6`() {
        every { InputReader.getInputAsString(any()) } returns testInput
        val answer = day6.part2()
        assertEquals(6, answer)
    }
}