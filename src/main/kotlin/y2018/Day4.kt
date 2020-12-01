package y2018

import misc.Counter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

typealias Minute = Int;

class Day4 : Day {
    override fun day() = 4

    private fun getInput() = getInputAsList()

    enum class Action {
        BEGIN_SHIFT,
        FALLS_ASLEEP,
        WAKES_UP
    }

    data class Entry(val date: String, val minute: Int, val action: Action, val id: Int?) {
        companion object {
            private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

            private val beginShiftRegex = Regex("""\[(\d{4}-\d{2}-\d{2}) (\d{2}):(\d{2})\] Guard #(\d+) begins shift""")
            private val fallAsleepRegex = Regex("""\[(\d{4}-\d{2}-\d{2}) (\d{2}):(\d{2})\] falls asleep""")
            private val wakesUpRegex = Regex("""\[(\d{4}-\d{2}-\d{2}) (\d{2}):(\d{2})\] wakes up""")

            private fun extractShiftDate(date: String, hour: String): String {
                if (hour.toInt() == 0) {
                    return date
                }
                val nextDay = LocalDate.parse(date, formatter).plusDays(1)
                return nextDay.format(formatter)
            }

            fun parse(str: String): Entry {
                when {
                    str.matches(beginShiftRegex) -> {
                        val match = beginShiftRegex.matchEntire(str)
                        val groups = match!!.groups
                        val date = groups[1]!!.value
                        val hour = groups[2]!!.value
                        val newDate = extractShiftDate(date, hour)
                        return Entry(newDate, 0, Action.BEGIN_SHIFT, groups[4]!!.value.toInt())
                    }
                    str.matches(fallAsleepRegex) -> {
                        val match = fallAsleepRegex.matchEntire(str)
                        val groups = match!!.groups
                        return Entry(groups[1]!!.value, groups[3]!!.value.toInt(), Action.FALLS_ASLEEP, null)
                    }
                    str.matches(wakesUpRegex) -> {
                        val match = wakesUpRegex.matchEntire(str)
                        val groups = match!!.groups
                        return Entry(groups[1]!!.value, groups[3]!!.value.toInt(), Action.WAKES_UP, null)
                    }
                    else -> throw IllegalArgumentException("Cannot parse $str")
                }
            }
        }
    }

    private fun extractId(entries: List<Entry>): Int {
        return entries.first { it.action == Action.BEGIN_SHIFT }.id!!
    }

    // returns map minute -> total slept that minute
    private fun guardSleepLog(guardLogs: List<List<Entry>>): Counter<Minute> {
        val sleepCounter = Counter<Minute>()

        for (entries in guardLogs) {
            val sleepActions = entries
                    .filter { it.action != Action.BEGIN_SHIFT }
                    .sortedBy { it.minute }
            var isSleeping = false
            var fallAsleepAt = -1
            for (action in sleepActions) {
                if (action.action == Action.FALLS_ASLEEP) {
                    isSleeping = true
                    fallAsleepAt = action.minute
                }
                if (action.action == Action.WAKES_UP && isSleeping) {
                    isSleeping = false
                    (fallAsleepAt..action.minute).forEach {
                        sleepCounter.inc(it)
                    }
                }
            }
        }
        return sleepCounter
    }

    private fun minutesSlept(entries: List<Entry>): Int {
        return guardSleepLog(listOf(entries)).getMap().values.sum()
    }

    data class SleepiestMinute(val minute: Int, val sleptTotal: Int)

    private fun sleepiestMinute(guardLogs: List<List<Entry>>): SleepiestMinute {
        val sleepMap = guardSleepLog(guardLogs)
        val sleepiestMinuteEntry = sleepMap.max() ?: return SleepiestMinute(0, 0)
        return SleepiestMinute(sleepiestMinuteEntry.first, sleepiestMinuteEntry.second)
    }

    fun part1(): Int {
        val input = getInput()
        val groups = input
                .map { Entry.parse(it) }
                .groupBy { it.date }

        val sleepCounter =  groups
                .map { Pair(extractId(it.value), minutesSlept(it.value)) }
                .groupBy { it.first }
                .mapValues { it.value.map { p -> p.second }.sum() }

        val laziestGuard = sleepCounter.maxByOrNull { it.value }!!.key
        val lazyGuardEntries = groups.filterValues { extractId(it) == laziestGuard }.values.toList()
        val laziestMinute = sleepiestMinute(lazyGuardEntries).minute

        return laziestGuard * laziestMinute
    }

    fun part2(): Int {
        val input = getInput()
        val groups = input
                .map { Entry.parse(it) }
                .groupBy { it.date }
        val guardGroups =  groups.values
                .groupBy { extractId(it) }

        val sleepiestMinuteEntry = guardGroups
                .mapValues { sleepiestMinute(it.value) }
                .maxByOrNull { it.value.sleptTotal }!!
        return sleepiestMinuteEntry.key * sleepiestMinuteEntry.value.minute
    }
}


fun main() {
    val day4 = Day4()

    println(day4.part1())
    println(day4.part2())
}