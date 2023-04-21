package solutions

typealias FileSize = Int

sealed class Line
object Ls : Line()
data class Cd(val dir: String) : Line()
data class DirEntry(val filename: String) : Line()
data class FileEntry(val size: FileSize, val filename: String) : Line()

private fun String.toLine(): Line = when {
    this == "$ ls" -> Ls
    this.startsWith("$ cd ") -> Cd(this.substring(5))
    this.startsWith("dir") -> DirEntry(this.substring(4))
    else -> {
        val (size, filename) = this.split(' ')
        FileEntry(size.toInt(), filename)
    }
}

data class Dir(val parent: Dir?, val name: String) {
    fun subdir(child: String) = Dir(this, child)
    override fun toString(): String = when (parent) {
        null -> "/"
        ROOT -> "/$name"
        else -> "$parent/$name"
    }
}

val ROOT = Dir(null, "")

class Path(var dir: Dir) {
    fun cd(arg: String) {
        dir = when (arg) {
            "/" -> ROOT
            ".." -> dir.parent!!
            else -> dir.subdir(arg)
        }
    }
}

class Day07 : Solution {
    private fun getDirectorySizes(input: String): Map<Dir, FileSize> {
        val path = Path(ROOT)
        val children = mutableMapOf<Dir, MutableSet<Dir>>()
        val directSize = mutableMapOf<Dir, FileSize>()
        val directories = mutableSetOf<Dir>()

        input.trim().lines().forEach {
            when (val line = it.toLine()) {
                Ls -> {} // We just assume each filesize row comes after an implicit ls

                is Cd -> {
                    path.cd(line.dir)
                    directories.add(path.dir)
                }

                is FileEntry -> {
                    // This is dumb, given there is a default :-(
                    directSize[path.dir] = (directSize[path.dir] ?: 0) + line.size
                }

                is DirEntry -> {
                    children.computeIfAbsent(path.dir) { _ -> mutableSetOf() }
                        .add(path.dir.subdir(line.filename))
                }
            }
        }

        fun totalSize(dir: Dir): FileSize {
            return (directSize[dir] ?: 0) + (children[dir]?.sumOf(::totalSize) ?: 0)
        }

        return directories.associateWith(::totalSize)
    }

    override fun part1(input: String): String {
        return getDirectorySizes(input).values.filter { it <= 100_000 }.sum().toString()
    }

    override fun part2(input: String): String {
        val totalSpace = 70_000_000
        val requiredSpace = 30_000_000
        val directorySizes = getDirectorySizes(input)

        val currentSpace = totalSpace - (directorySizes[ROOT] ?: 0)
        val mustDelete = requiredSpace - currentSpace

        return directorySizes.values.sorted().dropWhile { it < mustDelete }.first().toString()
    }
}