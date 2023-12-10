package aockt.y2023

import io.github.jadarma.aockt.core.Solution

/** A solution to a fictitious puzzle used for testing. */
object Y2023D10 : Solution {

    /**
     * We move in xy grid where index diffs pr direction is like:
     *
     * South = 1y
     * North = -1y
     * East = 1x
     * West = -1x
     *
     *
     */

    enum class Origin { NORTH, EAST, SOUTH, WEST, STOP }

    fun movement(char: Char, from: Origin): Triple<Int, Int, Origin> {
        return when (from) {
            Origin.NORTH -> {
                when (char) {
                    '|' -> Triple(0, 1, Origin.NORTH)
                    'L' -> Triple(1, 0, Origin.WEST)
                    'J' -> Triple(-1, 0, Origin.EAST)
                    else -> Triple(0, 0, Origin.STOP)
                }
            }
            Origin.EAST -> {
                when (char) {
                    '-' -> Triple(-1, 0, Origin.EAST)
                    'L' -> Triple(0, -1, Origin.SOUTH)
                    'F' -> Triple(0, 1, Origin.NORTH)
                    else -> Triple(0, 0, Origin.STOP)
                }
            }
            Origin.SOUTH -> {
                when (char) {
                    '|' -> Triple(0, -1, Origin.SOUTH)
                    '7' -> Triple(-1, 0, Origin.EAST)
                    'F' -> Triple(1, 0, Origin.WEST)
                    else -> Triple(0, 0, Origin.STOP)
                }
            }
            Origin.WEST -> {
                when (char) {
                    '-' -> Triple(1, 0, Origin.WEST)
                    '7' -> Triple(0, 1, Origin.NORTH)
                    'J' -> Triple(0, -1, Origin.SOUTH)
                    else -> Triple(0, 0, Origin.STOP)
                }
            }
            else -> Triple(0, 0, Origin.STOP)
        }
    }

    private fun part1(input: String): Int {
        val grid = input
            .split("\n")
            .map { it.toCharArray() }
            .toTypedArray()

        grid.forEach { println(it) }

        var current: Triple<Int, Int, Origin> = Triple(0, 0, Origin.STOP)

        for (y in grid.indices) {
            for (x in grid[y].indices) {
                if (grid[y][x] == 'S') {
                    when {
                        listOf('|', 'F', '7').contains(grid[y - 1][x]) -> current = Triple(x, y - 1, Origin.SOUTH)
                        listOf('|', 'J', 'L').contains(grid[y + 1][x]) -> current = Triple(x, y + 1, Origin.NORTH)
                        listOf('-', 'F', 'L').contains(grid[y][x - 1]) -> current = Triple(x - 1, y, Origin.EAST)
                        listOf('-', 'J', '7').contains(grid[y][x + 1]) -> current = Triple(x + 1, y, Origin.WEST)
                    }
                }
            }
        }

        println("Starting at $current")

        var moves = 1
        while (current.third != Origin.STOP) {
            val (x, y, from) = current
            val char = grid[y][x]
            val transform = movement(char, from)
            current = transform.copy(
                first = transform.first + current.first,
                second = transform.second + current.second
            )
            moves++
            println("Move #: $moves - Char $char: ($x, $y) -> (${current.first}, ${current.second}) ")
        }

        return moves / 2
    }

    private fun part2(input: String): Int {
        val grid = input
            .split("\n")
            .map { it.toCharArray() }
            .toTypedArray()

        val replacedGrid = input
            .split("\n")
            .map { it.toCharArray() }
            .toTypedArray()

        grid.forEach { println(it) }

        var current: Triple<Int, Int, Origin> = Triple(0, 0, Origin.STOP)
        var start = 0 to 0
        for (y in grid.indices) {
            for (x in grid[y].indices) {
                if (grid[y][x] == 'S') {
                    start = x to y
                    when {
                        listOf('|', 'F', '7').contains(grid[y - 1][x]) -> current = Triple(x, y - 1, Origin.SOUTH)
                        listOf('|', 'J', 'L').contains(grid[y + 1][x]) -> current = Triple(x, y + 1, Origin.NORTH)
                        listOf('-', 'F', 'L').contains(grid[y][x - 1]) -> current = Triple(x - 1, y, Origin.EAST)
                        listOf('-', 'J', '7').contains(grid[y][x + 1]) -> current = Triple(x + 1, y, Origin.WEST)
                    }
                }
            }
        }

        println("Starting at $current")

        var moves = 1
        while (current.third != Origin.STOP) {
            val (x, y, from) = current
            replacedGrid[y][x] = '#'
            val char = grid[y][x]
            val transform = movement(char, from)
            current = transform.copy(
                first = transform.first + current.first,
                second = transform.second + current.second
            )
            moves++
        }

        for (y in replacedGrid.indices) {
            for (x in replacedGrid[y].indices) {
                if (replacedGrid[y][x] != '#') {
                    grid[y][x] = '.'
                }
            }
        }

        val (startX, startY) = start
        val replaceStartWith = when {
            listOf('|', 'F', '7').contains(grid[startY - 1][startX]) &&
                    listOf('|', 'J', 'L').contains(grid[startY + 1][startX]) -> '|'

            listOf('-', 'F', 'L').contains(grid[startY][startX - 1]) &&
                    listOf('-', 'J', '7').contains(grid[startY][startX + 1]) -> '-'

            listOf('|', 'F', '7').contains(grid[startY - 1][startX]) &&
                    listOf('-', 'F', 'L').contains(grid[startY][startX - 1]) -> 'J'

            listOf('|', 'F', '7').contains(grid[startY - 1][startX]) &&
                    listOf('-', 'J', '7').contains(grid[startY][startX + 1]) -> 'L'

            listOf('|', 'J', 'L').contains(grid[startY + 1][startX]) &&
                    listOf('-', 'J', '7').contains(grid[startY][startX + 1]) -> 'F'

            listOf('|', 'J', 'L').contains(grid[startY + 1][startX]) &&
                    listOf('-', 'F', 'L').contains(grid[startY][startX - 1]) -> '7'

            else -> 'X'
        }

        grid[startY][startX] = replaceStartWith

        println("($startX, $startY) ${grid[startY][startX]} to $replaceStartWith")

        grid.forEach { println(it.fold("") { acc, c -> acc + c}) }


        var insideCounter = 0
        var outsideCounter = 0

        var open = false
        var top = false


        for (y in grid.indices) {
            var loopTransitions = 0
            for (x in grid[y].indices) {
                when {
                    grid[y][x] == '|' -> loopTransitions++
                    open && grid[y][x] == 'J' || grid[y][x] == '7' -> {
                        if (top && grid[y][x] == '7') loopTransitions++
                        else if(!top && grid[y][x] == 'J') loopTransitions++

                        open = false
                    }
                    !open && grid[y][x] == 'L' -> {
                        open = true
                        top = true
                    }
                    !open && grid[y][x] == 'F' -> {
                        open = true
                        top = false
                    }
                    grid[y][x] == '|' -> loopTransitions++
                    grid[y][x] == '.' -> {
                        when {
                            loopTransitions % 2 == 1 -> insideCounter++
                            else -> outsideCounter++
                        }
                    }
                }

            }
        }

        println("Inside: $insideCounter - Outside: $outsideCounter")

        return insideCounter
    }


    override fun partOne(input: String) = part1(input)

    override fun partTwo(input: String) = part2(input)

}
