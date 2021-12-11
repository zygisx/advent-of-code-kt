package y2021

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day10Test {

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }
    
    val testInput = """
    [({(<(())[]>[[{[]{<()<>>
    [(()[<>])]({[<{<<[]>>(
    {([(<{}[<>[]}>{[]{[(<()>
    (((({<>}<{<{<>}{[]{[]{}
    [[<[([]))<([[{}[[()]]]
    [{[{({}]{}}([{[{{{}}([]
    {<[[]]>}<{[{[{[]{()[[[]
    [<(<(<(<{}))><([]([]()
    <{([([[(<>()){}]>(<<{{
    <{([{{}}[<[[[<>{}]]]>[]]
    """.trimIndent().lines()

    @Test
    fun `part1 should return 26397`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day10.part1()
        assertEquals(26397, answer)
    }

    @Test
    fun `part2 should return 288957`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day10.part2()
        assertEquals(288957, answer)
    }
}