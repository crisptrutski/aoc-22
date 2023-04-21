package solutions

class Day04 : Solution {
    private fun parseRange(input: String): IntRange {
        val (left, right) = input.split('-')
        return left.toInt()..right.toInt()
    }

    override fun part1(input: String): String {
        return input.lines().count {
            val (left, right) = it.split(',').map(::parseRange)
            val leftContained = left.first >= right.first && left.last <= right.last
            val rightContained = left.first <= right.first && left.last >= right.last

            leftContained || rightContained
        }.toString()
    }

    override fun part2(input: String): String {
        return input.lines().count {
            val (left, right) = it.split(',').map(::parseRange)

            left.intersect(right).isNotEmpty()
        }.toString()
    }
}