package y2020

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day14Test {

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    val testInput = """
    mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
    mem[8] = 11
    mem[7] = 101
    mem[8] = 0
    """.trimIndent()

    @Test
    fun `part1 should return 165`() {
        every { InputReader.getInputAsString(any()) } returns testInput
        val answer = Day14.part1()
        assertEquals(165, answer)
    }

    @Test
    fun `part2 should return 208`() {
        every { InputReader.getInputAsString(any()) } returns """
            mask = 000000000000000000000000000000X1001X
            mem[42] = 100
            mask = 00000000000000000000000000000000X0XX
            mem[26] = 1
        """.trimIndent()
        val answer = Day14.part2()
        assertEquals(208, answer)
    }
}