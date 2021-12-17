package y2021

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day15Test {

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    val testInput = """
    1163751742
    1381373672
    2136511328
    3694931569
    7463417111
    1319128137
    1359912421
    3125421639
    1293138521
    2311944581
    """.trimIndent().lines()

    @Test
    fun `part1 should return 40`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day15.part1()
        assertEquals(40, answer)
    }

    @Test
    fun `part2 should return 315`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day15.part2()
        assertEquals(315, answer)
    }
}