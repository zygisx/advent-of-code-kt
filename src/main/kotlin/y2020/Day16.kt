package y2020


object Day16 : Day {
    override fun day() = 16

    data class Rule(val category: String, val ranges: List<IntRange>)

    data class Notes(val rules: List<Rule>, val yourTicket: List<Int>, val nearby: List<List<Int>>)

    private fun parseNotes(str: String): Notes {
        val rules = str.lines()
                .takeWhile { it.isNotBlank() }
                .map { it.split(": ") }
                .map {
                    val ranges = it[1].split(" or ").map { r ->
                        val range = r.split("-").map { i -> i.toInt() }
                        range[0]..range[1]
                    }
                    Rule(it[0], ranges)
                }
        val yourTicket = str.lines()
                .dropWhile { it != "your ticket:" }
                .drop(1)
                .takeWhile { it.isNotBlank() }
                .flatMap {
                    println(it)
                    it.split(",").map { num -> num.toInt() } }
        val nearby = str.lines()
                .dropWhile { it != "nearby tickets:" }
                .drop(1)
                .takeWhile { it.isNotBlank() }
                .map { it.split(",").map { num -> num.toInt() } }
        return Notes(rules, yourTicket, nearby)
    }

    private fun getInput() = getInputAsString().let { parseNotes(it) }

    fun getInvalidTickets(notes: Notes): List<Int> {
        return notes.nearby.flatten().filterNot { nearby ->
            notes.rules.any { rule -> rule.ranges.any { range -> nearby in range } }
        }
    }

    fun part1(): Int {
        val notes = getInput()
        val invalid = getInvalidTickets(notes)
        return invalid.sum()
    }

    fun applicableRules(initialRules: List<Rule>, seat: Int): List<Rule> {
        return initialRules.filter { rule -> rule.ranges.any { range -> seat in range } }
    }

    fun assignRulesToSeat(rules: List<Rule>, allSeats: List<List<Int>>): Map<Int, Rule> {
        val assignedRules = mutableMapOf<Int, Rule>()
        val availableRules = rules.toMutableList()
        while (availableRules.isNotEmpty()) {
            allSeats.forEachIndexed { idx, seats ->
                if (idx in assignedRules.keys) return@forEachIndexed
                val rulesRemaining = seats.fold(availableRules.toList()) { remainingRules, seat ->
                    applicableRules(remainingRules, seat)
                }
                if (rulesRemaining.size == 1) {
                    val rule = rulesRemaining[0]
                    availableRules.remove(rule)
                    assignedRules[idx] = rule
                }
            }
        }
        return assignedRules
    }

    fun part2(): Long {
        val notes = getInput()
        val invalid = getInvalidTickets(notes)

        val nearbyByPosition = (notes.nearby[0].indices)
                .map { notes.nearby.map { nearby -> nearby[it] }}

        val seats = nearbyByPosition.map { it.filterNot { seat -> seat in invalid } }
        val assignedRules = assignRulesToSeat(notes.rules, seats)

        val departureRulesSeats = assignedRules
                .filter { (seat, rule) -> rule.category.startsWith("departure") }
                .keys

        val result = notes.yourTicket
                .filterIndexed { idx, _ -> idx in departureRulesSeats }
                .fold(1L) { acc, seat -> acc * seat}

        return result
    }
}

fun main() {
    println(Day16.part1())
    println(Day16.part2())
}