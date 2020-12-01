package y2018

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day6Test {

    val day6 = Day6(32)

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    private val testInput = """
        1, 1
        1, 6
        8, 3
        3, 4
        5, 5
        8, 9""".trimStart().trimIndent().lines()

    @Test
    fun `part1 should return 17`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = day6.part1()
        assertEquals(17, answer)
    }

    @Test
    fun `part2 should return 16`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = day6.part2()
        assertEquals(16, answer)
    }
}