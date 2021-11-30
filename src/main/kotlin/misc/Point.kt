package misc

import kotlin.math.abs

typealias Points = Iterable<Point>

interface IPoint {
    operator fun plus(point: IPoint): IPoint

    fun directions(): List<IPoint>

    fun adjacent(): List<IPoint> = directions().map { this + it }
}

data class Point(val x: Int, val y: Int): IPoint {
    fun north() = north(1)
    fun north(steps: Int) = Point(x, y + steps)
    fun south() = south(1)
    fun south(steps: Int) = Point(x, y - steps)
    fun east() = east(1)
    fun east(steps: Int) = Point(x + steps, y)
    fun west() = west(1)
    fun west(steps: Int) = Point(x - steps, y)

    fun northWest() = Point(x - 1, y + 1)
    fun northEast() = Point(x + 1, y + 1)
    fun southWest() = Point(x - 1, y - 1)
    fun southEast() = Point(x + 1, y - 1)

    fun neighbours() = sequenceOf(north(), south(), east(), west())

    override fun plus(point: IPoint): IPoint {
        point as Point
        return Point(x + point.x, y + point.y)
    }

    override fun directions(): List<IPoint> {
        return directions
    }

    fun adjacentFn() = listOf(Point::north, Point::northWest, Point::northEast,
            Point::east, Point::west, Point::south, Point::southWest, Point::southEast, )

    fun manhattanDistance(anotherPoint: Point) = abs(x - anotherPoint.x) + abs(y - anotherPoint.y)

    companion object {
        val zeroPoint = Point(0, 0)
        val directions = directions()

        private fun directions(): List<Point> {
            return (-1..1).flatMap { x ->
                (-1..1).map { y ->
                    Point(x, y)
                }
            }.filterNot { it == zeroPoint }
        }

        fun centroid(points: Points): Point {
            val x = points.sumBy { it.x } / points.count()
            val y = points.sumBy { it.y } / points.count()
            return Point(x, y)
        }
    }
}

data class Point3d(val x: Int, val y: Int, val z: Int): IPoint {
    override operator fun plus(point: IPoint): Point3d {
        point as Point3d
        return Point3d(x + point.x, y + point.y, z + point.z)
    }

    override fun directions(): List<IPoint> {
        return directions
    }

    companion object {
        val zeroPoint = Point3d(0, 0, 0)
        val directions = directions()

        private fun directions(): List<Point3d> {
            return (-1..1).flatMap { x ->
                (-1..1).flatMap { y ->
                    (-1..1).map { z ->
                        Point3d(x, y, z)
                    }
                }
            }.filterNot { it == zeroPoint }
        }
    }
}

data class Point4d(val x: Int, val y: Int, val z: Int, val w: Int): IPoint {
    override operator fun plus(point: IPoint): Point4d {
        point as Point4d
        return Point4d(x + point.x, y + point.y, z + point.z, w + point.w)
    }

    override fun directions(): List<IPoint> {
        return directions
    }

    companion object {
        val zeroPoint = Point4d(0, 0, 0, 0)
        val directions = directions()

        private fun directions(): List<Point4d> {
            return (-1..1).flatMap { x ->
                (-1..1).flatMap { y ->
                    (-1..1).flatMap { z ->
                        (-1..1).map { w ->
                            Point4d(x, y, z, w)
                        }
                    }
                }
            }.filterNot { it == zeroPoint }
        }
    }
}