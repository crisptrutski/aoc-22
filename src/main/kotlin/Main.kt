import solutions.*
import java.nio.file.Files
import java.nio.file.Paths

val solutions = listOf(
    Day01(),
    Day02(),
    Day03(),
    Day04(),
    Day05(),
)

val n = solutions.size

// When using the REPL, relative paths resolve to root :-(
const val base = "/Users/jeffpalentine/Workspace/learn/aoc/Advent-of-Code-2022"

private fun readFile(path: String): String = Files.readString(Paths.get(path))
private fun example(it: Int): String = readFile("$base/inputs/0$it.example.txt")
private fun input(it: Int): String = readFile("$base/inputs/0$it.txt")

fun main(args: Array<String>) {
    val problem = if (args.isNotEmpty()) args[0].toInt() else n
    printProblem(problem)
}

fun printProblem(i: Int) {
    assert(i <= n)

    val idx = i - 1
    val solution = solutions[idx]
    val example = example(i)
    val input = input(i)


    println("$i.1")
    print("  Example: ")
    println(solution.part1(example))
    print("  Actual: ")
    println(solution.part1(input))

    println("$i.2")
    print("  Example: ")
    println(solution.part2(example))
    print("  Actual: ")
    println(solution.part2(input))
}

