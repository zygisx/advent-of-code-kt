package y2020

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day21Test {

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    val testInput = """
    mxmxvkd kfcds sqjhc nhms (contains dairy, fish)
    trh fvjkl sbzzf mxmxvkd (contains dairy)
    sqjhc fvjkl (contains soy)
    sqjhc mxmxvkd sbzzf (contains fish)
    """.trimIndent().lines()

    @Test
    fun `part1 should return xxx`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day21.part1()
        assertEquals(5, answer)
    }

    @Test
    fun `part2 should return mxmxvkd,sqjhc,fvjkl`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day21.part2()
        assertEquals("mxmxvkd,sqjhc,fvjkl", answer)
    }
}