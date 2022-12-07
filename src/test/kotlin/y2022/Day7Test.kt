package y2022

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day7Test {

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    val testInput = """
    ${'$'} cd /
    ${'$'} ls
    dir a
    14848514 b.txt
    8504156 c.dat
    dir d
    ${'$'} cd a
    ${'$'} ls
    dir e
    29116 f
    2557 g
    62596 h.lst
    ${'$'} cd e
    ${'$'} ls
    584 i
    ${'$'} cd ..
    ${'$'} cd ..
    ${'$'} cd d
    ${'$'} ls
    4060174 j
    8033020 d.log
    5626152 d.ext
    7214296 k
    """.trimIndent().lines()

    @Test
    fun `part1 should return 95437`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day7.part1()
        assertEquals(95437L, answer)
    }

    @Test
    fun `part2 should return 24933642`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day7.part2()
        assertEquals(24933642, answer)
    }
}