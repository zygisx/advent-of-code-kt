package y2023

import misc.Collections.queue
import misc.Math
import java.math.BigInteger
import kotlin.time.measureTimedValue


object Day20 : Day {
    override fun day() = 20

    // broadcaster -> a
    //%a -> inv, con
    //&inv -> b
    //%b -> con
    //&con -> output

    enum class Pulse { HIGH, LOW }


    sealed interface Module {
        val id: String
    }

    data class FlipFlop(override val id: String, var isOn: Boolean = false): Module {
        fun op(pulse: Pulse): Pulse? {
            return when (pulse) {
                Pulse.HIGH -> null
                Pulse.LOW -> {
                    val nextPulse = if (isOn) Pulse.LOW else Pulse.HIGH
                    isOn = !isOn
                    nextPulse
                }
            }
        }
    }
    data class Conjunction(override val id: String): Module {
        lateinit var lastSignals: MutableMap<String, Pulse>

        fun op(inputId: String, pulse: Pulse): Pulse {
            lastSignals[inputId] = pulse
            return if (lastSignals.all { it.value == Pulse.HIGH }) Pulse.LOW else Pulse.HIGH
        }
        fun initLastSignals(instructions: List<Instruction>) {
            val toTrack = instructions.filter { id in it.next }.map { it.module.id }
            lastSignals = toTrack.associateWith { Pulse.LOW }.toMutableMap()
        }
    }

    data object Broadcast : Module {
        override val id = "broadcaster"
        fun op(pulse: Pulse) = pulse
    }

    data class Instruction(val module: Module, val next: List<String>)

    private fun parseModule(moduleString: String): Module {
        if (moduleString.startsWith("broadcaster")) return Broadcast
        if (moduleString.startsWith("%")) return FlipFlop(moduleString.replace("%", ""))
        if (moduleString.startsWith("&")) return Conjunction(moduleString.replace("&", ""))
        error("unreachable")
    }

    private fun getInput() = getInputAsList().map { line ->
        val (moduleString, nextString) = line.split(" -> ")
        Instruction(parseModule(moduleString), nextString.split(",").map { it.trim() }.filter { it.isNotBlank() })
    }

    data class Signal(val from: String, val to: String, val pulse: Pulse)

    private fun runButtonClickSimulation(instructionById: Map<String, Instruction>): Pair<Long, Long> {
        val queue = queue(listOf<List<Signal>>())
        val nextPulses = instructionById["broadcaster"]!!.next.map {
            Signal("broadcaster", it, Pulse.LOW)
        }
        queue.add(nextPulses)

        var low = 1L +  nextPulses.size
        var high = 0L
        do {

            val fromQueue = queue.poll()
            fromQueue.forEach { signal ->
                val instruction = instructionById[signal.to]
//                println("- $instruction --> ${signal.from} -> ${signal.pulse} -> ${signal.to}")
                when (instruction?.module) {
                    null -> {
//                        println("No next instruction for ${signal.to}")
                    }
                    is Conjunction -> {
                        instruction.module as Conjunction
                        val nextPulse = instruction.module.op(signal.from, signal.pulse)
                        if (nextPulse == Pulse.HIGH) high += instruction.next.size else low += instruction.next.size
                        queue.add(instruction.next.map {
                            Signal(signal.to, it, nextPulse)
                        })
                    }
                    is FlipFlop -> {
                        instruction.module as FlipFlop
                        val nextPulse = instruction.module.op(signal.pulse)
                        if (nextPulse != null) {
                            if (nextPulse == Pulse.HIGH) high += instruction.next.size else low += instruction.next.size
                            queue.add(instruction.next.map {
                                Signal(signal.to, it, nextPulse)
                            })
                        }
                    }
                    else -> error("unreachable")
                }
            }
        } while (queue.isNotEmpty() )
        return low to high
    }

    fun part1(): Long {
        val instructions = getInput()
        instructions.map { it.module }.filterIsInstance<Conjunction>().forEach { it.initLastSignals(instructions) }

        val instructionById = instructions.associateBy { it.module.id }

        val total = (1..1000).map {
            runButtonClickSimulation(instructionById)
        }.reduce { acc, pair -> acc.first + pair.first to acc.second + pair.second }
        return total.first * total.second
    }

    fun lookForPulse(id: String, inputId: String, targetPulse: Pulse): Pair<Long, List<Instruction>> {
        val instructions = getInput()
        instructions.map { it.module }.filterIsInstance<Conjunction>().forEach { it.initLastSignals(instructions) }
        val instructionById = instructions.associateBy { it.module.id }

        var cnt = 0L
        do {
            runButtonClickSimulation(instructionById)
            cnt += 1
            println((instructionById[id]!!.module as Conjunction).lastSignals)
            if (cnt % 10_000_000 == 0L) {
                println("$id ($inputId) reached $cnt clicks")
                println((instructionById[id]!!.module as Conjunction).lastSignals)
            }
            if (cnt == 10000L) {
//                println("$id ($inputId) reached $cnt clicks")
//                println((instructionById[id]!!.module as Conjunction).lastSignals)
                break
            }
        } while (true) //((instructionById[id]!!.module as Conjunction).lastSignals[inputId] != targetPulse)
        return cnt to instructions
    }


