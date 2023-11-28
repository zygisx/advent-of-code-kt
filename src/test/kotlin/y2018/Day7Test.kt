package y2018

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day7Test {

    val day7 = Day7()

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    val testInput = """
        Step C must be finished before step A can begin.
        Step C must be finished before step F can begin.
        Step A must be finished before step B can begin.
        Step A must be finished before step D can begin.
        Step B must be finished before step E can begin.
        Step D must be finished before step E can begin.
        Step F must be finished before step E can begin.
    """.trimIndent().lines()

    @Test
    fun `part1 should return CABDFE`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = day7.part1()
        assertEquals("CABDFE", answer)
    }

    @Test
    fun `part2 should return xxx`() {
    }
}