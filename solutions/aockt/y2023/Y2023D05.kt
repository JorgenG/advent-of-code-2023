package aockt.y2023

import io.github.jadarma.aockt.core.Solution
import java.lang.Long.min
import kotlin.math.max

data class RangeMapping(
    val sourceStart: Long,
    val destination: Long,
    val rangeSize: Long
) {
    fun get(value: Long): Long? {
        return if (value >= sourceStart && value < sourceStart + rangeSize)
            destination + value - sourceStart
        else
            null
    }
}

data class SeedRange(
    val start: Long,
    val end: Long
)

fun map(source: Long, mappers: List<RangeMapping>): Long {
    return (mappers
        .find { it.get(source) != null }
        ?.get(source) ?: source)
}

/** A solution to a fictitious puzzle used for testing. */
object Y2023D05 : Solution {

    private fun parseInput(input: String): Long {
        val rows = input
            // Normalize input for easier parsing
            .replace("\n\n", ";")
            .replace("\n", ",")
            .replace(":,", ":")
            .replace(";", "\n")
            .split("\n")

        // rows[0] example: seeds: 79 14 55 13
        val seeds = rows[0].replace("seeds: ", "").split(" ").map { it.toLong() }

        // Remainder rows: seed-to-soil map:50 98 2,52 50 48
        val mappers = rows.subList(1, rows.size)
            .associate { row ->
                val mappingName = row
                    .split(":")[0]
                    .split(" ")[0]

                mappingName to row
                    .split(":")[1]
                    .split(",")
                    .filter { it != "" }
                    .map { range -> range.split(" ").map { it.toLong() } }
                    .map { rangeMapping ->
                        RangeMapping(rangeMapping[1], rangeMapping[0], rangeMapping[2]).also { println(it) }
                    }
            }

        return seeds.map {
            val soil = map(it, mappers["seed-to-soil"] ?: emptyList())
            val fertilizer = map(soil, mappers["soil-to-fertilizer"] ?: emptyList())
            val water = map(fertilizer, mappers["fertilizer-to-water"] ?: emptyList())
            val light = map(water, mappers["water-to-light"] ?: emptyList())
            val temperature = map(light, mappers["light-to-temperature"] ?: emptyList())
            val humidity = map(temperature, mappers["temperature-to-humidity"] ?: emptyList())
            val location = map(humidity, mappers["humidity-to-location"] ?: emptyList())

            println("seed $it")
            println("soil $soil")
            println("fertilizer $fertilizer")
            println("water $water")
            println("light $light")
            println("temperature $temperature")
            println("humidity $humidity")
            println("location $location")

            location
        }.min()
    }

    private fun min3(v1: Long, v2: Long, v3: Long): Long {
        return if (v1 < v2 && v1 < v3) {
            v1
        } else if (v2 < v1 && v2 < v3) {
            v2
        } else {
            v3
        }
    }

    private fun min4(v1: Long, v2: Long, v3: Long, v4: Long): Long {
        val min3 = min3(v2, v3, v4)
        return if (v1 < min3) {
            v1
        } else {
            min3
        }
    }

    data class MergePoint(val value: Long, val offset: Long, val isInDest: Boolean, val isStart: Boolean)

    fun toRangeMapping(mp1: MergePoint, mp2: MergePoint): RangeMapping {
        return when {
            mp1.isInDest && mp2.isInDest && mp2.isStart -> {
                RangeMapping(
                    mp1.value + 1,
                    mp1.value + 1,
                    mp2.value - (mp1.value + 1)
                )
            }

            !mp1.isInDest && !mp2.isInDest && mp2.isStart-> {
                RangeMapping(
                    mp1.value + 1,
                    mp1.value + 1,
                    mp2.value - (mp1.value + 1)
                )
            }

            mp1.isInDest && mp2.isInDest -> {
                RangeMapping(
                    mp1.value,
                    mp1.value + mp1.offset,
                    mp2.value - mp1.value
                )
            }

            !mp1.isInDest && !mp2.isInDest -> {
                RangeMapping(
                    mp1.value + mp1.offset,
                    mp1.value,
                    mp2.value - mp1.value
                )
            }

            !mp1.isInDest && mp2.isInDest -> {
                if (!mp2.isStart && !mp1.isStart) {
                    RangeMapping(
                        mp2.value + mp2.offset,
                        mp1.value + mp1.offset,
                        mp2.value - mp1.value
                    )
                } else if(!mp2.isStart) {
                    RangeMapping(
                        mp1.value + mp1.offset,
                        mp2.value + mp2.offset,
                        mp2.value - mp1.value
                    )
                } else {
                    RangeMapping(
                        mp1.value + mp1.offset,
                        mp1.value + mp1.offset,
                        mp2.value - mp1.value
                    )
                }
            }

            !mp1.isInDest && mp2.isInDest -> {
                RangeMapping(
                    mp1.value + mp1.offset,
                    mp1.value + mp1.offset,
                    mp2.value - mp1.value
                )
            }

            mp1.isInDest && !mp2.isInDest && !mp2.isStart -> {
                RangeMapping(
                    mp2.value + mp2.offset,
                    mp1.value + mp1.offset,
                    mp2.value - mp1.value
                )
            }

            else -> {
                // mp1.isInDest && !mp2.isInDest
                RangeMapping(
                    mp1.value,
                    mp1.value + mp1.offset,
                    mp2.value - mp1.value
                )
            }
        }.also { println("Merged $mp1 with $mp2: $it") }
    }

