package y2023

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
    O....#....
    O.OO#....#
    .....##...
    OO.#O....O
    .O.....O#.
    O.#..O.#.#
    ..O..#O..O
    .......O..
    #....###..
    #OO..#....
    """.trimIndent().lines()

    @Test
    fun `part1 should work as in example`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day14.part1()
        assertEquals(136, answer)
    }

    @Test
    fun `part2 should work as in example`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day14.part2()
        assertEquals(64, answer)
    }
}