import java.io.File

data class BallRound(val color: String, val count: Int)

data class Game(val gameId: Int, val rounds: List<List<BallRound>>) {
    private val minRedCount = rounds.flatten().filter { it.color == "red" }.maxOfOrNull { it.count } ?: 0
    private val minGreenCount = rounds.flatten().filter { it.color == "green" }.maxOfOrNull { it.count } ?: 0
    private val minBlueCount = rounds.flatten().filter { it.color == "blue" }.maxOfOrNull { it.count } ?: 0
    val power = minRedCount * minGreenCount * minBlueCount
}

fun main() {
    val fileName = "input.txt" // Replace with the actual file name

    val games = mutableListOf<Game>()

    File(fileName).forEachLine { line ->
        val parts = line.split(":")
        if (parts.size == 2) {
            try {
                val gameId = parts[0].trim().split(" ")[1].toInt()
                val roundsData = parts[1].split(";").map { round ->
                    val roundData = round.trim().split(",").map { ball ->
                        val (count, color) = ball.trim().split(" ")
                        BallRound(color, count.toInt())
                    }
                    roundData
                }
                val game = Game(gameId, roundsData)
                games.add(game)
            } catch (e: NumberFormatException) {
                println("Invalid game ID format: $line")
            }
        }
    }

    val validGames = games.filter { game ->
        val validRounds = game.rounds.all { round ->
            val redCount = round.filter { it.color == "red" }.sumOf { it.count }
            val greenCount = round.filter { it.color == "green" }.sumOf { it.count }
            val blueCount = round.filter { it.color == "blue" }.sumOf { it.count }

            redCount <= 12 && greenCount <= 13 && blueCount <= 14
        }

        validRounds
    }

    val sumOfValidGameIds = validGames.sumOf { it.gameId }
    val sumOfGamePower = games.sumOf { it.power }

    println("Sum of Game IDs for valid games: $sumOfValidGameIds")
    println("Sum of Game Power: $sumOfGamePower")
}