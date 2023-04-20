import java.nio.file.Files
import java.nio.file.Paths

// Somehow assigning a lambda to a variable does not give the correct signature
@Suppress("UNUSED_PARAMETER")
fun todo(unused: String): Int {
    return -1
}

const val problems = 3

// When using the REPL, relative paths resolve to root :-(
val base = "/Users/jeffpalentine/Workspace/learn/aoc/Advent-of-Code-2022";

val examples = (1..problems).map { Files.readString(Paths.get("$base/inputs/0$it.example.txt")) }
val actuals = (1..problems).map { Files.readString(Paths.get("$base/inputs/0$it.txt")).trim() }

val solutions = listOf(
    Day1(),
    Day2(),
    Day3(),
)

fun main(args: Array<String>) {
    val problem = if (args.isNotEmpty()) args[0].toInt() else problems
    printProblem(problem);
}

fun printProblem(i: Int) {
    assert(i <= problems)

    val idx = i - 1
    val solution = solutions[idx]

    println("Problem $i");

    println("Part 1")
    println("Example")
    println(solution.part1(examples.get(idx)));
    println("Actual")
    println(solution.part1(actuals.get(idx)));

    println("----")

    println("Part 2")
    println("Example")
    println(solution.part2(examples.get(idx)));
    println("Actual")
    println(solution.part2(actuals.get(idx)));

    println("====")
}

fun charPriority(c : Char) : Int {
    // A-Z = 65-90
    // a-z = 97-122
    val o = c.code
    // map [a-zA-Z] to 1..52
    return if (o > 90) o - 96 else o - 64 + 26
}

interface Solution {
    fun part1(input: String): Int = -1
    fun part2(input: String): Int = -1
}

class Day1 : Solution {
    override fun part1(input: String): Int {
        return input.split("\n\n")
            .map {
                it.lines().map { it.toInt() }.sum()
            }
            .max();
    }

    override fun part2(input: String): Int {
        return input.split("\n\n")
            .map {
                it.lines().map { it.toInt() }.sum()
            }
            .sorted()
            .takeLast(3)
            .sum();
    }
}

class Day2 : Solution {
    override fun part1(input: String): Int {
        val scores = mapOf('X' to 1, 'Y' to 2, 'Z' to 3)
        val beats = mapOf('A' to 'Z', 'B' to 'X', 'C' to 'Y')
        val equal = mapOf('A' to 'X', 'B' to 'Y', 'C' to 'Z')

        val strategy = input.split('\n')
            .map { it.split(' ') }
            .map { Pair(it[0][0], it[1][0]) }

        return strategy.sumOf { (opp, self) ->
            // 3 points for a tie, 0 points for a loss, 6 points for a win
            val outcomeScore = if (equal[opp]!! == self) 3 else if (beats[opp] == self) 0 else 6
            // Add the score for the move itself
            outcomeScore + scores[self]!!
        }
    }

    override fun part2(input: String): Int {
        val scores = mapOf('X' to 1, 'Y' to 2, 'Z' to 3)
        val beats = mapOf('A' to 'Z', 'B' to 'X', 'C' to 'Y')
        val equal = mapOf('A' to 'X', 'B' to 'Y', 'C' to 'Z')
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
            outcomeScore + scores[self]!!
        }
    }
}

class Day3 : Solution {
    override fun part1(input: String): Int {
        return input.lines().map {
            val left = it.substring(0, it.length / 2)
            val right = it.substring(it.length / 2)

            charPriority(left.toSet().intersect(right.toSet()).first())
        }.sum()
    }

    override fun part2(input: String): Int {
        return input.lines().chunked(3).map {
            val x = it[0].toSet()
            val y = it[1].toSet()
            val z = it[2].toSet()

            val badges = x.intersect(y).intersect(z)

            charPriority(badges.first())
        }.sum()
    }
}