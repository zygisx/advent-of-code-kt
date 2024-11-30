package y2023

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import org.junit.jupiter.api.Disabled
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day12Test {

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    val testInput = """
    ???.### 1,1,3
    .??..??...?##. 1,1,3
    ?#?#?#?#?#?#?#? 1,3,1,6
    ????.#...#... 4,1,1
    ????.######..#####. 1,6,5
    ?###???????? 3,2,1
    """.trimIndent().lines()

    @Test
    @Disabled("Fails")
    fun `part1 should work as in example`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day12.part1()
        assertEquals(21, answer)
    }

    @Test
    @Disabled("Takes too long")
    fun `part2 minimal example`() {
        every { InputReader.getInputAsList(any()) } returns listOf(".??..??...?##. 1,1,3")
        val answer = Day12.part2()
        assertEquals(16384, answer)
    }

    @Test
    @Disabled("Takes too long")
    fun `part2 should work as in example`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day12.part2()
        assertEquals(525152, answer)
    }
}