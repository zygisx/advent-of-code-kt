package y2023

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import java.math.BigInteger
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day8Test {

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    val testInput = """
    RL

    AAA = (BBB, CCC)
    BBB = (DDD, EEE)
    CCC = (ZZZ, GGG)
    DDD = (DDD, DDD)
    EEE = (EEE, EEE)
    GGG = (GGG, GGG)
    ZZZ = (ZZZ, ZZZ)
    """.trimIndent().lines()

    @Test
    fun `part1 should work as in example`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day8.part1()
        assertEquals(2, answer)
    }


    @Test
    fun `part1 example 2`() {
        val testInput2 = """
        LLR

        AAA = (BBB, BBB)
        BBB = (AAA, ZZZ)
        ZZZ = (ZZZ, ZZZ)
        """.trimIndent().lines()
        every { InputReader.getInputAsList(any()) } returns testInput2
        val answer = Day8.part1()
        assertEquals(6, answer)
    }

    @Test
    fun `part2 should work as in example`() {
        val part2TestInput = """
            LR

            11A = (11B, XXX)
            11B = (XXX, 11Z)
            11Z = (11B, XXX)
            22A = (22B, XXX)
            22B = (22C, 22C)
            22C = (22Z, 22Z)
            22Z = (22B, 22B)
            XXX = (XXX, XXX)
        """.trimIndent().lines()
        every { InputReader.getInputAsList(any()) } returns part2TestInput
        val answer = Day8.part2()
        assertEquals(BigInteger.valueOf(6), answer)
    }
}