package misc


class Counter<KEY> {
    private val counter = mutableMapOf<KEY, Int>()

    fun inc(key: KEY): Int {
        return add(key,1)
    }

    fun add(key: KEY, increment: Int): Int {
        counter.compute(key) { _, v -> if (v != null) v + increment else 1 }
        return counter[key]!!
    }

    fun getMap(): Map<KEY, Int> {
        return counter.toMap()
    }

    fun max(): Pair<KEY, Int>? {
        return counter.maxByOrNull { it.value }?.toPair()
    }

    fun maxKey(): KEY? {
        return max()?.first
    }
}