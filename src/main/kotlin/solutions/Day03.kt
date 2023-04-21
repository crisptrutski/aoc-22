package solutions

class Day03 : Solution {

    private fun charPriority(c: Char): Int {
        // A-Z = 65-90
        // a-z = 97-122
        val o = c.code
        // map [a-zA-Z] to 1..52
        return if (o > 90) o - 96 else o - 64 + 26
    }

    override fun part1(input: String): String {
        return input.lines().sumOf {
            val left = it.substring(0, it.length / 2)
            val right = it.substring(it.length / 2)

            charPriority(left.toSet().intersect(right.toSet()).first())
        }.toString()
    }

    override fun part2(input: String): String {
        return input.lines().chunked(3).sumOf {
            val x = it[0].toSet()
            val y = it[1].toSet()
            val z = it[2].toSet()

            val badges = x.intersect(y).intersect(z)

            charPriority(badges.first())
        }.toString()
    }
}