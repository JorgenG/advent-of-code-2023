package aockt.y2023

import io.github.jadarma.aockt.core.Solution

/** A solution to a fictitious puzzle used for testing. */
object Y2023D09 : Solution {

    private fun part1(input: String): Int {
        val inputNumbers = input
            .split("\n")
            .map { row -> row
                .split(" ")
                .map(String::toInt)
            }

        var sum = 0

        for (numberList in inputNumbers) {
            var listSum = numberList.last()
            var iteration = 1
            val numbers = numberList.toIntArray()
            println("${numbers.toList()}")
            while (!numbers.all { it == 0 }) {
                for ((i, _) in numbers.withIndex()) {
                    if (i < numbers.size - iteration) {
                        println("Putting ${numbers[i + 1]} - ${numbers[i]} into index $i")
                        numbers[i] = numbers[i + 1] - numbers[i]
                    } else {
                        listSum += numbers[i - 1]
                        numbers[i] = 0
                        iteration++
                        println("Sum: $sum, listSu: $listSum, numbers: ${numbers.toList()}")

                        break
                    }
                }
            }

            sum += listSum

            println("Sum: $sum")
        }

        return sum
    }

    private fun part2(input: String): Int {
        val inputNumbers = input
            .split("\n")
            .map { row -> row
                .split(" ")
                .map(String::toInt)
            }

        var sum = 0

        for (numberList in inputNumbers) {
            var iteration = 1
            val numbers = numberList.toIntArray()

            var leadingNumbers = listOf(numbers.first())

            println("${numbers.toList()}")
            while (!numbers.all { it == 0 }) {
                for ((i, _) in numbers.withIndex()) {
                    if (i < numbers.size - iteration) {
                        println("Putting ${numbers[i + 1]} - ${numbers[i]} into index $i")
                        numbers[i] = numbers[i + 1] - numbers[i]
                    } else {
                        leadingNumbers = leadingNumbers + numbers[0]
                        numbers[i] = 0
                        iteration++
                        println("Sum: $sum, listSu: $leadingNumbers, numbers: ${numbers.toList()}")

                        break
                    }
                }
            }

            sum += leadingNumbers.reduceRight { num, acc -> num - acc}

            println("Sum: $sum")
        }

        return sum
    }


    override fun partOne(input: String) = part1(input)

    override fun partTwo(input: String) = part2(input)

}
