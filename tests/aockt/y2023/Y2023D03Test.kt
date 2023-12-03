package aockt.y2023

import io.github.jadarma.aockt.test.AdventDay
import io.github.jadarma.aockt.test.AdventSpec
import io.kotest.matchers.comparables.shouldBeLessThan

/**
 * A test for a fictitious puzzle.
 *
 * ```text
 * The input is a string of numbers separated by a comma.
 * Part 1: Return the sum of the odd numbers.
 * Part 2: Return the product of the numbers.
 * ```
 */
@AdventDay(2023, 3, "Engine part numbers")
class Y2023D03Test : AdventSpec<Y2023D03>({

    val testCalibrationDocument = """
        467..114..
        ...*......
        ..35..633.
        ......#...
        617*......
        .....+.58.
        ..592.....
        ......755.
        ...$.*....
        .664.598..
    """.trimIndent()

    val constructedDocument = """
        ...*......
        ..35..6334
        ......#...
    """.trimIndent()

    val partFrom = """
        .479........155..............944.....622..............31.........264.......................532..........................254.........528.....
        ..............-...............%.....+...................=....111*.................495.......+.......558..................../..........*.....
        ....................791*..62.....$.............847........&........-..........618.*...........818....&..642.........................789.....
        ....520.58......405......#....542.../587.............*....198.......846.........*..............*.......*....................647.............
    """.trimIndent()

    partOne {
        testCalibrationDocument shouldOutput 4361
        constructedDocument shouldOutput 6369
        partFrom shouldOutput 10387
        Inputs.D3 shouldOutput 528819
    }

    val gearRatioExample = """
        467..114..
        ...*......
        ..35..633.
        ......#...
        617*......
        .....+.58.
        ..592.....
        ......755.
        ...$.*....
        .664.598..
    """.trimIndent()

    partTwo {
        gearRatioExample shouldOutput 467835
        Inputs.D3 shouldOutput 80403602
    }

})
