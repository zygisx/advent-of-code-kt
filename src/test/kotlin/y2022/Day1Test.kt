package y2022


import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day1Test {

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    val testInput = """
    1000
    2000
    3000

    4000

    5000
    6000

    7000
    8000
    9000

    10000
    """.trimIndent()

    @Test
    fun `part1 should return 24k`() {
        every { InputReader.getInputAsString(any()) } returns testInput
        val answer = Day1.part1()
        assertEquals(24000, answer)
    }

    @Test
    fun `part2 should return 45k`() {
        every { InputReader.getInputAsString(any()) } returns testInput
        val answer = Day1.part2()
        assertEquals(45000, answer)
    }
}