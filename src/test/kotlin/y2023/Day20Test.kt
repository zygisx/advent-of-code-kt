package y2023

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import org.junit.jupiter.api.Disabled
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day20Test {

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    val testInput = """
    1
    """.trimIndent().lines()

    @Test
    fun `part1 should work as in example`() {
        every { InputReader.getInputAsList(any()) } returns """
            broadcaster -> a, b, c
            %a -> b
            %b -> c
            %c -> inv
            &inv -> a
        """.trimIndent().lines()
        val answer = Day20.part1()
        assertEquals(32000000, answer)
    }

    @Test
    fun `part1 example 2`() {
        every { InputReader.getInputAsList(any()) } returns """
            broadcaster -> a
            %a -> inv, con
            &inv -> b
            %b -> con
            &con -> output
        """.trimIndent().lines()
        val answer = Day20.part1()
        assertEquals(11687500, answer)
    }

    @Test
    @Disabled
    fun `part2 should work as in example`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day20.part2()
        assertEquals(TODO(), answer)
    }
}