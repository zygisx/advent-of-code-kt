package misc

object Debug {

    fun visualizeMap(map: Map<Point, String>): String {
        return visualizeMap(map) { it ?: "" }
    }

    fun <T> visualizeMap(map: Map<Point, T>, toStringFn: (T?) -> String): String {
        val minX = map.minByOrNull { it.key.x }!!.key.x
        val minY = map.minByOrNull { it.key.y }!!.key.y
        val maxX = map.maxByOrNull { it.key.x }!!.key.x
        val maxY = map.maxByOrNull { it.key.y }!!.key.y

        return (minY..maxY).map { y ->
            (minX..maxX).map { x ->
                toStringFn(map[Point(x, y)])
            }.joinToString("")
        }.joinToString("\n")
    }
}