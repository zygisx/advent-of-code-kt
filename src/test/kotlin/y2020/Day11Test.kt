package y2020

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day11Test {

    val day11 = Day11()

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    val testInput = """
    #.##.##.##
    #######.##
    #.#.#..#..
    ####.##.##
    #.##.##.##
    #.#####.##
    ..#.#.....
    ##########
    #.######.#
    #.#####.##
    """.trimIndent()

    @Test
    fun `part1 should return 37`() {
        every { InputReader.getInputAsString(any()) } returns testInput
        val answer = day11.part1()
        assertEquals(37, answer)
    }

    @Test
    fun `part2 should return 26`() {
        every { InputReader.getInputAsString(any()) } returns testInput
        val answer = day11.part2()
        assertEquals(26, answer)
    }
}