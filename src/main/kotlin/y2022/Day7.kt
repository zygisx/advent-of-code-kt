package y2022


object Day7 : Day {
    override fun day() = 7

    private fun getInput() = getInputAsList()

    data class File(val name: String, val size: Long)
    data class Dir(val name: String, val innerFiles: List<File>, val innerDirs: List<String>)

    private fun Iterable<String>.toFullPath(): String {
        return this.joinToString("/")
    }

    private fun makeDirMap(lines: List<String>): Map<String, Dir> {
        var idx = 0
        val dirMap = mutableMapOf<String, Dir>()
        val currentPath = mutableListOf<String>()
        while (idx < lines.size) {
            when {
                lines[idx].startsWith("$ cd ..") -> {
                    currentPath.removeLast()
                    idx += 1
                }
                lines[idx].startsWith("$ cd") -> {
                    val dirName = lines[idx].split(" ").last()
                    currentPath.add(dirName)
                    dirMap[currentPath.toFullPath()] = Dir(dirName, emptyList(), emptyList())
                    idx += 1
                }
                lines[idx] == "$ ls" -> {
                    idx += 1
                    val filesInDir = mutableListOf<File>()
                    val dirsInDir = mutableListOf<String>()
                    while (idx < lines.size && !lines[idx].startsWith("$")) {
                        when {
                            lines[idx].startsWith("dir") -> {
                                val parts = lines[idx].split(" ")
                                dirsInDir.add(currentPath.toFullPath().plus("/${parts[1]}"))
                            }
                            else -> {
                                val parts = lines[idx].split(" ")
                                val file = File(parts[1], parts[0].toLong())
                                filesInDir.add(file)
                            }
                        }
                        idx += 1
                    }
                    val fullPath = currentPath.toFullPath()
                    dirMap[fullPath] = dirMap[fullPath]!!.copy(innerFiles = filesInDir, innerDirs = dirsInDir)
                }
            }
        }
        return dirMap
    }

    private fun getSize(dir: Dir, dirMap: Map<String, Dir>): Long {
        val innerDirs = dir.innerDirs
        val filesSize = dir.innerFiles.sumOf { it.size }
        return if (innerDirs.isEmpty()) {
            filesSize
        } else {
            filesSize + innerDirs.sumOf { getSize(dirMap[it]!!, dirMap) }
        }
    }

    fun part1(): Long {
        val directories = makeDirMap(getInput())
        val dirSizes = directories.map { getSize(it.value, directories) }
        return dirSizes.filter { it < 100000 }.sum()
    }

    fun part2(): Long {
        val spaceNeededForUpdate = 30000000L
        val totalSpace = 70000000L
        val directories = makeDirMap(getInput())
        val dirSizes = directories.map { getSize(it.value, directories) }
        val rootSize = dirSizes.max()
        val freeSpace = totalSpace - rootSize
        val spaceNeeded = spaceNeededForUpdate - freeSpace

        return dirSizes.sortedDescending().last { it > spaceNeeded }
    }
}

fun main() {
    println(Day7.part1())
    println(Day7.part2())
}