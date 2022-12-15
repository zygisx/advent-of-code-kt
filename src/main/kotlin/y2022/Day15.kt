package y2022

import misc.Point


object Day15 : Day {
    override fun day() = 15

    private fun getInput() = getInputAsList()

    data class Sensor(val sensor: Point, val beacon: Point) {
        val distance = sensor.manhattanDistance(beacon)
    }

    private val sensorRegex = """^Sensor at x=(-?\d+), y=(-?\d+): closest beacon is at x=(-?\d+), y=(-?\d+)${'$'}""".toRegex()
    private fun parseSensor(line: String): Sensor {
        val match = sensorRegex.matchEntire(line)
        val groups = match?.groups ?: error("regex not match $line")
        val point = Point(groups[1]!!.value.toInt(), groups[2]!!.value.toInt(), )
        val sensor = Point(groups[3]!!.value.toInt(), groups[4]!!.value.toInt(), )
        return Sensor(point, sensor)
    }

    fun part1(line: Int): Long {
        val sensors = getInput().map { parseSensor(it) }

        val minX = sensors.flatMap { listOf(it.beacon.x, it.sensor.x - it.distance) }.min()
        val maxX = sensors.flatMap { listOf(it.beacon.x, it.sensor.x + it.distance) }.max()

        val allBeacons = sensors.map { it.beacon }.toSet()
        val candidates = (minX .. maxX)
            .map { Point(it, line) }
            .mapNotNull { point ->
                val closerSensor = sensors.firstOrNull { it.sensor.manhattanDistance(point) <= it.distance }
                if (closerSensor != null) point else null
            }
            .filterNot { it in allBeacons }
        return candidates.count().toLong()
    }

    fun checkPointNotDetectable(sensors: List<Sensor>, point: Point): Boolean {
        return sensors.all {
            val distance = it.sensor.manhattanDistance(point)
            distance > it.distance
        }
    }

    fun sensorSignaldiagonals(sensor: Sensor): List<Point> {
        val d1 = (sensor.sensor.x + sensor.distance downTo sensor.sensor.x).flatMapIndexed { index, x ->
            listOf(Point(x, sensor.sensor.y - index), Point(x, sensor.sensor.y + index))
        }
        val d2 = (sensor.sensor.x - sensor.distance ..  sensor.sensor.x).flatMapIndexed { index, x ->
            listOf(Point(x, sensor.sensor.y + index), Point(x,  sensor.sensor.y - index))
        }
        return d1 + d2
    }

    fun part2(maxCoordinate: Int): Long {
        val sensors = getInput().map { parseSensor(it) }
        val coordinateRange = (0..maxCoordinate)
        val nonDetectablePoint = sensors.firstNotNullOf {
            sensorSignaldiagonals(it)
                .flatMap { it.neighbours().toList() }
                .filter { it.x in coordinateRange && it.y in coordinateRange }
                .firstOrNull { checkPointNotDetectable(sensors, it) }
        }
        return nonDetectablePoint.x * 4000000L + nonDetectablePoint.y
    }
}

fun main() {
    println(Day15.part1(2000000))
    println(Day15.part2(4000000))
}