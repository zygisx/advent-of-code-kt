package y2018

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day1Test {

    val day1 = Day1()

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    @Test
    fun `part1 should return 3`() {
        every { InputReader.getInputAsList(any()) } returns listOf("+1", "-2", "+3", "+1")
        val answer = day1.part1()
        assertEquals(3, answer)
    }

    @Test
    fun `part2 should return 2`() {
        every { InputReader.getInputAsList(any()) } returns listOf("+1", "-2", "+3", "+1")
        val answer = day1.part2()
        assertEquals(2, answer)
    }
}