package y2021

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
    6,10
    0,14
    9,10
    0,3
    10,4
    4,11
    6,0
    6,12
    4,1
    0,13
    10,12
    3,4
    3,0
    8,4
    1,10
    2,14
    8,10
    9,0

    fold along y=7
    fold along x=5
    """.trimIndent().lines()

    @Test
    fun `part1 should return 17`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day13.part1()
        assertEquals(17, answer)
    }
}