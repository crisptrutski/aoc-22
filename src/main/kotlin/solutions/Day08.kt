package solutions

typealias Height = Int
typealias Grid = List<List<Height>>
typealias Visibility = List<MutableList<Boolean>>

fun String.toGrid(): Grid = this.lines().map { it.map(Char::digitToInt) }

class Day08 : Solution {
    override fun part1(input: String): String {
        val grid = input.trim().toGrid()

        val h = grid.size
        val w = grid[0].size

        val visibility: Visibility = grid.map { it.map { false }.toMutableList() }

        // Fill Right
        for (i in 0 until h) {
            visibility[i][0] = true
            var highestSeen = grid[i][0]
            for (j in 1 until w) {
                if (grid[i][j] > highestSeen) {
                    visibility[i][j] = true
                    highestSeen = grid[i][j]
                }
            }
        }
        // Fill Left
        for (i in 0 until h) {
            visibility[i][w - 1] = true
            var highestSeen = grid[i][w-1]
            for (j in w - 2 downTo 0) {
                if (grid[i][j] > highestSeen) {
                    visibility[i][j] = true
                    highestSeen = grid[i][j]
                }
            }
        }
        // Fill Down
        for (j in 0 until w) {
            visibility[0][j] = true
            var highestSeen = grid[0][j]
            for (i in 1 until h) {
                if (grid[i][j] > highestSeen) {
                    visibility[i][j] = true
                    highestSeen = grid[i][j]
                }
            }
        }
        // Fill Up
        for (j in 0 until w) {
            visibility[h - 1][j] = true
            var highestSeen = grid[h-1][j]
            for (i in h - 2 downTo 0) {
                if (grid[i][j] > highestSeen) {
                    visibility[i][j] = true
                    highestSeen = grid[i][j]
                }
            }
        }

//        [3x, 0x, 3x, 7x, 3x]
//        [2x, 5x, 5x, 1 , 2x]
//        [6x, 5x, 3 , 3x, 2x]
//        [3x, 3 , 5x, 4 , 9x]
//        [3x, 5x, 3x, 9x, 0x]

//        visibility.forEach { println(it) }

        return visibility.sumOf { it -> it.count { it } }.toString()
    }

    override fun part2(input: String): String {
        val grid = input.toGrid()
        return "-1"
    }
}