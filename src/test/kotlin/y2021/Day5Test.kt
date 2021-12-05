package y2021


import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day5Test {

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    val testInput = """
    0,9 -> 5,9
    8,0 -> 0,8
    9,4 -> 3,4
    2,2 -> 2,1
    7,0 -> 7,4
    6,4 -> 2,0
    0,9 -> 2,9
    3,4 -> 1,4
    0,0 -> 8,8
    5,5 -> 8,2
    """.trimIndent().lines()

    @Test
    fun `part1 should return 5`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day5.part1()
        assertEquals(5, answer)
    }

    @Test
    fun `part2 should return 12`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day5.part2()
        assertEquals(12, answer)
    }
}