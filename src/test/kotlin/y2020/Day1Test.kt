package y2020

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day1Test {

    val day1 = Day1()

    private val testInput = """
        1721
        979
        366
        299
        675
        1456
    """.trimIndent().lines()

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    @Test
    fun `part1 should return 514579`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = day1.part1()
        assertEquals(514579, answer)
    }

    @Test
    fun `part2 should return 241861950`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = day1.part2()
        assertEquals(241861950, answer)
    }
}