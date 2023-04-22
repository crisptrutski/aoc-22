package solutions

typealias Height = Int
typealias Grid = List<List<Height>>
typealias LineOfSight = List<Height>

// A list would be fine, and even more efficient, but this is more fun.
class LinesOfSight(right: LineOfSight, left: LineOfSight, down: LineOfSight, up: LineOfSight)
    : Iterable<LineOfSight> {
    private val lines = listOf(right, left, down, up)
    override fun iterator(): Iterator<LineOfSight> {
        return lines.iterator()
    }
}

fun String.toGrid(): Grid = this.lines().map { it.map(Char::digitToInt) }

// Cast rays in the cardinal directions
fun Grid.linesOfSight(i: Int, j: Int): LinesOfSight {
    val row = get(i)
    val h = size
    val w = row.size

    return LinesOfSight(
        // going right
        (j + 1 until w).map { row[it] },
        // going left
        (j - 1 downTo 0).map { row[it] },
        // going down
        (i + 1 until h).map { this[it][j] },
        // going up
        (i - 1 downTo 0).map { this[it][j] },
    )
}

class Day08 : Solution {
    override fun part1(input: String): String {
        val grid = input.trim().toGrid()

        val h = grid.size
        val w = grid[0].size

        // Less efficient than doing directional fills, but shares more code with pt2
        // See git history for the fill-based approach.
        return (0 until h).sumOf { i ->
            (0 until w).filter { j ->
                val height = grid[i][j]

                grid.linesOfSight(i, j).any {
                    it.all { h -> h < height }
                }
            }.size
        }.toString()
    }

    override fun part2(input: String): String {
        val grid = input.trim().toGrid()

        val h = grid.size
        val w = grid[0].size

        // Less efficient than doing directional fills, but shares more code with pt2
        return (0 until h).maxOf { i ->
            (0 until w).maxOf { j ->
                val height = grid[i][j]

                grid.linesOfSight(i, j).map {
                    val visible = it.takeWhile { h -> h < height }.size
                    // If vision blocked by a tree, that tree is also visible
                    if (visible != it.size) visible + 1 else visible  // not handing boundaries
                }.reduce { x, b -> x * b }
            }
        }.toString()
    }
}