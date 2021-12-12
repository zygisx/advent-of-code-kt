package y2021

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day11Test {

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    val testInput = """
    5483143223
    2745854711
    5264556173
    6141336146
    6357385478
    4167524645
    2176841721
    6882881134
    4846848554
    5283751526
    """.trimIndent().lines()

    @Test
    fun `part1 should return 1656`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day11.part1()
        assertEquals(1656, answer)
    }

    @Test
    fun `part2 should return 195`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day11.part2()
        assertEquals(195, answer)
    }
}