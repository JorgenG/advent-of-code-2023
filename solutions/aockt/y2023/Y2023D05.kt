package aockt.y2023

import io.github.jadarma.aockt.core.Solution

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
