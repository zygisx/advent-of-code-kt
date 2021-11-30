package y2020

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day3Test {

    val day3 = Day3()

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    val testInput = """
    ..##.......
    #...#...#..
    .#....#..#.
    ..#.#...#.#
    .#...##..#.
    ..#.##.....
    .#.#.#....#
    .#........#
    #.##...#...
    #...##....#
    .#..#...#.#
    """.trimIndent().lines()

    @Test
    fun `part1 should return 7`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = day3.part1()
        assertEquals(7, answer)
    }

    @Test
    fun `part2 should return 336`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = day3.part2()
        assertEquals(336, answer)
    }
}