package y2023

import misc.splitToLongs


object Day9 : Day {
    override fun day() = 9

    private fun getInput() = getInputAsList().map {
        it.splitToLongs()
    }

    private fun findSensors(sensor: List<Long>): List<List<Long>> {
        val sensorsList = mutableListOf(sensor)
        while (!sensorsList.last().all { it == 0L }) {
            sensorsList.add(sensorsList.last().windowed(2).map { it[1] - it[0] })
        }
        return sensorsList
    }

    private fun predictSensors(
        sensorsLists: List<List<Long>>,
        mappingFn: (last: Long, sensors: List<Long>) -> Long
    ): Long {
        return sensorsLists.reversed().drop(1).fold(0, mappingFn)
    }

    fun part1(): Long {
        val sensors = getInput()
        return sensors
            .map { findSensors(it) }
            .sumOf { predictSensors(it) { last, sensors -> last + sensors.last()} }
    }

    fun part2(): Long {
        val sensors = getInput()
        return sensors
            .map { findSensors(it) }
            .sumOf { predictSensors(it) { last, sensors -> sensors.first() - last} }
    }
}

fun main() {
    println(Day9.part1())
    println(Day9.part2())
}