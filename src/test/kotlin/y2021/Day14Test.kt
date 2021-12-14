package y2021

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day14Test {

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    val testInput = """
    NNCB

    CH -> B
    HH -> N
    CB -> H
    NH -> C
    HB -> C
    HC -> B
    HN -> C
    NN -> C
    BH -> H
    NC -> B
    NB -> B
    BN -> B
    BB -> N
    BC -> B
    CC -> N
    CN -> C
    """.trimIndent().lines()

    @Test
    fun `part1 should return 1588`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day14.part1()
        assertEquals(1588L, answer)
    }

    @Test
    fun `part2 should return 2188189693529`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day14.part2()
        assertEquals(2188189693529, answer)
    }
}