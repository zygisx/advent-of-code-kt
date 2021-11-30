package y2020

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day8Test {

    val day8 = Day8()

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    val testInput = """
    nop +0
    acc +1
    jmp +4
    acc +3
    jmp -3
    acc -99
    acc +1
    jmp -4
    acc +6
    """.trimIndent().lines()

    @Test
    fun `part1 should return 5`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = day8.part1()
        assertEquals(5, answer)
    }

    @Test
    fun `part2 should return 8`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = day8.part2()
        assertEquals(8, answer)
    }
}