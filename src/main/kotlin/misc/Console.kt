package misc

import y2024.Day7
import kotlin.time.measureTimedValue

object Console {

    fun printWithTime(block: () -> Any) {
        measureTimedValue {
            block()
        }.also { println("Result: ${it.value} in ${it.duration.inWholeMilliseconds} ms.") }
    }
}