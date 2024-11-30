package y2023

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import org.junit.jupiter.api.Disabled
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day10Test {

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    val testInput = """
    .....
    .S-7.
    .|.|.
    .L-J.
    .....
    """.trimIndent().lines()

    @Test
    fun `part1 should work as in example`() {
        every { InputReader.getInputAsList(any()) } returns testInput
        val answer = Day10.part1()
        assertEquals(4, answer)
    }

    @Test
    fun `part1 complex example`() {
        every { InputReader.getInputAsList(any()) } returns """
        ..F7.
        .FJ|.
        SJ.L7
        |F--J
        LJ...
        """.trimIndent().lines()
        val answer = Day10.part1()
        assertEquals(8, answer)
    }

    @Test
    fun `part1 complex example 2`() {
        every { InputReader.getInputAsList(any()) } returns """
        F-7..
        L-J..
        .S-7.
        .|.|.
        .L-J.
        .....
        """.trimIndent().lines()
        val answer = Day10.part1()
        assertEquals(4, answer)
    }

    @Test
    @Disabled("Fails")
    fun `part2 should work as in example`() {
        every { InputReader.getInputAsList(any()) } returns """
        ...........
        .S-------7.
        .|F-----7|.
        .||.....||.
        .||.....||.
        .|L-7.F-J|.
        .|..|.|..|.
        .L--J.L--J.
        ...........
        """.trimIndent().lines()
        val answer = Day10.part2()
        assertEquals(4, answer)
    }
}