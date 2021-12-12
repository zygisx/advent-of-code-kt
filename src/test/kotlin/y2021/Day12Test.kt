package y2021

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day12Test {

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    val testInput = """
    dc-end
    HN-start
    start-kj
    dc-start
    dc-HN
    LN-dc
    HN-end
    kj-sa
    kj-HN
    kj-dc
    """.trimIndent().lines()

    @Test
    fun `part1 simple example should return 10`() {
        every { InputReader.getInputAsList(any()) } returns
        """
            start-A
            start-b
            A-c
            A-b
            b-d
            A-end
            b-end
        """.trimIndent().lines()
        val answer = Day12.part1()
        assertEquals(10, answer)
    }

    @Test
    fun `part1 should return 19`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day12.part1()
        assertEquals(19, answer)
    }

    @Test
    fun `part2 simple example should return 36`() {
        every { InputReader.getInputAsList(any()) } returns
        """
            start-A
            start-b
            A-c
            A-b
            b-d
            A-end
            b-end
        """.trimIndent().lines()
        val answer = Day12.part2()
        assertEquals(36, answer)
    }


    @Test
    fun `part2 should return 103`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day12.part2()
        assertEquals(103, answer)
    }
}