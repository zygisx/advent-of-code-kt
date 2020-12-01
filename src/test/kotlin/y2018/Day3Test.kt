package y2018

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

    @Test
    fun `part1 should return 4`() {
        every { InputReader.getInputAsList(any()) } returns listOf(
                "#1 @ 1,3: 4x4",
                "#2 @ 3,1: 4x4",
                "#3 @ 5,5: 2x2"
        )
        val answer = day3.part1()
        assertEquals(4, answer)
    }

    @Test
    fun `part2 should return 3`() {
        every { InputReader.getInputAsList(any()) } returns listOf(
                "#1 @ 1,3: 4x4",
                "#2 @ 3,1: 4x4",
                "#3 @ 5,5: 2x2"
        )
        val answer = day3.part2()
        assertEquals(3, answer)
    }
}