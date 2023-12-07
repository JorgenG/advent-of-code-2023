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
@AdventDay(2023, 7, "Calibration document extracted")
class Y2023D07Test : AdventSpec<Y2023D07>({

    val testCalibrationDocument = """
        32T3K 765
        T55J5 684
        KK677 28
        KTJJT 220
        QQQJA 483
    """.trimIndent()

    partOne {
        testCalibrationDocument shouldOutput 6440
    }

    val testCalibrationDocument2 = """
        32T3K 765
        T55J5 684
        KK677 28
        KTJJT 220
        QQQJA 483
    """.trimIndent()

    partTwo {
        testCalibrationDocument2 shouldOutput 5905
    }

})
