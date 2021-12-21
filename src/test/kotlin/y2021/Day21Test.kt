package y2021

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day21Test {

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    val testInput = """
    Player 1 starting position: 4
    Player 2 starting position: 8
    """.trimIndent()

    @Test
    fun `part1 should return 739785`() {
        every { InputReader.getInputAsString(any()) } returns testInput
        val answer = Day21.part1()
        assertEquals(739785, answer)
    }

    @Test
    fun `part2 should return 444356092776315`() {
        every { InputReader.getInputAsString(any()) } returns testInput
        val answer = Day21.part2()
        assertEquals(444356092776315, answer)
    }
}