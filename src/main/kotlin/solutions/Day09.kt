package solutions

import kotlin.math.absoluteValue

typealias Position = Pair<Int, Int>

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

private fun Position.getPulledBy(parent: Position): Position {
    var x = this.first
    var y = this.second

    val dx = parent.first - x
    val dy = parent.second - y

    if (dx.absoluteValue > 1) {
        x += dx / dx.absoluteValue
        if (dy.absoluteValue > 1) {
            y += dy / dy.absoluteValue
        } else {
            y = parent.second
        }
    } else {
        if (dy.absoluteValue > 1) {
            x = parent.first
            y += dy / dy.absoluteValue
        }
    }

    return Position(x, y)
}

private fun String.toMove(): Move = Move(
    this[0].toDirection(),
    this.split(" ")[1].toInt()
)

private fun Char.toDirection(): Direction = when (this) {
    'U' -> Direction.UP
    'D' -> Direction.DOWN
    'L' -> Direction.LEFT
    'R' -> Direction.RIGHT
    else -> throw IllegalArgumentException()
}

class User(
    val id: Int,
    val name: String,
    val roles: Array<String>
);

class Day09 : Solution {


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


//                displayMap(pieces, width, height)
            }
        }

//        visualize(visited)
        return visited.count().toString()
    }

    private fun displayMap(pieces: List<Position>, width: Int, height: Int) {
        val head = pieces.first()
        val others = setOf(*pieces.toTypedArray())

        for (y in 4 downTo 0) {
            for (x in 0 until 6) {
                val pos = Position(x, y)
                if (head == pos) {
                    print("H")
                } else if (others.contains(pos)) {
                    for (j in 1 until pieces.size) {
                        if (pieces[j] == pos) {
                            print(j.toString())
                            break
                        }
                    }
                } else {
                    print(".")
                }
            }
            println()
        }
        println("")
    }

    // Expect for real input:
    // 5930, 2443 (only second part is wrong..)

    private fun visualize(positions: Set<Position>) {
        val minW = positions.minOf { it.first }
        val maxW = positions.maxOf { it.first }
        val minH = positions.minOf { it.second }
        val maxH = positions.maxOf { it.second }

        println()
        for (y in minH..maxH) {
            for (x in minW..maxW) {
                print(if (positions.contains(Position(x, minH + maxH - y))) '#' else '.')
            }
            println()
        }
    }
}
