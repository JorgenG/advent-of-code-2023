package aockt.y2023

import io.github.jadarma.aockt.core.Solution

/** A solution to a fictitious puzzle used for testing. */
object Y2023D02 : Solution {

    private fun parseInput(input: String): Int {
        return input
            .split("\n")
            .sumOf { line ->
                // Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
                val gamePart = line.split(":")[0]
                val numberPart = gamePart.split(" ")[1]
                val gameId = numberPart.toInt()

                val (red, green, blue) = listOf("red", "green", "blue").map { color ->
                    line.split(":")[1]
                        .split(";")
                        .maxOf { trial ->
                            trial.split(", ")
                                .filter { it.contains(color) }.getOrNull(0)
                                ?.trim()
                                ?.split(" ")
                                ?.getOrNull(0)
                                ?.toInt() ?: 0
                        }
                }

                if (red <= 12 && green <= 13 && blue <= 14) {
                    gameId
                } else {
                    0
                }
            }
    }

    override fun partOne(input: String) = parseInput(input)

    override fun partTwo(input: String): Int {
        return input
            .split("\n")
            .sumOf { line ->
                val (red, green, blue) = listOf("red", "green", "blue").map { color ->
                    line.split(":")[1]
                        .split(";")
                        .maxOf { trial ->
                            trial.split(", ")
                                .filter { it.contains(color) }.getOrNull(0)
                                ?.trim()
                                ?.split(" ")
                                ?.getOrNull(0)
                                ?.toInt() ?: 0
                        }
                }

                red * green * blue
            }
    }

}
