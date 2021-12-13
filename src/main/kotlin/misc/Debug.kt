package misc

object Debug {

    fun visualizeMap(map: Map<Point, String>): String {
        return visualizeMap(map) { it ?: "" }
    }

    fun <T> visualizeMap(map: Map<Point, T>, toStringFn: (T?) -> String): String {
        val maxX = map.maxByOrNull { it.key.x }!!.key.x
        val maxY = map.maxByOrNull { it.key.y }!!.key.y

        return (0..maxY).map { y ->
            (0..maxX).map { x ->
                toStringFn(map[Point(x, y)])
            }.joinToString("")
        }.joinToString("\n")
    }
}