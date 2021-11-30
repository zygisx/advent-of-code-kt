package y2020

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
    light red bags contain 1 bright white bag, 2 muted yellow bags.
    dark orange bags contain 3 bright white bags, 4 muted yellow bags.
    bright white bags contain 1 shiny gold bag.
    muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
    shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
    dark olive bags contain 3 faded blue bags, 4 dotted black bags.
    vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
    faded blue bags contain no other bags.
    dotted black bags contain no other bags.
    """.trimIndent().lines()
// 3 + 1 +2 + 9 +1 + 2 +3 + 4
    // 1 + 7 * (3 + (3 * 0) + 4 + (4 * 0) ) + 2 + 2 *
    @Test
    fun `part1 should return 4`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = day7.part1()
        assertEquals(4, answer)
    }

    @Test
    fun `part2 should return 32`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = day7.part2()
        assertEquals(32, answer)
    }

    val testInputComplex = """
    shiny gold bags contain 2 dark red bags.
    dark red bags contain 2 dark orange bags.
    dark orange bags contain 2 dark yellow bags.
    dark yellow bags contain 2 dark green bags.
    dark green bags contain 2 dark blue bags.
    dark blue bags contain 2 dark violet bags.
    dark violet bags contain no other bags.
    """.trimIndent().lines()

    @Test
    fun `part2 should return 126`() {
        every { InputReader.getInputAsList(any()) } returns testInputComplex
        val answer = day7.part2()
        assertEquals(126, answer)
    }
}