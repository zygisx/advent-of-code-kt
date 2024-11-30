package y2023

import misc.Collections.stack
import misc.splitWithPredicate
import kotlin.time.measureTimedValue

typealias Rating = Map<Char, Long>
typealias RatingRange = Map<Char, LongRange>

object Day19 : Day {
    override fun day() = 19

    data class Rule(val subject: Char, val condition: Char, val comparison: Long, val target: String)
    data class Workflow(val id: String, val rules: List<Rule>, val lastTarget: String)

    private fun parseRule(ruleStr: String): Rule {
        val (rule, target) = ruleStr.split(":")
        val id = rule[0]
        val op = rule[1]
        val comp = rule.substring(2..<rule.length).toLong()
        return Rule(id, op, comp, target)
    }

    private val WORKFLOW_REGEX = """(\w+)\{((\w+[<>]\d+:\w+,?)*)(\w+)\}""".toRegex()
    private fun getInput(): Pair<List<Workflow>, List<Rating>> {
        val (workflowLines, ratingLines) = getInputAsList().splitWithPredicate { it.isBlank() }.map { it.toList() }.toList()

        val workflows = workflowLines.map { line ->
            val groups = WORKFLOW_REGEX.matchEntire(line)!!.groupValues
            val rules = groups[2].split(",").filter { it.isNotBlank() }.map { parseRule(it) }
            Workflow(id = groups[1], rules = rules, lastTarget = groups.last())
        }
        val ratings = ratingLines
            .map { line ->
                line.split(",").map { it.replace("""[xmas={}]""".toRegex(), "") }.map { it.toLong() }
            }
            .map { mapOf('x' to it[0], 'm' to it[1], 'a' to it[2], 's' to it[3]) }

        return workflows to ratings

    }

    private val finalTarget = setOf("A", "R")
    private fun isAccepted(rating: Rating, workflowsById: Map<String, Workflow>, start: String): Boolean {
        var target = "in"
        while (target !in finalTarget) {
            val workflow = workflowsById[target]!!

            target = workflow.rules.firstOrNull {
                when (it.condition) {
                    '>' -> rating[it.subject]!! > it.comparison
                    '<' -> rating[it.subject]!! < it.comparison
                    else -> error("unreachable")
                }
            }?.target ?: workflow.lastTarget
        }

        return target == "A"
    }

    // First FALSE second TRUE
    private fun LongRange.splitByRule(rule: Rule): List<LongRange> {
        return if (rule.comparison in this) {
            when (rule.condition) {
                '>' -> listOf(this.first..rule.comparison, rule.comparison+1..this.last)
                '<' -> listOf(rule.comparison..this.last, this.first..<rule.comparison)
                else -> error("unreachable")
            }
        } else listOf(this)
    }

    private fun RatingRange.withRule(rule: Rule): List<RatingRange> {
        return this[rule.subject]!!
            .splitByRule(rule)
            .map { this + (rule.subject to it) }
    }

    private fun RatingRange.sumAllInRanges(): Long {
        return this.map { it.value.last - it.value.first + 1 }.reduce { a, b -> a*b }
    }

    private fun findAccepted(
        startingRatings: Map<Char, LongRange>,
        workflowsById: Map<String, Workflow>
    ): Long {
        val stack = stack(listOf("in" to startingRatings))
        var sum = 0L
        while (stack.isNotEmpty()) {
            val (target, ratings) = stack.pop()
            if (ratings.values.all { it.isEmpty() }) continue
            when (target) {
                "A" -> sum += ratings.sumAllInRanges()
                "R" -> continue
                else -> {
                    val workflow = workflowsById[target]!!
                    val remainingRatings = workflow.rules.fold(ratings) { acc, rule ->
                        val newRatings = acc.withRule(rule)
                        if (newRatings.size > 1) {
                            stack.push(rule.target to newRatings[1])
                        }
                        newRatings.first()
                    }
                    stack.push(workflow.lastTarget to remainingRatings)
                }
            }
        }
        return sum
    }

    fun part1(): Long {
        val (workflows, ratings) = getInput()
        val workflowsById = workflows.associateBy { it.id }
        return ratings
            .filter { isAccepted(it, workflowsById, workflows.first().id) }
            .sumOf { it.values.sum() }
    }

    fun part2(): Long {
        val (workflows, _) = getInput()
        val workflowsById = workflows.associateBy { it.id }
        val fullRange = 1L..4000L
        val startingRatings = mapOf(
            'x' to fullRange,
            'm' to fullRange,
            'a' to fullRange,
            's' to fullRange,
        )

        return findAccepted(startingRatings, workflowsById)
    }
}

fun main() {
    val (part1Answer, part1Duration) = measureTimedValue { Day19.part1() }
    println("$part1Answer in ${part1Duration.inWholeMilliseconds} ms")

    val (part2Answer, part2Duration) = measureTimedValue { Day19.part2() }
    println("$part2Answer in ${part2Duration.inWholeMilliseconds} ms")
}