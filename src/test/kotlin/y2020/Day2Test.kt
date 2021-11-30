package y2020

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

    val testInput = """
    1-3 a: abcde
    1-3 b: cdefg
    2-9 c: ccccccccc
    """.trimIndent().lines()

    @Test
    fun `part1 should return 2`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = day2.part1()
        assertEquals(2, answer)
    }

    @Test
    fun `part2 should return 1`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = day2.part2()
        assertEquals(1, answer)
    }
}