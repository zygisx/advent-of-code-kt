package y2018

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day4Test {

    val day4 = Day4()

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    val TestInput = """
        [1518-11-01 00:00] Guard #10 begins shift
        [1518-11-01 00:05] falls asleep
        [1518-11-01 00:25] wakes up
        [1518-11-01 00:30] falls asleep
        [1518-11-01 00:55] wakes up
        [1518-11-01 23:58] Guard #99 begins shift
        [1518-11-02 00:40] falls asleep
        [1518-11-02 00:50] wakes up
        [1518-11-03 00:05] Guard #10 begins shift
        [1518-11-03 00:24] falls asleep
        [1518-11-03 00:29] wakes up
        [1518-11-04 00:02] Guard #99 begins shift
        [1518-11-04 00:36] falls asleep
        [1518-11-04 00:46] wakes up
        [1518-11-05 00:03] Guard #99 begins shift
        [1518-11-05 00:45] falls asleep
        [1518-11-05 00:55] wakes up
    """.trimIndent().lines()

    @Test
    fun `part1 should return 240`() {
        every { InputReader.getInputAsList(any()) } returns TestInput
        val answer = day4.part1()
        assertEquals(240, answer)
    }

    @Test
    fun `part2 should return 3`() {
        every { InputReader.getInputAsList(any()) } returns TestInput
        val answer = day4.part2()
        assertEquals(4455, answer)
    }
}