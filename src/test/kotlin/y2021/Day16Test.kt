package y2021

import io.mockk.every
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
    1
    """.trimIndent().lines()

    @Test
    fun `part1 simplest`() {
        every { InputReader.getInputAsString(any()) } returns "D2FE28"
        val answer = Day16.part1()
        assertEquals(6, answer)
    }

    @Test
    fun `part1 operator with id 1 `() {
        every { InputReader.getInputAsString(any()) } returns "38006F45291200"
        val answer = Day16.part1()
        assertEquals(9, answer)
    }

    @Test
    fun `part1 should return 16`() {
        every { InputReader.getInputAsString(any()) } returns "8A004A801A8002F478"
        val answer = Day16.part1()
        assertEquals(16, answer)
    }

    @Test
    fun `part1 should return 31`() {
        every { InputReader.getInputAsString(any()) } returns "A0016C880162017C3686B18A3D4780"
        val answer = Day16.part1()
        assertEquals(31, answer)
    }

    @Test
    fun `part2 simple should return 3`() {
        every { InputReader.getInputAsString(any()) } returns "C200B40A82"
        val answer = Day16.part2()
        assertEquals(3, answer)
    }

    @Test
    fun `part2 should return 1`() {
        every { InputReader.getInputAsString(any()) } returns "9C0141080250320F1802104A08"
        val answer = Day16.part2()
        assertEquals(1, answer)
    }
}