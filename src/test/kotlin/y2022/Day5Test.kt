package y2022

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
        [D]    
    [N] [C]    
    [Z] [M] [P]
     1   2   3 
    
    move 1 from 2 to 1
    move 3 from 1 to 3
    move 2 from 2 to 1
    move 1 from 1 to 2
    """.trimIndent().lines()

    @Test
    fun `part1 should return CMZ`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day5.part1()
        assertEquals("CMZ", answer)
    }

    @Test
    fun `part2 should return MCD`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day5.part2()
        assertEquals("MCD", answer)
    }
}