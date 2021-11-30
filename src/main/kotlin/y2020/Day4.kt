package y2020

enum class Field {
    BIRTH_YEAR,
    ISSUE_YEAR,
    EXPIRATION_YEAR,
    HEIGHT,
    HAIR_COLOR,
    EYE_COLOR,
    PASSPORT_ID,
    COUNTRY_ID;


    companion object {
        val all = values().toSet()

        fun fromString(str: String): Field {
            return when (str) {
                "byr" -> BIRTH_YEAR
                "iyr" -> ISSUE_YEAR
                "eyr" -> EXPIRATION_YEAR
                "hgt" -> HEIGHT
                "hcl" -> HAIR_COLOR
                "ecl" -> EYE_COLOR
                "pid" -> PASSPORT_ID
                "cid" -> COUNTRY_ID
                else -> throw IllegalArgumentException("Unknown passport field: $str")
            }
        }
    }
}

typealias Passport = Map<Field, String>


class Day4 : Day {
    override fun day() = 4

    private fun getInput() = getInputAsString()

    private val anyWhitespace = Regex("""\s+""")

    private fun parsePassports(input: String): List<Passport> {
        val rawPassports = input.split("\n\n")
        return rawPassports.map { rawPassport ->
            rawPassport.split(anyWhitespace)
                .filter { it.isNotBlank() }
                .map { field ->
                    val parts = field.split(":")
                    Field.fromString(parts[0]) to parts[1]
                }.toMap()
        }
    }

    private fun isPassportValid(passport: Passport): Boolean {
        val missing = Field.all - passport.keys
        return missing.isEmpty() || (missing.size == 1 && Field.COUNTRY_ID in missing)
    }

    private fun yearValidation(value: String, range: IntRange) = try {
        value.toInt() in range
    } catch (e: Exception) {
        false
    }

    private fun birthdayValidation(value: String) = yearValidation(value, 1920..2002)
    private fun issueYearValidation(value: String) = yearValidation(value, 2010..2020)
    private fun expirationYearValidation(value: String) = yearValidation(value, 2020..2030)

    private val heightRegex = Regex("""(\d+)(in|cm)""")
    private fun heightValidation(value: String): Boolean {
        val match = heightRegex.matchEntire(value)
        val groups = match?.groups ?: return false
        if (groups.size < 3) return false
        if (groups[2]!!.value == "in") {
            return groups[1]!!.value.toInt() in 59..76
        } else if (groups[2]!!.value == "cm") {
            return groups[1]!!.value.toInt() in 150..193
        } else {
            return false
        }
    }

    private val hairColorRegex = Regex("""#(\d|a|b|c|d|e|f){6}""")
    private fun hairColorValidation(value: String) = hairColorRegex.matches(value)

    private fun eyeColorValidation(value: String) = value in setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")

    private val passportIdRegex = Regex("""\d{9}""")
    private fun passportIdValidation(value: String) = passportIdRegex.matches(value)

    private fun isEachFieldValid(passport: Passport): Boolean {
        return birthdayValidation(passport[Field.BIRTH_YEAR]!!)
                && issueYearValidation(passport[Field.ISSUE_YEAR]!!)
                && expirationYearValidation(passport[Field.EXPIRATION_YEAR]!!)
                && heightValidation(passport[Field.HEIGHT]!!)
                && hairColorValidation(passport[Field.HAIR_COLOR]!!)
                && eyeColorValidation(passport[Field.EYE_COLOR]!!)
                && passportIdValidation(passport[Field.PASSPORT_ID]!!)
    }


    fun part1(): Int {
        val input = getInput()
        val passports = parsePassports(input)

        return passports.count { isPassportValid(it) }
    }

    fun part2(): Int {
        val input = getInput()
        val passports = parsePassports(input)

        return passports.count { isPassportValid(it) && isEachFieldValid(it) }
    }
}

fun main() {
    val day4 = Day4()
    println(day4.part1())
    println(day4.part2())
}