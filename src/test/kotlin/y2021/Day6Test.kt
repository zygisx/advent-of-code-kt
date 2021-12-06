package y2021

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day6Test {

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    val testInput = """
    3,4,3,1,2
    """.trimIndent()

    @Test
    fun `part1 should return 5934`() {
        every { InputReader.getInputAsString(any()) } returns testInput
        val answer = Day6.part1()
        assertEquals(5934, answer)
    }

    @Test
    fun `part2 should return 26984457539`() {
        every { InputReader.getInputAsString(any()) } returns testInput
        val answer = Day6.part2()
        assertEquals(26984457539, answer)
    }
}