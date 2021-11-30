package y2020

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day10Test {

    val day10 = Day10()

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    val testInput = """
    28
    33
    18
    42
    31
    14
    46
    20
    48
    47
    24
    23
    49
    45
    19
    38
    39
    11
    1
    32
    25
    35
    8
    17
    7
    9
    4
    2
    34
    10
    3
    """.trimIndent().lines()

    @Test
    fun `part1 should return 220`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = day10.part1()
        assertEquals(220, answer)
    }

    @Test
    fun `part2 should return 19208`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = day10.part2()
        assertEquals(19208, answer)
    }
}