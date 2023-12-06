package aockt.y2023

import io.github.jadarma.aockt.test.AdventDay
import io.github.jadarma.aockt.test.AdventSpec

/**
 * A test for a fictitious puzzle.
 *
 * ```text
 * The input is a string of numbers separated by a comma.
 * Part 1: Return the sum of the odd numbers.
 * Part 2: Return the product of the numbers.
 * ```
 */
@AdventDay(2023, 6, "Calibration document extracted")
class Y2023D06Test : AdventSpec<Y2023D06>({

    val testCalibrationDocument = """
        Time:      7  15   30
        Distance:  9  40  200
    """.trimIndent()

    partOne {
        testCalibrationDocument shouldOutput 288
    }

    partTwo {
        testCalibrationDocument shouldOutput 71503
    }

})
