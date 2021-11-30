package y2020


object Day13 : Day {
    override fun day() = 13

    data class Notes(val earliest: Long, val buses: List<Long>)

    private fun parseNotes(input: String): Notes {
        val lines = input.lines()
        val earliest = lines[0].toLong()
        val buses = lines[1].split(",")
                .filterNot { it == "x" }
                .map { it.toLong() }
        return Notes(earliest, buses)
    }

    private fun getInput() = getInputAsString().let { parseNotes(it) }

    fun part1(): Long {
        val notes = getInput()
        val (bus, time) = notes.buses.map { bus ->
            bus to generateSequence(notes.earliest) { it+1 }
                .dropWhile { it % bus != 0L }
                .first()
        }.minByOrNull { it.second }!!


        return (time - notes.earliest) * bus
    }

    fun part2(): Long {
        val buses = getInputAsString()
                .lines()[1]
                .split(",")
                .map { if (it == "x") 0 else it.toLong() }
                .mapIndexed { index, bus -> bus to index.toLong() }
                .filter { it.first != 0L }

        buses.forEach { println("x = ${it.second} mod(${it.first})") }

        // chinese reminder source: https://www.youtube.com/watch?v=zIFehsBHB8o
        val product = buses.map { it.first }.fold(1L) { acc, it -> acc * it}

        val Ni = buses.map { it.first }.map { product / it}
        val modules = buses.map{ it.first }
        val bi = buses.map { it.second }

        val xi = Ni.zip(modules).map { (n, m) ->
            val reminder = n % m
            // reminder * x = 1 % 7
            generateSequence(1L) { it + 1L }.dropWhile { (reminder * it) % m != 1L }.first()
        }

        val biNixi = bi
                .zip(Ni)
                .map { (b, N) -> b * N }
                .zip(xi)
                .map { (bN, x) -> bN * x }

        val N = modules.fold(1L) { acc, it -> acc * it}
        val x = biNixi.sum() % N

        return N % x
    }
}

//19	0
//37	13
//383	19
//23	27
//13	32
//29	48
//457	50
//41	60
//17	67

fun main() {
    println(Day13.part1())
    println(Day13.part2())
}