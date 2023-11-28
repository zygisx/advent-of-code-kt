package y2022


import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day18Test {

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    val testInput = """
    2,2,2
    1,2,2
    3,2,2
    2,1,2
    2,3,2
    2,2,1
    2,2,3
    2,2,4
    2,2,6
    1,2,5
    3,2,5
    2,1,5
    2,3,5
    """.trimIndent().lines()

    @Test
    fun `part1 small example`() {
        every { InputReader.getInputAsList(any()) } returns listOf("1,1,1", "2,1,1")
        val answer = Day18.part1()
        assertEquals(10, answer)
    }


    @Test
    fun `part1 should work as in example`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day18.part1()
        assertEquals(64, answer)
    }
}