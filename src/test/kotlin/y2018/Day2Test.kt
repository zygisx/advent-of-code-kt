package y2018

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day2Test {

    val day2 = Day2()

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    @Test
    fun `part1 should return 12`() {
        every { InputReader.getInputAsList(any()) } returns listOf(
                "abcdef",
                "bababc",
                "abbcde",
                "abcccd",
                "aabcdd",
                "abcdee",
                "ababab",
        )
        val answer = day2.part1()
        assertEquals(12, answer)
    }

    @Test
    fun `part2 should return 2`() {
        every { InputReader.getInputAsList(any()) } returns listOf(
                "abcde",
                "fghij",
                "klmno",
                "pqrst",
                "fguij",
                "axcye",
                "wvxyz",
        )
        val answer = day2.part2()
        assertEquals("fgij", answer)
    }
}