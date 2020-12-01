package misc

import java.io.File

object InputReader {

    fun getInputAsString(fileName: String): String {
        return fromResources(fileName).readText()
    }

    fun getInputAsList(fileName: String): List<String> {
        return fromResources(fileName).readLines()
    }

    private fun fromResources(fileName: String): File {
        return File(javaClass.classLoader.getResource(fileName).toURI())
    }
}
