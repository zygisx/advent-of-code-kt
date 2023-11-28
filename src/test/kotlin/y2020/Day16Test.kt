package y2020

import  io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day16Test {

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    val testInput = """
    class: 1-3 or 5-7
    row: 6-11 or 33-44
    seat: 13-40 or 45-50

    your ticket:
    7,1,14

    nearby tickets:
    7,3,47
    40,4,50
    55,2,20
    38,6,12
    """.trimIndent()

    @Test
    fun `part1 should return 71`() {
        every { InputReader.getInputAsString(any()) } returns testInput
        val answer = Day16.part1()
        assertEquals(71, answer)
    }

    @Test
    fun `part2 should return xxx`() {
    }
}