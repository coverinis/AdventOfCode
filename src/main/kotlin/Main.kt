import java.io.File

fun main() {
    val filePath = "input.txt"
    val inputData = File(filePath).readLines()
    val totalInputData = calculateTotalInputData(inputData)

    println("Total Value: $totalInputData")
}

fun calculateTotalInputData(inputData: List<String>): Int {
    val wordsToDigits = mapOf(
        "three" to 3,
        "seven" to 7,
        "eight" to 8,
        "four" to 4,
        "five" to 5,
        "nine" to 9,
        "one" to 1,
        "six" to 6,
        "two" to 2
    )
    val regexPattern = wordsToDigits.keys.joinToString("|") + "|\\d"
    val regex = Regex(regexPattern)

    return inputData.sumOf { line ->
        val matches = mutableListOf<String>()
        var currentLine = line

        while (regex.containsMatchIn(currentLine)) {
            val match = regex.find(currentLine)
            match?.let {
                matches.add(it.value)
                currentLine = if (it.range.last == 0) {
                    currentLine.removeRange(0, 1)
                } else {
                    currentLine.removeRange(0, it.range.first + 1)
                }
            }
        }

        val digits = matches.map {
            it.toIntOrNull() ?: wordsToDigits[it] ?: 0
        }

        val firstDigit = digits.firstOrNull() ?: 0
        val lastDigit = digits.lastOrNull() ?: 0
        firstDigit * 10 + lastDigit
    }
}
