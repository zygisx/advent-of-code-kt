import misc.InputReader

interface DayInputReader {

    fun inputFile(): String

    fun getInputAsString(): String {
        return InputReader.getInputAsString(inputFile())
    }

    fun getInputAsList(): List<String> {
        return InputReader.getInputAsList(inputFile())
    }
}