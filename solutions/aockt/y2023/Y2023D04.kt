package aockt.y2023

import io.github.jadarma.aockt.core.Solution
import kotlin.math.pow

data class ScratchPad(val id: Int, val numbers: List<Int>, val winners: List<Int>) {

    fun points(): Int {
        return numbers.count { winners.contains(it) }
    }

    fun score(): Int {
        val points = points()
        return if (points > 0) {
            2.toDouble().pow(points - 1).toInt()
        } else {
            0
        }
    }
}

/** A solution to a fictitious puzzle used for testing. */
object Y2023D04 : Solution {

    private fun parseInput(input: String): Int {
        return input
            // Normalize spacing for easier parsing
            .replace("    ", " ")
            .replace("   ", " ")
            .replace("  ", " ")
            .split("\n")
            .map { row ->
                val idToGameDetails = row.split(":")
                val id = idToGameDetails[0].split(" ")[1].toInt()
                val gameDetails = idToGameDetails[1].split("|")
                val numbers = gameDetails[1].trim().split(" ").map { it.toInt() }
                val winners = gameDetails[0].trim().split(" ").map { it.toInt() }
                ScratchPad(id, numbers, winners)
            }.sumOf { scratchpad ->
                println("$scratchpad - ${scratchpad.score()}")
                scratchpad.score()
            }
    }

    private fun cardCounts(current: ScratchPad, scratchPads: Map<Int, ScratchPad>): Int {
        if (current.points() == 0) {
            return 1
        }

        return 1 + IntRange(current.id + 1, current.id + current.points())
            .sumOf { id ->
                (scratchPads[id]?.let { cardCounts(it, scratchPads)} ?: 0)
            }
    }

    private fun parseInput2(input: String): Int {
        val scratchPads = input
            // Normalize spacing for easier parsing
            .replace("    ", " ")
            .replace("   ", " ")
            .replace("  ", " ")
            .split("\n")
            .associate { row ->
                val idToGameDetails = row.split(":")
                val id = idToGameDetails[0].split(" ")[1].toInt()
                val gameDetails = idToGameDetails[1].split("|")
                val numbers = gameDetails[1].trim().split(" ").map { it.toInt() }
                val winners = gameDetails[0].trim().split(" ").map { it.toInt() }
                id to ScratchPad(id, numbers, winners)
            }



        return scratchPads.values.sumOf { scratchPad ->
            cardCounts(scratchPad, scratchPads)
                .also { println("$it for $scratchPad (points: ${scratchPad.points()}") }
        }
    }

    override fun partOne(input: String) = parseInput(input)

    override fun partTwo(input: String) = parseInput2(input)

}