    fun combineMergePoints(mergePoints: List<MergePoint>): List<RangeMapping> {
        val initial: Triple<List<RangeMapping>, MergePoint?, MergePoint?> = Triple(emptyList(), null, null)
        return mergePoints.sortedBy { it.value }
            .foldIndexed(initial) { i, (mappings, prevOutSource, prevInDest), mergePoint ->
                when {
                    prevOutSource != null && prevOutSource.isStart -> {
                        when {
                            prevInDest == null -> {
                                Triple(
                                    mappings + toRangeMapping(prevOutSource, mergePoint),
                                    if (mergePoint.isInDest) prevOutSource else mergePoint,
                                    if (mergePoint.isInDest) mergePoint else prevInDest
                                )
                            }

                            else -> {
                                val prevMergePoint = if (prevInDest.value > prevOutSource.value) prevInDest else prevOutSource
                                val moddedMergePoint = if (prevMergePoint.isInDest == mergePoint.isInDest && mergePoint.isStart) {
                                    if (prevMergePoint.isInDest) {
                                        prevOutSource.copy(value = prevMergePoint.value)
                                    } else {
                                        prevInDest.copy(value = prevMergePoint.value)
                                    }
                                } else {
                                    prevMergePoint
                                }
                                Triple(
                                    mappings + toRangeMapping(moddedMergePoint, mergePoint),
                                    if (mergePoint.isInDest) prevOutSource else mergePoint,
                                    if (mergePoint.isInDest) mergePoint else prevInDest
                                )
                            }
                        }
                    }

                    else ->
                        Triple(
                            mappings,
                            if (mergePoint.isInDest) prevOutSource else mergePoint,
                            if (mergePoint.isInDest) mergePoint else prevInDest
                        )
                }
            }.first.filter { it.rangeSize > 0 }
    }


    fun main() {
        val merged = Y2023D05.mergeMappers(
            listOf(
                RangeMapping(10, 42, 10), // 42 - 52
                RangeMapping(100, 55, 15), // 55 - 70
            ),
            listOf(
                RangeMapping(13, 200, 30), // 13 - 43
                RangeMapping(45, 300, 20), // 45 - 65
            )
        )

        merged.forEach { it.also { println(it) } }
    }

    fun mergeMappers(inMapper: List<RangeMapping>, outMapper: List<RangeMapping>): List<RangeMapping> {
        val inMergePoints = inMapper.flatMap { inMap ->
            listOf(
                MergePoint(inMap.destination, inMap.sourceStart - inMap.destination, false, true),
                MergePoint(
                    inMap.destination + inMap.rangeSize,
                    inMap.sourceStart - inMap.destination,
                    false,
                    false
                ),
            )
        }

        val outMergePoints = outMapper.flatMap { outMap ->
            listOf(
                MergePoint(outMap.sourceStart, outMap.destination - outMap.sourceStart, true, true),
                MergePoint(
                    outMap.sourceStart + outMap.rangeSize,
                    outMap.destination - outMap.sourceStart,
                    true,
                    false
                ),
            )
        }

        val allMergePoints = inMergePoints + outMergePoints
        return combineMergePoints(allMergePoints)
    }


    private fun parseInput2(input: String): Long {
        val rows = input
            // Normalize input for easier parsing
            .replace("\n\n", ";")
            .replace("\n", ",")
            .replace(":,", ":")
            .replace(";", "\n")
            .split("\n")

        // rows[0] example: seeds: 79 14 55 13
        val seedValues = rows[0]
            .replace("seeds: ", "")
            .split(" ")
            .map { it.toLong() }

        val seedRange = IntRange(0, (seedValues.size - 1) / 2)
            .map { SeedRange(seedValues[it*2], seedValues[it*2] + seedValues[(it*2) + 1]) }


        // Remainder rows: seed-to-soil map:50 98 2,52 50 48
        val mappers = rows.subList(1, rows.size)
            .associate { row ->
                val mappingName = row
                    .split(":")[0]
                    .split(" ")[0]

                mappingName to row
                    .split(":")[1]
                    .split(",")
                    .filter { it != "" }
                    .map { range -> range.split(" ").map { it.toLong() } }
                    .map { rangeMapping ->
                        RangeMapping(rangeMapping[1], rangeMapping[0], rangeMapping[2]).also { println(it) }
                    }
            }

        return seedRange.map { range ->
            println(range)
            LongRange(range.start, range.end)
                .minOf {
                    val soil = map(it, mappers["seed-to-soil"] ?: emptyList())
                    val fertilizer = map(soil, mappers["soil-to-fertilizer"] ?: emptyList())
                    val water = map(fertilizer, mappers["fertilizer-to-water"] ?: emptyList())
                    val light = map(water, mappers["water-to-light"] ?: emptyList())
                    val temperature = map(light, mappers["light-to-temperature"] ?: emptyList())
                    val humidity = map(temperature, mappers["temperature-to-humidity"] ?: emptyList())
                    val location = map(humidity, mappers["humidity-to-location"] ?: emptyList())

                    location
                }
        }.min()
    }

    override fun partOne(input: String) = parseInput(input)

    override fun partTwo(input: String) = parseInput2(input)

}
