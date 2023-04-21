package solutions

class Day02 : Solution {
    private val scores = mapOf('X' to 1, 'Y' to 2, 'Z' to 3)
    private val beats = mapOf('A' to 'Z', 'B' to 'X', 'C' to 'Y')
    private val equal = mapOf('A' to 'X', 'B' to 'Y', 'C' to 'Z')

    override fun part1(input: String): String {
        val strategy = input.split('\n')
            .map { it.split(' ') }
            .map { Pair(it[0][0], it[1][0]) }

        return strategy.sumOf { (opp, self) ->
            // 3 points for a tie, 0 points for a loss, 6 points for a win
            val outcomeScore = if (equal[opp]!! == self) 3 else if (beats[opp] == self) 0 else 6
            // Add the score for the move itself
            outcomeScore + scores[self]!!
        }.toString()
    }

    override fun part2(input: String): String {
        val loses = mapOf('A' to 'Y', 'B' to 'Z', 'C' to 'X')

        val strategy = input.split('\n')
            .map { it.split(' ') }
            .map { Pair(it[0][0], it[1][0]) }

        return strategy.sumOf { (opp, outcome) ->
            // 3 points for a tie, 0 points for a loss, 6 points for a win
            val outcomeScore = when (outcome) {
                'X' -> 0
                'Y' -> 3
                'Z' -> 6
                else -> throw Exception("Invalid outcome")
            }

            val self = when (outcome) {
                'X' -> beats[opp]!!
                'Y' -> equal[opp]!!
                'Z' -> loses[opp]!!
                else -> throw Exception("Invalid outcome")
            }

            // Add the score for the move itself
            (outcomeScore + scores[self]!!)
        }.toString()
    }
}