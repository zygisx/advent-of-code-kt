package y2022

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
    nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg
    """.trimIndent().lines()

    @Test
    fun `part1 should return 10`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day6.part1()
        assertEquals(10, answer)
    }

    @Test
    fun `part2 should return 29`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day6.part2()
        assertEquals(29, answer)
    }
}