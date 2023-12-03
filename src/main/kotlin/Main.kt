import java.io.File

fun main() {
    val filePath = "input.txt"
    val inputData = File(filePath).readLines()
    val totalInputData = calculateTotalInputData(inputData)

    println("Total Value: $totalInputData")
}

fun calculateTotalInputData(inputData: List<String>): Int {
    return inputData.sumOf { line ->
        val firstDigit = line.firstOrNull {it.isDigit()}?:'0'
        val lastDigit = line.lastOrNull {it.isDigit()}?:'0'
        "$firstDigit$lastDigit".toInt()
    }
}
