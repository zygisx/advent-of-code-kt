package misc

import java.util.*

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