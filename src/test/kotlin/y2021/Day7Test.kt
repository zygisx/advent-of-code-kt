package y2021

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day7Test {

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    val testInput = """
    16,1,2,0,4,2,7,1,2,14
    """.trimIndent()

    @Test
    fun `part1 should return 37`() {
        every { InputReader.getInputAsString(any()) } returns testInput
        val answer = Day7.part1()
        assertEquals(37, answer)
    }

    @Test
    fun `part2 should return 168`() {
        every { InputReader.getInputAsString(any()) } returns testInput
        val answer = Day7.part2()
        assertEquals(168, answer)
    }
}