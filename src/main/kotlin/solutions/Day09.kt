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

private fun sign(x: Int, y: Int): Int {
    return if (x > y) 1 else if (x < y) -1 else 0
}

private fun isTouching(x: Int, y: Int): Boolean = (x - y).absoluteValue <= 1

private fun Position.shouldPull(tail: Position): Boolean =
    !isTouching(first, tail.first) || !isTouching(second, tail.second)

private fun Position.getPulledBy(parent: Position): Position = if (parent.shouldPull(this)) {
    Position(
        first + sign(parent.first, first),
        second + sign(parent.second, second),
    )
} else {
    this
}

private fun String.toMove(): Move = Move(this[0].toDirection(), this.split(" ")[1].toInt())

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

//        visualize(visited)

        return visited.count().toString()
    }

    private fun visualize(positions: Set<Position>) {
        val minW = positions.minOf { it.first }
        val maxW = positions.maxOf { it.first }
        val minH = positions.minOf { it.second }
        val maxH = positions.maxOf { it.second }

        println()
        for (y in minH..maxH) {
            for (x in minW..maxW) {
                print(if (positions.contains(Position(x, maxH - y))) '#' else '.')
            }
            println()
        }
    }
}
