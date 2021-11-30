package y2020

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day13Test {

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    val testInput = """
    939
    7,13,x,x,59,x,31,19
    """.trimIndent()

    @Test
    fun `part1 should return 295`() {
        every { InputReader.getInputAsString(any()) } returns testInput
        val answer = Day13.part1()
        assertEquals(295, answer)
    }

    @Test
    fun `part2 should return 754018`() {
        every { InputReader.getInputAsString(any()) } returns """
            1
            67,7,59,61
        """.trimIndent()
        val answer = Day13.part2()
        assertEquals(754018, answer)
    }

    @Test
    fun `part2 should return 779210`() {
        every { InputReader.getInputAsString(any()) } returns """
            1
            67,x,7,59,61
        """.trimIndent()
        val answer = Day13.part2()
        assertEquals(779210, answer)
    }

    @Test
    fun `part2 should return 1202161486`() {
        every { InputReader.getInputAsString(any()) } returns """
            1
            1789,37,47,1889
        """.trimIndent()
        val answer = Day13.part2()
        assertEquals(1202161486, answer)
    }
}