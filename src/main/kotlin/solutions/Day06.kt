package solutions

class Day06 : Solution {
    override fun part1(input: String): String = endOfFirstUniqueNgram(4, input).toString()
    override fun part2(input: String): String = endOfFirstUniqueNgram(14, input).toString()

    private fun endOfFirstUniqueNgram(n: Int, input: String): Int {
        return input.windowed(n).takeWhile { xs ->
            xs.toSet().size != n
        }.size + n
    }
}
