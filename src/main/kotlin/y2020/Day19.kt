package y2020

import misc.InputReader


object Day19 : Day {


    sealed class Either {
        data class Rule(val rules: List<List<Int>>): Either()
        data class Value(val value: String): Either()
    }

    data class Input(val rules: Map<Int, Either>, val messages: List<String>)

    override fun day() = 19

    private fun getInput() = getInputAsList().let { parseRules(it) }

    private fun parseRules(lines: List<String>): Input {
        val allRules = lines.takeWhile { it.isNotBlank() }
            .map {
                val parts = it.split(":")
                val ruleNumber = parts[0].toInt()
                val rule = parts[1].trim()
                if (rule.startsWith("\"")) {
                    val value = rule.replace("\"", "")
                    ruleNumber to Either.Value(value)
                } else {
                    val rules = rule.split("|").map { r ->
                        r.trim().split(" ").map { rid -> rid.toInt() }
                    }
                    ruleNumber to Either.Rule(rules)
                }
            }.toMap()
        val messages = lines.dropWhile { it.isNotBlank() }.drop(1)
        return Input(allRules, messages)
    }

    fun findMatchingValues(allRules: Map<Int, Either>, ruleId: Int, cache: MutableMap<Int, List<List<String>>>): List<List<String>> {
        val matchingRule = allRules[ruleId]!!
        return when (matchingRule) {
            is Either.Value -> listOf(listOf(matchingRule.value))
            is Either.Rule -> {
                matchingRule.rules.map { rulesList ->
                    val matches = rulesList.map { rule ->
                        if (rule in cache) cache[rule]!!
                        else findMatchingValues(allRules, rule, cache).also { cache[rule] = it }
                    }
                    matches.drop(0).fold(listOf("")) { acc, match ->
                        match.flatMap { ma -> ma.flatMap { m -> acc.map { a -> a + m } } }
                    }
                }
            }
        }

    }

    fun part1(): Int {
        val input = getInput()
        val allPossibilities = findMatchingValues(input.rules, 0, mutableMapOf())
        val count = input.messages.count { msg ->
            allPossibilities.any { pos ->
                pos.contains(msg)
            }
        }
        return count
    }

    fun rule8(msg: String, res42: List<String>): Boolean {
        fun isMatchRec(mes: String): Boolean {
            val itsAMatch = res42.contains(mes)
            if (itsAMatch) return true
            val thoseThatStarts = res42.filter { pos -> mes.startsWith(pos) }
            return thoseThatStarts.map { mes.removePrefix(it) }.any { isMatchRec(it) }
        }

        return isMatchRec(msg)
    }

    fun rule11(msg: String, res42: List<String>, res31: List<String>): Boolean {
        val allCombos = res42.flatMap { x -> res31.map { y -> x + y } }

        fun isMatchRec(mes: String): Boolean {
            val itsAMatch = allCombos.contains(mes)
            if (itsAMatch) return true
            val thoseThatStarts = res42.filter { pos -> mes.startsWith(pos) }
            val thoseThatEnds = res31.filter { pos -> mes.endsWith(pos) }
            val allCombinations = thoseThatStarts.flatMap { x -> thoseThatEnds.map { y -> x to y } }
            return allCombinations.map { mes.removePrefix(it.first).removeSuffix(it.second) }.any { isMatchRec(it) }
        }

        return isMatchRec(msg)
    }

    fun part2(): Int {
        val input = getInput()
        //8: 42 | 42 8
        //11: 42 31 | 42 11 31
        val changesRules = input.rules.toMutableMap()
        changesRules[8] = Either.Rule(listOf(listOf(42), listOf(42, 8)))
        changesRules[11] = Either.Rule(listOf(listOf(42, 31), listOf(42, 11, 31)))

        val res42 = findMatchingValues(input.rules, 42, mutableMapOf()).flatten()
        val res31 = findMatchingValues(input.rules, 31, mutableMapOf()).flatten()

        return input.messages.count { msg ->
            // very brutal...
            (2 until msg.length).any {
                val part1 = msg.substring(0 until it)
                val part2 = msg.substring(it until msg.length)
                rule8(part1, res42) && rule11(part2, res42, res31)
            }
        }
    }
}

fun main() {
    println(Day19.part1())
    println(Day19.part2())
}