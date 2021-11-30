package y2020

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import misc.InputReader
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Day4Test {

    val day4 = Day4()

    @BeforeTest
    fun setUp() {
        mockkObject(InputReader)
    }

    val testInput = """
    ecl:gry pid:860033327 eyr:2020 hcl:#fffffd
    byr:1937 iyr:2017 cid:147 hgt:183cm

    iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884
    hcl:#cfa07d byr:1929

    hcl:#ae17e1 iyr:2013
    eyr:2024
    ecl:brn pid:760753108 byr:1931
    hgt:179cm

    hcl:#cfa07d eyr:2025 pid:166559648
    iyr:2011 ecl:brn hgt:59in
    """.trimIndent()

    @Test
    fun `part1 should return 2`() {
        every { InputReader.getInputAsString(any()) } returns testInput
        val answer = day4.part1()
        assertEquals(2, answer)
    }

    @Test
    fun `part2 should return ???`() {
//        every { InputReader.getInputAsString(any()) } returns testInput
//        val answer = day4.part2()
//        assertEquals(3, answer)
    }
}