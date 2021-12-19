package y2021

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day17Test {

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    val testInput = """
    target area: x=20..30, y=-10..-5
    """.trimIndent()

    @Test
    fun `part1 should return 45`() {
        every { InputReader.getInputAsString(any()) } returns testInput
        val answer = Day17.part1()
        assertEquals(45, answer)
    }

    @Test
    fun `part2 should return 112`() {
        every { InputReader.getInputAsString(any()) } returns testInput
        val answer = Day17.part2()
        assertEquals(112, answer)
    }
}