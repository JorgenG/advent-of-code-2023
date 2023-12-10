package aockt.y2023

import io.github.jadarma.aockt.test.AdventDay
import io.github.jadarma.aockt.test.AdventSpec
import io.kotest.matchers.should

/**
 * A test for a fictitious puzzle.
 *
 * ```text
 * The input is a string of numbers separated by a comma.
 * Part 1: Return the sum of the odd numbers.
 * Part 2: Return the product of the numbers.
 * ```
 */
@AdventDay(2023, 10, "Calibration document extracted")
class Y2023D10Test : AdventSpec<Y2023D10>({

    val example1 = """
        .....
        .S-7.
        .|.|.
        .L-J.
        .....
    """.trimIndent()

    val example2 = """
        ..F7.
        .FJ|.
        SJ.L7
        |F--J
        LJ...
    """.trimIndent()

    partOne {
        example1 shouldOutput 4
        example2 shouldOutput 8
    }


    val example3 = """
        ...........
        .S-------7.
        .|F-----7|.
        .||.....||.
        .||.....||.
        .|L-7.F-J|.
        .|..|.|..|.
        .L--J.L--J.
        ...........
    """.trimIndent()

    partTwo {
        example3 shouldOutput 4
    }

})
