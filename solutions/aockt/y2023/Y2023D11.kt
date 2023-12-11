package aockt.y2023

import io.github.jadarma.aockt.core.Solution
import kotlin.math.abs
import kotlin.math.max

/** A solution to a fictitious puzzle used for testing. */
object Y2023D11 : Solution {

    private fun part1(input: String, isPart1: Boolean): Long {

        val universe = input
            .split("\n")
            .map { row ->
                if (row.all { it == '.' }) {
                    row.replace('.', '2').toCharArray()
                } else {
                    row.replace('.', '1').toCharArray()
                }
            }.toTypedArray()


        val galaxies = mutableListOf<Pair<Int, Int>>()

        for (columnIndex in universe[0].indices) {
            var isSpace = true
            for (rowIndex in universe.indices) {
                if (universe[rowIndex][columnIndex] == '#') {
                    galaxies.add(rowIndex to columnIndex)
                    isSpace = false
                }
            }
            if (isSpace) {
                for (rowIndex in universe.indices) {
                    universe[rowIndex][columnIndex] = '2'
                }
            }
        }


        val distanceMesh = universe.map { it.map {
            when (it) {
                '2' -> if (isPart1) 2 else 1000000
                '1' -> 1
                else -> 1
            }
        } }

        var sum = 0L
        for (i in galaxies.indices) {
            val (x, y) = galaxies[i]
            for ((targetX, targetY) in galaxies.subList(i, galaxies.size)) {
                var deltaX = abs(targetX - x)
                var deltaY = abs(targetY - y)
                val modifierX = if (targetX - x >= 0) 1 else -1
                val modifierY = if (targetY - y >= 0) 1 else -1
                var distance = 0L

                println("Searching for distance between ($x, $y) and ($targetX, $targetY). Deltas: ($deltaX, $deltaY)")
                while (deltaX != 0 || deltaY != 0) {
                    if (deltaX > deltaY) {
                        distance += distanceMesh[x + (deltaX - 1) * modifierX][y]
                        deltaX--
                    } else {
                        distance += distanceMesh[x][y + (deltaY - 1) * modifierY]
                        deltaY--
                    }
                }
                println("Calculated $distance between ($x, $y) and ($targetX, $targetY)")
                sum += distance
            }
        }

        return sum
    }

    private fun part2(input: String): Int {

        return 2
    }


    override fun partOne(input: String) = part1(input, true)

    override fun partTwo(input: String) = part1(input, false)

}
