package solutions

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day06Test {

    @Test
    fun test() {
        val solution = Day06()

        assertEquals("5", solution.part1("bvwbjplbgvbhsrlpgdmjqwftvncz"))
        assertEquals("6", solution.part1("nppdvjthqldpwncqszvftbrmjlhg"))
        assertEquals("10", solution.part1("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"))
        assertEquals("11", solution.part1("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"))

        assertEquals("23", solution.part2("bvwbjplbgvbhsrlpgdmjqwftvncz"))
        assertEquals("23", solution.part2("nppdvjthqldpwncqszvftbrmjlhg"))
        assertEquals("29", solution.part2("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"))
        assertEquals("26", solution.part2("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"))
    }
}