    fun lookForPulseIntervals(id: String, inputId: String, simulations: Int): List<Pair<Pulse, IntRange>> {
        val instructions = getInput()
        instructions.map { it.module }.filterIsInstance<Conjunction>().forEach { it.initLastSignals(instructions) }
        val instructionById = instructions.associateBy { it.module.id }

        var cnt = 0
        var lastPulse = Pulse.LOW
        var intervalStart = 0
        val toReturn = mutableListOf<Pair<Pulse, IntRange>>()
        do {
            runButtonClickSimulation(instructionById)
            cnt += 1
            if ((instructionById[id]!!.module as Conjunction).lastSignals[inputId] != lastPulse) {
                toReturn.add(lastPulse to intervalStart..<cnt)
                intervalStart = cnt
                lastPulse = (instructionById[id]!!.module as Conjunction).lastSignals[inputId]!!
            }
//            println((instructionById[id]!!.module as Conjunction).lastSignals)
//            if (cnt % 10_000_000 == 0L) {
//                println("$id ($inputId) reached $cnt clicks")
//                println((instructionById[id]!!.module as Conjunction).lastSignals)
//            }
            if (cnt == simulations) {
//                println("$id ($inputId) reached $cnt clicks")
//                println((instructionById[id]!!.module as Conjunction).lastSignals)
                break
            }
        } while (true) //((instructionById[id]!!.module as Conjunction).lastSignals[inputId] != targetPulse)
        return toReturn
    }

    fun part2(): Long {

//        listOf("zl", "xn", "qn", "xf").forEach {
//            val clicks = lookForPulse(it, Pulse.HIGH)
//            println("$it -> $clicks")
//        }

//        listOf("qx", "gf", "vc", "db").shuffled().forEach {
//            val clicks = lookForPulse(it, Pulse.LOW)
//            println("$it -> $clicks")
//        }

//        val clicks = lookForPulse("xf", "db", Pulse.LOW)
//        println("zl -> $clicks")
//        val instructions = getInput()

//        val (clicks, instructions) = lookForPulse("xf", "db", Pulse.LOW)
//        println("db = $clicks")
//        println((instructions.first { it.module.id == "db" }!!.module as Conjunction).lastSignals)

//        listOf("pl", "xm", "nn", "qj", "mc", "jz", "ch", "bp").subList(0, 1).forEach {
//            val (clicks, instructions) = lookForPulse("db", it, Pulse.HIGH)
//            println("db <- $it = $clicks")
//            println((instructions.first { it.module.id == "db" }!!.module as Conjunction).lastSignals)
//        }

        println("\n\n\nxm")
        var intervals = lookForPulseIntervals("db", "xm", 10000)
        println(intervals)
        intervals.forEach {
            println("$it -> ${it.second.last - it.second.first}")
        }

        println("\n\n\npl")
        intervals = lookForPulseIntervals("db", "pl", 10000)
        println(intervals)
        intervals.forEach {
            println("$it -> ${it.second.last - it.second.first}")
        }

        println("\n\n\nnn")
        intervals = lookForPulseIntervals("db", "nn", 10000)
        println(intervals)
        intervals.forEach {
            println("$it -> ${it.second.last - it.second.first}")
        }

        println("\n\n\nqj")
        intervals = lookForPulseIntervals("db", "qj", 10000)
        println(intervals)
        intervals.forEach {
            println("$it -> ${it.second.last - it.second.first}")
        }

        println("\n\n\nqj")
        intervals = lookForPulseIntervals("db", "qj", 10000)
        println(intervals)
        intervals.forEach {
            println("$it -> ${it.second.last - it.second.first}")
        }

        // needs to be low therefore all their inputs should be high
//        listOf("qx", "gf", "vc", "db").map {  id ->
//            println("--- $id ---")
//            instructions
//                .filter { id in it.next }
//                .map { it.module.id }
//                .onEach { print("$it ") }
//                .map { lookForPulse(id, it, Pulse.HIGH).first }
//                .onEach { println("$it") }
//                .fold(BigInteger.ONE) { acc, l -> Math.lcm(acc, BigInteger.valueOf(l)) }
//        }.reduce { acc, l -> Math.lcm(acc, l) }.toLong()

        return 0

//            .forEach {
//                val clicks =
//                println("$it -> $clicks")
//            }




        // rx (low) <- th (4 inputs 👇) (all 4 last should be high)
            // - &zl: (HIGH)
                // - &qx (LOW)
            // - &xn (HIGH)
                // - &gf
            // - &qn (HIGH)
                // - &vc
            // - &xf (HIGH)
                // - &db (LOW)
                    // pl, xm, nn, qj, mc, jz, ch, bp (all HIGH)

//        fun op(inputId: String, pulse: Pulse): Pulse {
//            lastSignals[inputId] = pulse
//            return if (lastSignals.all { it.value == Pulse.HIGH }) Pulse.LOW else Pulse.HIGH
//        }
    }
}

fun main() {
//    val (part1Answer, part1Duration) = measureTimedValue { Day20.part1() }
//    println("$part1Answer in ${part1Duration.inWholeMilliseconds} ms")

    val (part2Answer, part2Duration) = measureTimedValue { Day20.part2() }
    println("$part2Answer in ${part2Duration.inWholeMilliseconds} ms")
}

// 810340069670912 -- too high