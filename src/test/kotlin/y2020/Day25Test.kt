package y2020

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import org.junit.jupiter.api.Disabled
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day25Test {

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    val testInput = """
    5764801
    17807724
    """.trimIndent().lines()

    @Test
    fun `part1 should return 14897079`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day25.part1()
        assertEquals(14897079, answer)
    }

    @Disabled
    @Test
    fun `part2 should return xxx`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day25.part2()
        assertEquals(3, answer)
    }
}