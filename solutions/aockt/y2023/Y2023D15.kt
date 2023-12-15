package aockt.y2023

import io.github.jadarma.aockt.core.Solution

/** A solution to a fictitious puzzle used for testing. */
object Y2023D15 : Solution {

    private fun part1(input: String): Long {
        val words = input.split(",")
        var sum = 0L

        for (word in words) {
            var wordValue = 0

            for (char in word) {
                wordValue += char.code
                wordValue *= 17
                wordValue %= 256
            }

            sum += wordValue
        }

        return sum
    }

    private fun part2(input: String): Int {
        val words = input.split(",")
        val boxes = IntRange(0, 255).map { mutableListOf<Pair<String, String>>() }.toTypedArray()

        for (word in words) {
            val (id, value) = word.split("-", "=")

            var wordValue = 0
            for (char in id) {
                wordValue += char.code
                wordValue *= 17
                wordValue %= 256
            }

            if (word.contains("=")) {
                val pair = id to value
                if (boxes[wordValue].find { it.first == id } != null) {
                    boxes[wordValue].replaceAll { (curId, curValue) ->
                        if (curId == id) {
                            pair
                        } else {
                            curId to curValue
                        }
                    }
                }
                else {
                    boxes[wordValue].add(id to value)
                }
            } else {
                boxes[wordValue].removeAll { it.first == id }
            }

            println("$wordValue for $word - boxes: ${boxes[wordValue].map { it }.toList()}")
        }

        var sum = 0
        for (i in boxes.indices) {
            for (j in boxes[i].indices) {
                sum += (i + 1) * (j + 1) * boxes[i][j].second.toInt()
            }
        }

        return sum
    }


    override fun partOne(input: String) = part1(input)

    override fun partTwo(input: String) = part2(input)

}
