package solutions

import kotlin.math.absoluteValue

typealias Position = Pair<Int, Int>
typealias Positions = MutableList<Position>

enum class Direction {
    UP, DOWN, LEFT, RIGHT,
}

typealias Move = Pair<Direction, Int>

fun Position.move(dir: Direction): Position {
    return when (dir) {
        Direction.UP -> Position(first, second + 1)
        Direction.DOWN -> Position(first, second - 1)
        Direction.LEFT -> Position(first - 1, second)
        Direction.RIGHT -> Position(first + 1, second)
    }
}

private fun sign(x : Int, y : Int) : Int {
    return if (x > y) 1 else if (x < y) -1 else 0
}

private fun Position.getPulledBy(parent: Position): Position = Position(
    if ((first - parent.first).absoluteValue <= 1) first else first + sign(parent.first, first),
    if ((second - parent.second).absoluteValue <= 1) second else second + sign(parent.second, second),
)

private fun String.toMove(): Move = Move(this[0].toDirection(), this[2].digitToInt())

private fun Char.toDirection(): Direction = when (this) {
    'U' -> Direction.UP
    'D' -> Direction.DOWN
    'L' -> Direction.LEFT
    'R' -> Direction.RIGHT
    else -> throw IllegalArgumentException()
}

class Day09 : Solution {

    override fun part1(input: String): String = uniqueTailPositions(2, input)

    override fun part2(input: String): String = uniqueTailPositions(10, input)

    private fun uniqueTailPositions(knots: Int, input: String): String {
        val pieces = MutableList(knots) { Position(0, 0) }

        val visited: MutableSet<Position> = mutableSetOf()

        // Track the starting position.
        visited.add(pieces.last())

        input.lines().map(String::toMove).forEach { (dir, times) ->
            for (i in 0 until times) {
                // Move the head
                pieces[0] = pieces.first().move(dir)
                // Propagate the movement along the rope
                (1 until pieces.size).forEach { j ->
                    pieces[j] = pieces[j].getPulledBy(pieces[j - 1])
                }
                // Track where the tail visited
                visited.add(pieces.last())
            }
        }

        return visited.count().toString()
    }
}
