package y2020

import misc.Collections.queue

data class Bag(val count: Int, val color: String)
data class Rule(val color: String, val bags: List<Bag>) {
    companion object {
        private val rootBagRegex = Regex("""(\w+ \w+) bags """)
        private val innerBagRegex = Regex("""(\d+) (\w+ \w+) bags?""")
        fun parse(ruleStr: String): Rule {
            val parts = ruleStr.split("contain")
            val rootBag = parts[0]
            val innerBags = parts[1]

            val rootColor = rootBagRegex.matchEntire(rootBag)!!.groups[1]!!.value

            if (innerBags.contains("no other bags.")) {
                return Rule(rootColor, listOf())
            }

            val bags = innerBagRegex.findAll(innerBags).map {
                Bag(it.groups[1]!!.value.toInt(), it.groups[2]!!.value)
            }.toList()
            return Rule(rootColor, bags)
        }
    }
}

class Day7 : Day {
    override fun day() = 7

    private fun parseInput() = getInputAsList().map { Rule.parse(it) }

    private fun findBags(rulesMap : Map<String, Set<String>>): Collection<String> {
        val initial = setOf("shiny gold")
        val visited = mutableSetOf<String>()
        val queue = queue(initial)

        while (queue.isNotEmpty()) {
            val bag = queue.poll()
            if (bag in visited) {
                continue
            }
            visited.add(bag)
            rulesMap
                .filter { bag in it.value }
                .filter { it.key !in visited }
                .map { it.key }
                .forEach {
                    queue.add(it)
                }
        }
        return visited - initial
    }

    private fun makeRulesMap(rules: List<Rule>) = rules
        .map { it.color to it.bags.map { b -> b.color }.toSet() }
        .toMap()


    fun part1(): Int {
        val rules = parseInput()
        val rulesMap = makeRulesMap(rules)

        val bags = findBags(rulesMap)

        return bags.size
    }

    fun part2(): Int {
        val rules = parseInput()

        fun countBags(colorToLook: String): Int {
            val rule = rules.first { it.color == colorToLook }
            return rule.bags.sumOf { it.count + it.count * countBags(it.color) }
        }

        return countBags("shiny gold")
    }
}

fun main() {
    val day7 = Day7()

    println(day7.part1())
    println(day7.part2())
}