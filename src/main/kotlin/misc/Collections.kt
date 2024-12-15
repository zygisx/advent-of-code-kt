package misc

import java.util.*
import java.util.function.Predicate

fun <T> MutableList<T>.swap(idx1: Int, idx2: Int) {
    val temp = this[idx1]
    this[idx1] = this[idx2]
    this[idx2] = temp
}

fun <T> List<T>.getAllUniquePairs(): List<Pair<T, T>> {
    val pairs = mutableListOf<Pair<T, T>>()

    for (i in 0 until size) {
        for (j in (i + 1) until size) {
            pairs.add(this[i] to this[j])
        }
    }

    return pairs
}

object Collections {

    fun <T> queue(initial: Collection<T>): Queue<T> {
        return LinkedList(initial)
    }

    fun <T> stack(initial: Collection<T>): ArrayDeque<T> {
        return ArrayDeque(initial)
    }

    fun <T> deque(initial: Collection<T>): ArrayDeque<T> {
        return ArrayDeque(initial)
    }
}

fun <T> List<T>.splitWithPredicate(predicate: (T) -> Boolean): Sequence<List<T>> {
    val list = this
    return sequence {
        val buffer = mutableListOf<T>()
        list.forEach {
            if (predicate(it)) {
                yield(buffer)
                buffer.clear()
            } else buffer.add(it)
        }
        if (buffer.isNotEmpty()) yield(buffer)
    }
}
