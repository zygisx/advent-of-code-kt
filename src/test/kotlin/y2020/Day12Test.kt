package y2020

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day12Test {

    val day12 = Day12()

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    val testInput = """
    F10
    N3
    F7
    R90
    F11
    """.trimIndent().lines()

    @Test
    fun `part1 should return 25`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = day12.part1()
        assertEquals(25, answer)
    }

    @Test
    fun `part2 should return 286`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = day12.part2()
        assertEquals(286, answer)
    }
}