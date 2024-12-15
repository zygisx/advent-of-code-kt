package y2024

import misc.Console.printWithTime

object Day9 : Day {

    override fun day() = 9

    private fun getInput() = getInputAsList()

    sealed interface Slot {
        val size: Int
        val id: Int
    }
    data class Used(override val size: Int, override val id: Int) : Slot
    data class Free(override val size: Int, override val id: Int) : Slot

    private fun expand(disk: List<Slot>): List<Int> {
        val expanded = mutableListOf<Int>()
        disk.forEach { d ->
            repeat(d.size) {
                when (d) {
                    is Used -> expanded.add(d.id)
                    is Free -> expanded.add(-1)
                }
            }
        }
        return expanded
    }

    fun sum(disk: List<Int>): Long {
        return disk.mapIndexed { index, i -> if (i == -1) 0 else index * i.toLong() }.sum()
    }

    fun part1(): Long {
        val disk = getInput().first()
        val diskArray = mutableListOf<Int>()
        disk.trim().windowed(2, 2).forEachIndexed { index, c ->
            val fileSize = c[0].digitToInt()
            val emptySlot = c[1].digitToInt()

            repeat(fileSize) { diskArray.add(index) }
            repeat(emptySlot) { diskArray.add(-1) }
        }
        if (disk.length % 2 == 1) {
            repeat(disk.last().digitToInt()) { diskArray.add(disk.length / 2 ) }
        }

        var a = 0
        var b = diskArray.size-1
        while (a < b) {
            while (diskArray[a] != -1) {
                a++
            }
            while (diskArray[b] == -1) {
                b--
            }
            diskArray[a] = diskArray[b]
            diskArray[b] = -1
        }

        return diskArray
            .filter { it != -1 }
            .mapIndexed { index, i -> index * i.toLong() }.sum()
    }

    fun part2(): Long {
        val disk = getInput().first()
        val diskArray = mutableListOf<Slot>()
        disk.trim().windowed(2, 2).forEachIndexed { index, c ->
            val fileSize = c[0].digitToInt()
            val emptySlot = c[1].digitToInt()

            diskArray.add(Used(fileSize, index))
            diskArray.add(Free(emptySlot, index))
        }
        if (disk.length % 2 == 1) {
            diskArray.add(Used(disk.last().digitToInt(), disk.length / 2))
        }

        var j = diskArray.size -1
        while (j > 0) {
            if (diskArray[j] is Used) {
                val used = diskArray[j] as Used
                val firstFree = diskArray.withIndex().firstOrNull { (idx, it) ->
                    it is Free && it.size >= used.size && idx < j
                }?.value
                if (firstFree != null) {
                    val leftFree = firstFree.size - used.size
                    val idxToRemove = diskArray.indexOf(firstFree)
                    diskArray.removeAt(j)
                    diskArray.add(j, Free(used.size, used.id))
                    diskArray.removeAt(idxToRemove)
                    diskArray.add(idxToRemove, Used(used.size, used.id))
                    if (leftFree > 0) {
                        diskArray.add(idxToRemove+1, Free(leftFree, used.id))
                    }
                }
            }
            j--
        }
        return sum(expand(diskArray))
    }
}

fun main() {
    printWithTime { Day9.part1() }
    printWithTime { Day9.part2() }
}
