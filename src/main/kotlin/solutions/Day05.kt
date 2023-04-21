package solutions

import java.util.*

class Day05 : Solution {
    override fun part1(input: String): String {
        val numCrates = input.lines().first().split(' ').size
        val stacks = (1..numCrates).map { LinkedList<Char>() }
        input.lines().map { line ->
            if (line.isNotEmpty() && line[1] != '1') {
                if (line[0] != 'm') {
                    val crates = line.chunked(4).map { s -> s[1] }
                    stacks.zip(crates).forEach { (s, c) -> if (c != ' ') s.addFirst(c) }
                } else {
                    val (num, from, to) = Regex("move (\\d+) from (\\d+) to (\\d+)")
                        .find(line)!!
                        .groupValues
                        .drop(1)
                        .map(String::toInt)

                    (1..num).forEach {
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
        val numCrates = input.lines().first().split(' ').size
        val stacks = (1..numCrates).map { LinkedList<Char>() }
        input.lines().map { line ->
            if (line.isNotEmpty() && line[1] != '1') {
                if (line[0] != 'm') {
                    val crates = line.chunked(4).map { s -> s[1] }
                    stacks.zip(crates).forEach { (s, c) -> if (c != ' ') s.addFirst(c) }
                } else {
                    val (num, from, to) = Regex("move (\\d+) from (\\d+) to (\\d+)")
                        .find(line)!!
                        .groupValues
                        .drop(1)
                        .map(String::toInt)

                    val buffer = mutableListOf<Char>()
                    (1..num).forEach {
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
}