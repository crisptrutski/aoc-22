package solutions

class Day01 : Solution {
    override fun part1(input: String): String {
        return input.split("\n\n").maxOf { it ->
            it.lines().sumOf { it.toInt() }
        }.toString()
    }

    override fun part2(input: String): String {
        return input.split("\n\n")
            .map { it.lines().sumOf(String::toInt) }
            .sorted()
            .takeLast(3)
            .sum()
            .toString()
    }
}