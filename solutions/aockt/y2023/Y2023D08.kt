package aockt.y2023

import io.github.jadarma.aockt.core.Solution

/** A solution to a fictitious puzzle used for testing. */
object Y2023D08 : Solution {

    private fun parseInput(input: String): Int {
        val lines = input
            .split("\n")
        val instructions = lines[0]

        val mapDetails =
            lines.subList(2, lines.size).associate { row ->
                val keyValues = row
                    .replace(" ", "")
                    .replace("(", "")
                    .replace(")", "")
                    .split("=")
                val key = keyValues[0]
                val values = keyValues[1].split(",")

                key to values
            }

        var found = false
        var steps = 0
        var location = "AAA"

        while (location != "ZZZ") {
            for (element in instructions) {
                location = if (element == 'L') {
                    mapDetails[location]!![0]
                } else {
                    mapDetails[location]!![1]
                }
                steps++
                println("$location in $steps")
                if (location == "ZZZ") {
                    break
                }
            }
        }

        return steps
    }

    fun findRelevantFactors(number: Int): List<Int> {
        val factors = mutableListOf<Int>()
        for (i in 2 .. number) {
            if (number % i == 0) {
                factors.add(i)
            }
        }
        return if (factors.size > 2) {
            selectFactors(factors.filter { it != number })
        } else {
            factors
        }
    }

    fun selectFactors(factors: List<Int>): List<Int> {
        return factors.filter {
            findRelevantFactors(it).size == 1
        }
    }

    private fun parseInput2(input: String): Long {
        val lines = input
            .split("\n")
        val instructions = lines[0]

        val mapDetails =
            lines.subList(2, lines.size).associate { row ->
                val keyValues = row
                    .replace(" ", "")
                    .replace("(", "")
                    .replace(")", "")
                    .split("=")
                val key = keyValues[0]
                val values = keyValues[1].split(",")

                key to values
            }


        val locations = mapDetails.keys.toList().filter { it.endsWith("A") }.toMutableList()
        var steps = 0L

        val locationsFrequency = locations.map { 0 }.toMutableList()

        /**
         * Observed during debugging that each starting location loops according to the input pattern.
         * Theory:
         * Search for the frequency the end location ends up in a location.
         * Find the smallest factors of those numbers, and we have the iteration where they will
         * all align.
         */
        while (!locationsFrequency.all { it > 0 }) {
            for (element in instructions) {
                steps++
                for (i in locations.indices) {
                    locations[i] = if (element == 'L') {
                        mapDetails[locations[i]]!![0]
                    } else {
                        mapDetails[locations[i]]!![1]
                    }

                    if (locationsFrequency[i] == 0 && locations[i].endsWith("Z")) {
                        locationsFrequency[i] = steps.toInt()
                    }
                }

                if (locationsFrequency.all { it > 0 }) {
                    break
                }
            }
        }

        return locationsFrequency
            .flatMap { findRelevantFactors(it).also { factors -> println("Factors $factors for $it") } }
            .toSet()
            .fold(1L) { acc, i -> i.toLong() * acc}
    }


    override fun partOne(input: String) = parseInput(input)

    override fun partTwo(input: String) = parseInput2(input)

}
