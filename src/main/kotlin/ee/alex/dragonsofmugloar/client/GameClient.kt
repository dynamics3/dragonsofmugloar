package ee.alex.dragonsofmugloar.client

import ee.alex.dragonsofmugloar.extension.toModel
import ee.alex.dragonsofmugloar.helper.loggerFor
import ee.alex.dragonsofmugloar.model.BattleResult
import ee.alex.dragonsofmugloar.model.Dragon
import ee.alex.dragonsofmugloar.model.Game
import khttp.get
import khttp.put
import khttp.responses.Response

/**
 * @author Aleksei Kulitškov
 */
object GameClient {

  private val log = loggerFor(this::class)

  private const val GAME_URL = "http://www.dragonsofmugloar.com/api/game"

  @JvmStatic
  fun getNew(): Game? {
    val response = getNewGameFromAPI()

    return if (response.statusCode == 200 && response.text.isNotEmpty()) {
      response.toGame()
    } else {
      log.error("Could not retrieve new game")
      null
    }
  }

  @JvmStatic
  private fun getNewGameFromAPI() = get(GAME_URL)

  private fun Response.toGame() = this.toModel<Game>()

  @JvmStatic
  fun battle(gameId: Int, dragon: Dragon?): BattleResult {
    val response = sendBattleInfoToAPI(gameId, dragon)

    return if (response.statusCode == 200 && response.text.isNotEmpty()) {
      response.toBattleResult()
    } else {
      throw RuntimeException("Could not retrieve game (id='$gameId') battle result")
    }
  }

  @JvmStatic
  private fun sendBattleInfoToAPI(gameId: Int, dragon: Dragon?)
      = put(url = "$GAME_URL/$gameId/solution", json = mapOf("dragon" to dragon))

  private fun Response.toBattleResult() = this.toModel<BattleResult>()

}
