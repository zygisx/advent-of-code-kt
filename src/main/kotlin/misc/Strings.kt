package misc

fun String.splitToInts(delimiter: String = " "): List<Int> {
    return this.split(delimiter).filter { it.isNotBlank() }.map { it.toInt() }
}

fun String.splitToLongs(): List<Long> {
    return this.split(" ").filter { it.isNotBlank() }.map { it.toLong() }
}

fun String.indicesOf(char: Char): List<Int> {
    return sequence {
        var last = -1
        while (this@indicesOf.indexOf(char, last+1).also { last = it } != -1) {
            yield(last)
        }
    }.toList()
}