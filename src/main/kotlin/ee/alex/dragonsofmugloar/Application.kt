package ee.alex.dragonsofmugloar

import ee.alex.dragonsofmugloar.helper.loggerFor
import ee.alex.dragonsofmugloar.model.BattleStatus.VICTORY
import ee.alex.dragonsofmugloar.service.GameService
import ee.alex.dragonsofmugloar.service.WeatherService
import java.time.Instant


/**
 * @author Aleksei Kulitškov
 */
class Application {

  private companion object {
    const val DEFAULT_GAME_LIMIT = 10
  }

  private var numberOfGames = 0
  private var numberOfVictories = 0

  private val log = loggerFor(this::class)

  fun run(args: Array<String>) {
    val gameLimit = getGameLimitFromArgs(args)

    log.info("========================================")
    log.info("  GAME SET - ${Instant.now()}")

    (1..gameLimit).forEach {
      GameService.getNew()?.let { game ->
        log.info("========================================")

        val knight = game.knight
        val weatherCondition = WeatherService.getWeatherCondition(game.gameId)

        val dragon = GameService.getDragon(knight, weatherCondition)
        val result = GameService.battle(game.gameId, dragon)

        if (result.status == VICTORY) numberOfVictories++

        log.info("Game: ${game.gameId}; Knight: $knight; Weather: $weatherCondition; Dragon: $dragon; result: ${result.status}")
        log.info("Battle explanation: ${result.message}")
      }

      numberOfGames++
    }

    log.info("================ TOTAL =====================")
    log.info("Games: $numberOfGames, victories: $numberOfVictories, win rate: ${getWinRate()}%")
  }

  private fun getGameLimitFromArgs(args: Array<String>): Int {
    if (args.isEmpty() || !args.contains("-limit")) return DEFAULT_GAME_LIMIT

    return try {
      args[1].toInt()
    } catch (ex: NumberFormatException) {
      DEFAULT_GAME_LIMIT
    }
  }

  private fun getWinRate() = numberOfVictories.toDouble().div(numberOfGames).times(100).toInt()

}
