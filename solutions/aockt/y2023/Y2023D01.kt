package aockt.y2023

import aockt.y9999.Y9999D01
import io.github.jadarma.aockt.core.Solution

/** A solution to a fictitious puzzle used for testing. */
object Y2023D01 : Solution {

    private fun parseInput(input: String): Int {
        return input
            .split("\n")
            .sumOf {
                val leftMostInt = it
                    .reduceOrNull { acc, c -> acc.toString().toIntOrNull()?.toString()?.get(0) ?: c }
                    ?.toString()
                    ?.toIntOrNull()
                    ?.toString() ?: "0"
                val rightMostInt = it
                    .reduceOrNull { acc, c -> c.toString().toIntOrNull()?.toString()?.get(0) ?: acc }
                    ?.toString()
                    ?.toIntOrNull()
                    ?.toString() ?: "0"
                val result = (leftMostInt + rightMostInt)
                result.toInt()
            }
    }


    private fun transform(input: String): String {
        return input
            .replace("one", "o1e")
            .replace("two", "t2o")
            .replace("three", "t3e")
            .replace("four", "4")
            .replace("five", "5e")
            .replace("six", "6")
            .replace("seven", "7n")
            .replace("eight", "e8t")
            .replace("nine", "n9e")
    }


    override fun partOne(input: String) = parseInput(input)

    override fun partTwo(input: String) = parseInput(transform(input))

}
