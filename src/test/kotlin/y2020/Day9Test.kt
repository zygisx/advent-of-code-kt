package y2020

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day9Test {

    val day9 = Day9(5)

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    val testInput = """
    35
    20
    15
    25
    47
    40
    62
    55
    65
    95
    102
    117
    150
    182
    127
    219
    299
    277
    309
    576
    """.trimIndent().lines()

    @Test
    fun `part1 should return 127`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = day9.part1()
        assertEquals(127, answer)
    }

    @Test
    fun `part2 should return 62`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = day9.part2()
        assertEquals(62, answer)
    }
}