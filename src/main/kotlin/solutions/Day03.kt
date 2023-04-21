package solutions

class Day03 : Solution {

    private fun Char.priority(): Int =
        when (this) {
            in 'a'..'z' -> (this - 'a') + 1
            in 'A'..'Z' -> (this - 'A') + 27
            else -> throw IllegalArgumentException("Unexpected letter: $this")
        }

    override fun part1(input: String): String {
        return input.lines().sumOf {
            val left = it.substring(0, it.length / 2).toSet()
            val right = it.substring(it.length / 2).toSet()

            left.intersect(right).first().priority()
        }.toString()
    }

    override fun part2(input: String): String {
        return input.lines().chunked(3).sumOf {
            val x = it[0].toSet()
            val y = it[1].toSet()
            val z = it[2].toSet()

            val badges = x.intersect(y).intersect(z)

            badges.first().priority()
        }.toString()
    }
}