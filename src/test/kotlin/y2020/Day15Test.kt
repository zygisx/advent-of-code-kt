package y2020

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import org.junit.jupiter.api.Disabled
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day15Test {

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    val testInput = """
    0,3,6
    """.trimIndent()

    @Test
    fun `part1 should return 436`() {
        every { InputReader.getInputAsString(any()) } returns testInput
        val answer = Day15.part1()
        assertEquals(436, answer)
    }

    @Test
    fun `part1 should return 1836`() {
        every { InputReader.getInputAsString(any()) } returns "3,1,2"
        val answer = Day15.part1()
        assertEquals(1836, answer)
    }

    @Test
    @Disabled("Takes too long")
    fun `part2 should return 175594`() {
        every { InputReader.getInputAsString(any()) } returns testInput
        val answer = Day15.part2()
        assertEquals(175594, answer)
    }
}