import java.nio.file.Files
import java.nio.file.Paths

// When using the REPL, relative paths resolve to root :-(
const val base = "/Users/jeffpalentine/Workspace/learn/aoc/Advent-of-Code-2022"

val solutions = listOf(
    Day1(),
    Day2(),
    Day3(),
)

val n = solutions.size

private fun readFile(path: String): String = Files.readString(Paths.get(path))
private fun example(it: Int): String = readFile("$base/inputs/0$it.example.txt")
private fun actual(it: Int): String = readFile("$base/inputs/0$it.txt")

val examples = (1..n).map { example(it) }
val actualInputs = (1..n).map { actual(it).trim() }

fun main(args: Array<String>) {
    val problem = if (args.isNotEmpty()) args[0].toInt() else n
    printProblem(problem)
}

fun printProblem(i: Int) {
    assert(i <= n)

    val idx = i - 1
    val solution = solutions[idx]

    println("$i.1")
    print("  Example: ")
    println(solution.part1(examples[idx]))
    print("  Actual: ")
    println(solution.part1(actualInputs[idx]))

    println("$i.2")
    print("  Example: ")
    println(solution.part2(examples[idx]))
    print("  Actual: ")
    println(solution.part2(actualInputs[idx]))
}

fun charPriority(c: Char): Int {
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
        return input.split("\n\n").maxOf { it ->
            it.lines().sumOf { it.toInt() }
        }
    }

    override fun part2(input: String): Int {
        return input.split("\n\n")
            .map { it.lines().sumOf(String::toInt) }
            .sorted()
            .takeLast(3)
            .sum()
    }
}

class Day2 : Solution {
    private val scores = mapOf('X' to 1, 'Y' to 2, 'Z' to 3)
    private val beats = mapOf('A' to 'Z', 'B' to 'X', 'C' to 'Y')
    private val equal = mapOf('A' to 'X', 'B' to 'Y', 'C' to 'Z')

    override fun part1(input: String): Int {

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
        return input.lines().sumOf {
            val left = it.substring(0, it.length / 2)
            val right = it.substring(it.length / 2)

            charPriority(left.toSet().intersect(right.toSet()).first())
        }
    }

    override fun part2(input: String): Int {
        return input.lines().chunked(3).sumOf {
            val x = it[0].toSet()
            val y = it[1].toSet()
            val z = it[2].toSet()

            val badges = x.intersect(y).intersect(z)

            charPriority(badges.first())
        }
    }
}