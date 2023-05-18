import solutions.*
import java.nio.file.Files
import java.nio.file.Paths

// TODO: We should instead create an instance of the class for every example we run.
//       That way we can initialize some state if we want. Take input in constructor.
val solutions = listOf(
    Day01(),
    Day02(),
    Day03(),
    Day04(),
    Day05(),
    Day06(),
    Day07(),
    Day08(),
    Day09(),
)

val n = solutions.size

// When using the REPL, relative paths resolve to root :-(
const val base = "/Users/jeffpalentine/Workspace/learn/aoc/Advent-of-Code-2022"

// TODO: If we are missing a non-example file, we should pull it from the website.
//       Being smart enough to extract the example input is probably too complicated.
//       Maybe they are nice and put some identifiers in the HTML.
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

    // TODO: We should version control the answers, to test refactoring correct solutions safely.
}

