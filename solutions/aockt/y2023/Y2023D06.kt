package aockt.y2023

import io.github.jadarma.aockt.core.Solution

/** A solution to a fictitious puzzle used for testing. */
object Y2023D06 : Solution {

    data class Record(val timeInMillis: Long, val recordMilliMeters: Long)

    fun countTrialsBefore(recordTimeMillis: Long, recordDistance: Long): Long {
        for (i in 0 .. recordTimeMillis) {
            if ((recordTimeMillis - i) * i > recordDistance) {
                return i
            }
        }
        return -1
    }

    fun countTrialsBeforeReverse(recordTimeMillis: Long, recordDistance: Long): Long {
        for (i in 0 .. recordTimeMillis) {
            val holdTime = recordTimeMillis - i - 1
            if ((recordTimeMillis - holdTime) * holdTime > recordDistance) {
                return i
            }
        }
        return -1
        /*
        return LongRange(1, recordTimeMillis).indexOfFirst { counter ->
            val holdTime = recordTimeMillis - counter
            val speed = holdTime
            ((recordTimeMillis - holdTime) * speed)
                .also { println("Calculated $it for holdTime $holdTime for record $recordTimeMillis ms and $recordDistance mm") } > recordDistance
        }*/
    }

    fun trialsBetterThanRecord(recordTimeMillis: Long, recordDistance: Long): Int {
        return LongRange(0, recordTimeMillis).map { holdTime ->
            val speed = holdTime
            ((recordTimeMillis - holdTime) * speed).also { println("Calculated $it for holdTime $holdTime for record $recordTimeMillis ms and $recordDistance mm") }
        }.count { (it > recordDistance) }
    }


    private fun parseInput(input: String): Int {
        val inputs = input
            .split("\n").associate { row ->
                val extract = row.split(" ").filter { it.isNotEmpty() }
                val designator = extract[0]
                val values = extract.subList(1, extract.size).map { it.toLong() }
                designator to values
            }

        val boatRecords =
            inputs["Time:"]!!.zip(inputs["Distance:"]!!).map { (time, distance) -> Record(time, distance) }

        return boatRecords.fold(1) { acc, record ->
            trialsBetterThanRecord(
                record.timeInMillis,
                record.recordMilliMeters.toLong()
            ) * acc
        }
    }


    fun parseInput2(input: String): Long {
        val inputs = input
            .split("\n").associate { row ->
                val extract = row.split(" ").filter { it.isNotEmpty() }
                val designator = extract[0]
                val value = extract.subList(1, extract.size)
                    .also { println(it) }
                    .joinToString(separator = "")
                    .also { println(it) }
                    .toLong()
                designator to value
            }

        val record = Record(inputs["Time:"]!!, inputs["Distance:"]!!)

        val firstTrialAbove = countTrialsBefore(record.timeInMillis, record.recordMilliMeters)
        val lastTrialAbove = countTrialsBeforeReverse(record.timeInMillis, record.recordMilliMeters)

        // Attempts left
        return record.timeInMillis - lastTrialAbove - firstTrialAbove
    }

    override fun partOne(input: String) = parseInput(input)

    override fun partTwo(input: String) = parseInput2(input)

}