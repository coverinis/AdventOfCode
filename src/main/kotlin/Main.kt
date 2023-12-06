import java.io.File

fun isAdjacentToSymbol(schematic: List<List<Char>>, i: Int, j: Int): Boolean {
    for (di in -1..1) {
        for (dj in -1..1) {
            if (di == 0 && dj == 0) continue
            val ni = i + di
            val nj = j + dj
            if (ni in schematic.indices && nj in schematic[ni].indices && !schematic[ni][nj].isDigit() && schematic[ni][nj] != '.') {
                return true
            }
        }
    }
    return false
}

fun main() {
    val filePath = "input.txt"

    val schematicMatrix: List<List<Char>> = File(filePath).useLines { lines ->
        lines.map { it.toList() }.toList()
    }

    var sum = 0
    val numberString = StringBuilder()
    var nextToSymbol = false

    for (i in schematicMatrix.indices) {
        for (j in schematicMatrix[i].indices) {
            if (schematicMatrix[i][j].isDigit()) {
                numberString.append(schematicMatrix[i][j])
                if (!nextToSymbol) {
                    nextToSymbol = isAdjacentToSymbol(schematicMatrix, i, j)
                }
            } else if (nextToSymbol) {
                sum += numberString.toString().toInt()
                numberString.setLength(0)
                nextToSymbol = false
            } else {
                numberString.setLength(0)
            }
        }
        if (nextToSymbol) {
            sum += numberString.toString().toInt()
            nextToSymbol = false
        }
        numberString.setLength(0)
    }

    println("Sum: $sum")
}
