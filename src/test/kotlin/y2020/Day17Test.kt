package y2020

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import org.junit.jupiter.api.Disabled
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day17Test {

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    val testInput = """
    .#.
    ..#
    ###
    """.trimIndent()

    @Disabled
    @Test
    fun `part1 should return 112`() {
        every { InputReader.getInputAsString(any()) } returns testInput
        val answer = Day17.part1()
        assertEquals(4, answer)
    }

    @Disabled
    @Test
    fun `part2 should return 848`() {
        every { InputReader.getInputAsString(any()) } returns testInput
        val answer = Day17.part2()
        assertEquals(848, answer)
    }
}