package y2024

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day8Test {

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    val testInput = """
        ............
        ........0...
        .....0......
        .......0....
        ....0.......
        ......A.....
        ............
        ............
        ........A...
        .........A..
        ............
        ............
    """.trimIndent().lines()

    @Test
    fun `simplest`() {
        val testInput = """
        ..........
        ..........
        ..........
        ....a.....
        ........a.
        .....a....
        ..........
        ..........
        ..........
        ..........
        """.trimIndent().lines()

        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day8.part1()
        assertEquals(4, answer)
    }

    @Test
    fun `part1 should work as in example`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day8.part1()
        assertEquals(14, answer)
    }

    @Test
    fun `simplest pt2`() {
        val testInput = """
        T.........
        ...T......
        .T........
        ..........
        ..........
        ..........
        ..........
        ..........
        ..........
        ..........
        """.trimIndent().lines()

        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day8.part2()
        assertEquals(9, answer)
    }

    @Test
    fun `part2 should work as in example`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day8.part2()
        assertEquals(34, answer)
    }
}