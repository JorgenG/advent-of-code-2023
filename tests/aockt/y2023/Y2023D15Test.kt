package aockt.y2023

import io.github.jadarma.aockt.test.AdventDay
import io.github.jadarma.aockt.test.AdventSpec
import io.kotest.matchers.should

@AdventDay(2023, 15, "Calibration document extracted")
class Y2023D15Test : AdventSpec<Y2023D15>({

    val testCalibrationDocument = """
        rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7
    """.trimIndent()

    partOne {
        testCalibrationDocument shouldOutput 1320
    }

    partTwo {
        testCalibrationDocument shouldOutput 145
    }

})
