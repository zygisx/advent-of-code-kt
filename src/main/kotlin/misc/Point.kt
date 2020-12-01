package misc

import kotlin.math.abs

typealias Points = Iterable<Point>

data class Point(val x: Int, val y: Int) {
    fun north() = Point(x, y + 1)
    fun south() = Point(x, y - 1)
    fun east() = Point(x + 1, y)
    fun west() = Point(x - 1, y)

    fun neighbours() = sequenceOf(north(), south(), east(), west())
    fun manhattanDistance(anotherPoint: Point) = abs(x - anotherPoint.x) + abs(y - anotherPoint.y)

    companion object {

        fun centroid(points: Points): Point {
            val x = points.sumBy { it.x } / points.count()
            val y = points.sumBy { it.y } / points.count()
            return Point(x, y)
        }
    }
}