package misc

import java.util.*
import java.util.function.Predicate

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
