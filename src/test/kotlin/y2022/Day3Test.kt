package y2022

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
    vJrwpWtwJgWrhcsFMMfFFhFp
    jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
    PmmdzqPrVvPwwTWBwg
    wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
    ttgJtRGJQctTZtZT
    CrZsJsPPZsGzwwsLwLmpwMDw
    """.trimIndent().lines()

    @Test
    fun `part1 should return 157`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day3.part1()
        assertEquals(157, answer)
    }

    @Test
    fun `part2 should return 70`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day3.part2()
        assertEquals(70, answer)
    }
}