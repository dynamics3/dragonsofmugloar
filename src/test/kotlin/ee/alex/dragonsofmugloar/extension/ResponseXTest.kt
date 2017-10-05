package ee.alex.dragonsofmugloar.extension

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import ee.alex.dragonsofmugloar.extension.toModel
import ee.alex.dragonsofmugloar.model.Dragon
import ee.alex.dragonsofmugloar.model.Game
import ee.alex.dragonsofmugloar.model.Knight
import khttp.responses.Response
import org.assertj.core.api.Assertions.assertThat
import org.json.JSONObject
import org.junit.Test

/**
 * @author Aleksei Kulitškov
 */
class ResponseXTest {

  @Test
  fun toModel_DragonWithCorrectFieldValues_IfResponseReturnsDragonJsonObject() {
    // given
    val response: Response = mock()

    val dragonJsonObject = JSONObject()
    dragonJsonObject.put("scaleThickness", 5)
    dragonJsonObject.put("clawSharpness", 4)
    dragonJsonObject.put("wingStrength", 1)
    dragonJsonObject.put("fireBreath", 10)
    whenever(response.jsonObject).thenReturn(dragonJsonObject)

    // when
    val result = response.toModel<Dragon>()

    // then
    assertThat(result).isEqualTo(Dragon(scaleThickness = 5, clawSharpness = 4, wingStrength = 1, fireBreath = 10))
  }

  @Test
  fun toModel_GameWithCorrectFieldValues_IfResponseReturnsGameJsonObject() {
    // given
    val response: Response = mock()

    val knightJsonObject = JSONObject()
    knightJsonObject.put("name", "Sir Arthur")
    knightJsonObject.put("attack", 10)
    knightJsonObject.put("armor", 5)
    knightJsonObject.put("agility", 1)
    knightJsonObject.put("endurance", 4)

    val gameJsonObject = JSONObject()
    gameJsonObject.put("gameId", 10011)
    gameJsonObject.put("knight", knightJsonObject)
    whenever(response.jsonObject).thenReturn(gameJsonObject)

    // when
    val result = response.toModel<Game>()

    // then
    assertThat(result).isEqualTo(
        Game(
            gameId = 10011,
            knight = Knight(name = "Sir Arthur", attack = 10, armor = 5, agility = 1, endurance = 4)
        )
    )
  }

}
