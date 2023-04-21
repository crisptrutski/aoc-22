package solutions

import java.util.*

private const val MOVE_PATTERN = "move (\\d+) from (\\d+) to (\\d+)"

class Day05 : Solution {

    override fun part1(input: String): String {
        val stacks = initStacks(parseStackCount(input))
        input.lines().map { line ->
            if (line.isNotEmpty() && line[1] != '1') {
                if (line[0] != 'm') {
                    parseRow(line, stacks)
                } else {
                    val (num, from, to) = parseMove(line)

                    // Implicitly reverse crate order as they are moved
                    repeat(num) {
                        val stackFrom = stacks[from - 1]
                        val top = stackFrom.removeLast()
                        stacks[to - 1].add(top)
                    }
                }
            }
        }
        return stacks.map { if (it.isEmpty()) "" else it.last() }.joinToString("")
    }

    override fun part2(input: String): String {
        val numCrates = parseStackCount(input)
        val stacks = initStacks(numCrates)
        input.lines().map { line ->
            if (line.isNotEmpty() && line[1] != '1') {
                if (line[0] != 'm') {
                    parseRow(line, stacks)
                } else {
                    val (num, from, to) = parseMove(line)

                    // Ensure order is preserved for the moved crates
                    val buffer = mutableListOf<Char>()
                    repeat((num)) {
                        val stackFrom = stacks[from - 1]
                        val top = stackFrom.removeLast()
                        buffer.add(top)
                    }
                    while (buffer.isNotEmpty()) {
                        stacks[to - 1].add(buffer.removeLast())
                    }
                }
            }
        }
        return stacks.map { if (it.isEmpty()) "" else it.last() }.joinToString("")
    }

    private fun initStacks(num: Int) = (1..num).map { LinkedList<Char>() }

    private fun parseMove(line: String) = Regex(MOVE_PATTERN).find(line)!!.groupValues.drop(1).map(String::toInt)

    private fun parseRow(line: String, stacks: List<LinkedList<Char>>) {
        val crates = line.chunked(4).map { s -> s[1] }
        stacks.zip(crates).forEach { (s, c) -> if (c != ' ') s.addFirst(c) }
    }

    private fun parseStackCount(input: String) = input.lines().first().split(' ').size
}