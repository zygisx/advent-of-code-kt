package y2024

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day3Test {

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    val testInput = """
        xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))
    """.trimIndent()

    @Test
    fun `part1 should work as in example`() {
        every { InputReader.getInputAsString(any()) } returns testInput
        val answer = Day3.part1()
        assertEquals(161L, answer)
    }

    val testInput2 = """
        xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))
    """.trimIndent()

    @Test
    fun `part2 should work as in example`() {
        every { InputReader.getInputAsString(any()) } returns testInput2
        val answer = Day3.part2()
        assertEquals(48L, answer)
    }
}