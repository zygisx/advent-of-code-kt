package y2022

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day9Test {

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }
    
    val testInput = """
    R 4
    U 4
    L 3
    D 1
    R 4
    D 1
    L 5
    R 2
    """.trimIndent().lines()

    @Test
    fun `part1 should work as in example`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day9.part1()
        assertEquals(13, answer)
    }

    @Test
    fun `part2 should work as in example`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day9.part2()
        assertEquals(1, answer)
    }

    @Test
    fun `part2 should work with larger example`() {
        every { InputReader.getInputAsList(any()) } returns """
            R 5
            U 8
            L 8
            D 3
            R 17
            D 10
            L 25
            U 20
        """.trimIndent().lines()
        val answer = Day9.part2()
        assertEquals(36, answer)
    }
}