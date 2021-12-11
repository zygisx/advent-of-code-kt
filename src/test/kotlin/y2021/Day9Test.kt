package y2021

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day9Test {

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }
    
    val testInput = """
    2199943210
    3987894921
    9856789892
    8767896789
    9899965678
    """.trimIndent().lines()

    @Test
    fun `part1 should return 15`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day9.part1()
        assertEquals(15, answer)
    }

    @Test
    fun `part2 should return 1134`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day9.part2()
        assertEquals(1134, answer)
    }
}