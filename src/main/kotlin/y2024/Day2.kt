package y2024

import misc.splitToInts

object Day2 : Day {
    override fun day() = 2

    private fun getInput() = getInputAsList()
        .map { it.splitToInts() }

    fun part1(): Int {
        val reports = getInput()
        return reports.count { getFailedIndex(it) == null }
    }

    private fun getFailedIndex(report: List<Int>): Int? {
        if (report[0] == report[1]) {
            return 0
        }
        val isInc = report[0] < report[1]
        val okRange = if (isInc) -3..-1 else 1..3
        return report.withIndex().windowed(2)
            .firstOrNull { it[0].value - it[1].value !in okRange }
            ?.get(0)?.index
    }

    private fun testWithRemovedAt(report: List<Int>, idx: Int): Boolean {
        val newReport = report.toMutableList()
        newReport.removeAt(idx)
        return getFailedIndex(newReport) == null
    }

    fun part2(): Int {
        val reports = getInput()
        var okCount = 0
        for (report in reports) {
            val failedIndex = getFailedIndex(report)
            if (failedIndex == null) {
                okCount++
                continue
            }
            if (testWithRemovedAt(report, 0)) {
                okCount++
                continue
            }
            if (testWithRemovedAt(report, 1)) {
                okCount++
                continue
            }
            if (testWithRemovedAt(report, failedIndex)) {
                okCount++
                continue
            }
            if (testWithRemovedAt(report, failedIndex+1)) {
                okCount++
                continue
            }
        }
        return okCount
    }
}

fun main() {
    println(Day2.part1())
    println(Day2.part2())
}

// 509 -
// 526 - too low