package y2023

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day7Test {

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    val testInput = """
    32T3K 765
    T55J5 684
    KK677 28
    KTJJT 220
    QQQJA 483
    """.trimIndent().lines()

    @Test
    fun `part1 should work as in example`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day7.part1()
        assertEquals(6440, answer)
    }

    @Test
    fun `part2 should work as in example`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day7.part2()
        assertEquals(5905, answer)
    }
}