package aockt.y2023

import io.github.jadarma.aockt.core.Solution
import kotlin.math.max

/** A solution to a fictitious puzzle used for testing. */
object Y2023D07 : Solution {



    private fun parseInput(input: String): Long {
        val hands = input
            .split("\n")
            .map { row ->
                val values = row.split(" ")
                val score = values[1].toLong()

                val hand = values[0]
                    // Cards to hex value
                    .replace("A", "E")
                    .replace("T", "A")
                    .replace("J", "B")
                    .replace("Q", "C")
                    .replace("K", "D")

                val unique = hand.groupBy { it }.size
                val maxSize = hand.groupBy { it }.values.fold(0) { acc, chars ->
                    if (chars.size > acc) chars.size else acc
                }
                /**
                 * G - High Card -> 5 unique
                 * H - One pair -> 4 unique
                 * I - Two Pair -> 3 unique, max size = 2
                 * J - Three of a kind -> 3 unique, max size = 3
                 * K - Full House -> 2 unique
                 * L - Four of a kind -> 2 unique, max size = 4
                 * M - Five of a kind -> 1 unique
                 */

                val typeHand = when {
                    unique == 5 -> "G$hand"
                    unique == 4 -> "H$hand"
                    unique == 3 && maxSize == 2 -> "I$hand"
                    unique == 3 && maxSize == 3 -> "J$hand"
                    unique == 2 && maxSize == 3 -> "K$hand"
                    unique == 2 && maxSize == 4 -> "L$hand"
                    unique == 1 -> "M$hand"
                    else -> throw Exception("Invalid logic for this $hand - unique: $unique, maxSize: $maxSize")
                }

                typeHand to score
            }

        val sorted = hands.sortedBy { it.first }

        val result = sorted.foldIndexed(0L) { i, acc, handToScore -> (i + 1) * handToScore.second + acc }

        return result
    }


    private fun part2(input: String): Long {
        val hands = input
            .split("\n")
            .map { row ->
                val values = row.split(" ")
                val score = values[1].toLong()

                val hand = values[0]
                    // Cards to hex value
                    .replace("A", "E")
                    .replace("T", "A")
                    .replace("J", "1") // Mapping to 1 to have the natural ordering
                    .replace("Q", "C")
                    .replace("K", "D")

                val jokers = hand.count { it == '1' }
                val uniquePreJoker = hand.filter { it != '1' }.groupBy { it }.size
                val maxSizePreJoker = hand.filter { it != '1' }.groupBy { it }.values.fold(0) { acc, chars ->
                    if (chars.size > acc) chars.size else acc
                }
                /**
                 * G - High Card -> 5 unique
                 * H - One pair -> 4 unique
                 * I - Two Pair -> 3 unique, max size = 2
                 * J - Three of a kind -> 3 unique, max size = 3
                 * K - Full House -> 2 unique
                 * L - Four of a kind -> 2 unique, max size = 4
                 * M - Five of a kind -> 1 unique
                 */

                val unique = max(uniquePreJoker, 1)
                val maxSize = if (jokers > 0) max(maxSizePreJoker + jokers, jokers) else maxSizePreJoker

                val typeHand = when {
                    unique == 5 -> "G$hand"
                    unique == 4 -> "H$hand"
                    unique == 3 && maxSize == 2 -> "I$hand"
                    unique == 3 && maxSize == 3 -> "J$hand"
                    unique == 2 && maxSize == 3 -> "K$hand"
                    unique == 2 && maxSize == 4 -> "L$hand"
                    unique == 1 -> "M$hand"
                    else -> throw Exception("Invalid logic for this $hand - unique: $unique, maxSize: $maxSize")
                }

                typeHand to score
            }

        val sorted = hands.sortedBy { it.first }

        val result = sorted.foldIndexed(0L) { i, acc, handToScore -> (i + 1) * handToScore.second + acc }

        return result
    }


    override fun partOne(input: String) = parseInput(input)

    override fun partTwo(input: String) = part2(input)

}
