package y2020

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import kotlin.test.BeforeTest
import kotlin.test.assertEquals
import kotlin.test.assertTrue


internal class Day5Test {

    val day5 = Day5()

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    val testInput = """
    FBFBBFFRLR
    BFFFBBFRRR
    FFFBBBFRRR
    BBFFBBFRLL
    """.trimIndent().lines()

    @Test
    fun `part1 should return 820`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = day5.part1()
        assertEquals(820, answer)
    }

    @Test
    fun `part2 should return ???`() {
        assertTrue { true }
    }
}