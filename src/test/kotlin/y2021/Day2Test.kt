package y2021

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
    forward 5
    down 5
    forward 8
    up 3
    down 8
    forward 2
    """.trimIndent().lines()

    @Test
    fun `part1 should return ???`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day2.part1()
        assertEquals(150, answer)
    }

    @Test
    fun `part2 should return ???`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day2.part2()
        assertEquals(900, answer)
    }
}