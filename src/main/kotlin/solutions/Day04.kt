package solutions

class Day04 : Solution {
    override fun part1(input: String): String {
        return input.lines().count {
            val (left, right) = it.toRangePair()

            left.contains(right) || right.contains(left)
        }.toString()
    }

    override fun part2(input: String): String {
        return input.lines().count {
            val (left, right) = it.toRangePair()

            left intersects right
        }.toString()
    }

    private fun IntRange.contains(other: IntRange) = first <= other.first && last >= other.last

    private infix fun IntRange.intersects(other: IntRange) = first <= other.last && other.first <= last

    private fun String.toRange(): IntRange {
        val (left, right) = split('-')
        return left.toInt()..right.toInt()
    }

    private fun String.toRangePair(): Pair<IntRange, IntRange> {
        val (left, right) = split(',')
        return Pair(left.toRange(), right.toRange())
    }
}