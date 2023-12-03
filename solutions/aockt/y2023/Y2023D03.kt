package aockt.y2023

import io.github.jadarma.aockt.core.Solution
import kotlin.math.max
import kotlin.math.min
import kotlin.streams.toList

/** A solution to a fictitious puzzle used for testing. */
object Y2023D03 : Solution {

    private fun parseInput(input: String): Int {
        val grid = input
            .split("\n")
            .map { line ->
                line.chars().mapToObj { it.toChar() }.toList()
            }

        val symbols = grid.flatMapIndexed { i, row ->
            row.flatMapIndexed { j, char ->
                if (char.toString().toIntOrNull() != null) {
                    emptyList()
                }
                else if (char.toString() == ".") {
                    emptyList()
                }
                else {
                    listOf("$i,$j" to true)
                }
            }
        }.toMap()

        // println("${grid.size}, ${grid[0].size}")
        // println(symbols)

        val numbers = grid.flatMapIndexed { i, row ->
            // println()
            row.foldIndexed(emptyList<Int>() to "") { j, (numList, prevNum), char ->
                // print(char.toString())

                val curNum = if (char.toString().toIntOrNull() != null) {
                    prevNum + char.toString()
                } else {
                    prevNum
                }
                when {
                    curNum.isNotEmpty() && (
                            char.toString().toIntOrNull() == null || j == row.size - 1
                        ) -> {
                        // We must process a number
                        val columnRange = IntRange(max(i - 1, 0), min(i + 1, grid.size - 1))
                        val rowRange = IntRange(max(j - curNum.length - 1, 0), min(j, row.size - 1))

                        val hasSymbolNearby = columnRange.any { i ->
                            rowRange.any { j ->
                                symbols.containsKey("$i,$j")
                            }
                        }
                        // println("$columnRange - $rowRange - $hasSymbolNearby - $curNum")
                        if (hasSymbolNearby) {
                            numList +  curNum.toInt() to ""
                        } else {
                            numList to ""
                        }
                    }
                    else -> numList to curNum
                }
            }.first
        }

        return numbers.sum()
    }

    private fun gearRatios(input: String): Int {
        val grid = input
            .split("\n")
            .map { line ->
                line.chars().mapToObj { it.toChar() }.toList()
            }

        val gearCounter = grid.flatMapIndexed { i, row ->
            row.flatMapIndexed { j, char ->
                if (char.toString() == "*") {
                    listOf("$i,$j" to true)
                }
                else {
                    emptyList()
                }
            }
        }.toMap()

        // println("${grid.size}, ${grid[0].size}")
        // println(gearCounter)

        val numbers = grid.flatMapIndexed { i, row ->
            // println()
            row.foldIndexed(emptyList<Pair<String, Int>>() to "") { j, (numList, prevNum), char ->
                // print(char.toString())

                val curNum = if (char.toString().toIntOrNull() != null) {
                    prevNum + char.toString()
                } else {
                    prevNum
                }
                when {
                    curNum.isNotEmpty() && (
                            char.toString().toIntOrNull() == null || j == row.size - 1
                            ) -> {
                        // We must process a number
                        val columnRange = IntRange(max(i - 1, 0), min(i + 1, grid.size - 1))
                        val rowRange = IntRange(max(j - curNum.length - 1, 0), min(j, row.size - 1))

                        val gearMatches = columnRange.flatMap { i ->
                            rowRange.fold(emptyList<Pair<String, Int>>()) { acc, j ->
                                if (gearCounter.containsKey("$i,$j")) {
                                    acc + listOf("$i,$j" to curNum.toInt())
                                }
                                else {
                                    acc
                                }
                            }
                        }
                        numList + gearMatches to ""
                    }
                    else -> numList to curNum
                }
            }.first
        }

        // println(numbers)

        return numbers
            .groupBy { it.first }
            .filter { it.value.size == 2 }
            .map { it.value[0].second * it.value[1].second }
            .sum()
    }

    override fun partOne(input: String) = parseInput(input)

    override fun partTwo(input: String) = gearRatios(input)

}
