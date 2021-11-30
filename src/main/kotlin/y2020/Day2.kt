package y2020


data class Policy(val min: Int, val max: Int, val symbol: Char)

data class Password(val policy: Policy, val pass: String) {
    companion object {
        private val regex = Regex("""(\d+)-(\d+) (\w): (\w+)""")
        fun parse(str: String): Password {
            val match = regex.matchEntire(str)
            val groups = match?.groups ?: throw IllegalArgumentException()
            val policy = Policy(groups[1]!!.value.toInt(), groups[2]!!.value.toInt(), groups[3]!!.value.first())
            return Password(policy, groups[4]!!.value)
        }
    }
}

class Day2 : Day {
    override fun day() = 2

    private fun getInput() = getInputAsList().map { Password.parse(it) }

    private fun isValidSledCompany(password: Password): Boolean {
        val matches = password.pass.count { it == password.policy.symbol }
        return matches in password.policy.min..password.policy.max
    }

    private fun isValidTobogganCompany(password: Password): Boolean {
        val first = password.pass[password.policy.min - 1]
        val second = password.pass[password.policy.max - 1]
        return listOf(first, second).count { password.policy.symbol == it } == 1
    }

    fun part1(): Int {
        val passwords = getInput()
        return passwords.count { isValidSledCompany(it) }
    }

    fun part2(): Int {
        val passwords = getInput()
        return passwords.count { isValidTobogganCompany(it) }
    }
}

fun main() {
    val day2 = Day2()

    println(day2.part1())
    println(day2.part2())
}