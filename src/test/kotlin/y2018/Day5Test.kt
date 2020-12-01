package y2018

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day5Test {

    val day5 = Day5()

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    val TestInput = "dabAcCaCBAcCcaDA"

    @Test
    fun `part1 should return 10`() {
        every { InputReader.getInputAsString(any()) } returns TestInput
        val answer = day5.part1()
        assertEquals(10, answer)
    }

    @Test
    fun `part2 should return 4`() {
        every { InputReader.getInputAsString(any()) } returns TestInput
        val answer = day5.part2()
        assertEquals(4, answer)
    }
}