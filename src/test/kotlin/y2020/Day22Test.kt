package y2020

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day22Test {

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    val testInput = """
    Player 1:
    9
    2
    6
    3
    1

    Player 2:
    5
    8
    4
    7
    10
    """.trimIndent().lines()

    @Test
    fun `part1 should return 306`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day22.part1()
        assertEquals(306, answer)
    }

    @Test
    fun `part2 should return 291`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day22.part2()
        assertEquals(291, answer)
    }
}