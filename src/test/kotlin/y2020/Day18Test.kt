package y2020

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import java.math.BigInteger
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day18Test {

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    val testInput = """
    1 + (2 * 3) + (4 * (5 + 6))
    5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))
    5 + (8 * 3 + 9 + 3 * 4 * 3)
    ((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2
    """.trimIndent().lines()

    @Test
    fun `part1 should return 13683`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day18.part1()
        assertEquals(13632 + 51 + 12240 + 437, answer)
    }

    @Test
    fun `part2 should return`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day18.part2()
        assertEquals(51 + 669060 + 1445 + 23340, answer)
    }
}