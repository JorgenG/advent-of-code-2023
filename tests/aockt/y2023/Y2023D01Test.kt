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
@AdventDay(2023, 1, "Calibration document extracted")
class Y2023D01Test : AdventSpec<Y2023D01>({

    val testCalibrationDocument = """
        1abc2
        pqr3stu8vwx
        a1b2c3d4e5f
        treb7uchet
    """.trimIndent()

    partOne {
        testCalibrationDocument shouldOutput 142
        listOf("asdsadasd", "asdds0sasd", "023320") shouldAllOutput 0
        Inputs.D1 shouldOutput 55816
    }

    partTwo {
        "eighthree2threeight" shouldOutput 88
        listOf("asdsadasd", "asdds0sasd", "023320") shouldAllOutput 0
        Inputs.D1 shouldOutput 54980
    }

})
