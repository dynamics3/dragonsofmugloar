package ee.alex.dragonsofmugloar.client

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import ee.alex.dragonsofmugloar.model.BattleResult
import ee.alex.dragonsofmugloar.model.BattleStatus.VICTORY
import ee.alex.dragonsofmugloar.model.Dragon
import ee.alex.dragonsofmugloar.model.Game
import ee.alex.dragonsofmugloar.model.Knight
import khttp.responses.Response
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.json.JSONObject
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.api.mockito.PowerMockito.doReturn
import org.powermock.api.mockito.PowerMockito.spy
import org.powermock.api.mockito.mockpolicies.Slf4jMockPolicy
import org.powermock.core.classloader.annotations.MockPolicy
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner

/**
 * @author Aleksei Kulitškov
 */
@RunWith(PowerMockRunner::class)
@MockPolicy(Slf4jMockPolicy::class)
@PrepareForTest(GameClient::class)
class GameClientTest {

  @Test
  fun getNew_ReturnsGame_IfStatusCodeIs200AndTextIsNotEmpty() {
    // given
    val knightJsonObject = JSONObject()
    knightJsonObject.put("name", "Sir Arthur")
    knightJsonObject.put("attack", 10)
    knightJsonObject.put("armor", 5)
    knightJsonObject.put("agility", 1)
    knightJsonObject.put("endurance", 4)

    val gameJsonObject = JSONObject()
    gameJsonObject.put("gameId", 10011)
    gameJsonObject.put("knight", knightJsonObject)

    val response: Response = mock()

    spy(GameClient::class.java)
    doReturn(response).`when`(GameClient::class.java, "getNewGameFromAPI")

    whenever(response.statusCode).thenReturn(200)
    whenever(response.text).thenReturn(gameJsonObject.toString())
    whenever(response.jsonObject).thenReturn(gameJsonObject)

    // when
    val result = GameClient.getNew()

    // then
    assertThat(result).isEqualTo(
        Game(
            gameId = 10011,
            knight = Knight(name = "Sir Arthur", attack = 10, armor = 5, agility = 1, endurance = 4)
        )
    )
  }

  @Test
  fun getNew_ReturnsNull_IfStatusCodeIs200ButTextIsEmpty() {
    // given
    val response: Response = mock()

    spy(GameClient::class.java)
    doReturn(response).`when`(GameClient::class.java, "getNewGameFromAPI")

    whenever(response.statusCode).thenReturn(200)
    whenever(response.text).thenReturn("")

    // when
    val result = GameClient.getNew()

    // then
    assertThat(result).isNull()
  }

  @Test
  fun getNew_ReturnsNull_IfStatusCodeIs400() {
    // given
    val response: Response = mock()

    spy(GameClient::class.java)
    doReturn(response).`when`(GameClient::class.java, "getNewGameFromAPI")

    whenever(response.statusCode).thenReturn(400)

    // when
    val result = GameClient.getNew()

    // then
    assertThat(result).isNull()
  }

  @Test
  fun battle_ReturnsBattleResult_IfStatusCodeIs200AndTextIsNotEmpty() {
    // given
    val gameId = 10011
    val dragon = Dragon(scaleThickness = 10, clawSharpness = 4, wingStrength = 2, fireBreath = 4)
    val battleResultJsonObject = JSONObject()
    battleResultJsonObject.put("status", "Victory")
    battleResultJsonObject.put("message", "Dragon won")

    val response: Response = mock()

    spy(GameClient::class.java)
    doReturn(response).`when`(GameClient::class.java, "sendBattleInfoToAPI", gameId, dragon)

    whenever(response.statusCode).thenReturn(200)
    whenever(response.text).thenReturn(battleResultJsonObject.toString())
    whenever(response.jsonObject).thenReturn(battleResultJsonObject)

    // when
    val result = GameClient.battle(gameId, dragon)

    // then
    assertThat(result).isEqualTo(BattleResult(VICTORY, "Dragon won"))
  }

  @Test
  fun battle_ThrowsRuntimeException_IfStatusCodeIs200ButTextIsEmpty() {
    // given
    val gameId = 10011
    val dragon = Dragon(scaleThickness = 10, clawSharpness = 4, wingStrength = 2, fireBreath = 4)
    val response: Response = mock()

    spy(GameClient::class.java)
    doReturn(response).`when`(GameClient::class.java, "sendBattleInfoToAPI", gameId, dragon)
    whenever(response.statusCode).thenReturn(200)
    whenever(response.text).thenReturn("")

    // when
    val result = catchThrowable { GameClient.battle(gameId, dragon) }

    // then
    assertThat(result).isInstanceOf(RuntimeException::class.java)
        .hasMessage("Could not retrieve game (id='10011') battle result")
  }

  @Test
  fun battle_ThrowsRuntimeException_IfStatusCodeIs400() {
    // given
    val gameId = 10011
    val dragon = Dragon(scaleThickness = 10, clawSharpness = 4, wingStrength = 2, fireBreath = 4)
    val response: Response = mock()

    spy(GameClient::class.java)
    doReturn(response).`when`(GameClient::class.java, "sendBattleInfoToAPI", gameId, dragon)

    whenever(response.statusCode).thenReturn(400)

    // when
    val result = catchThrowable { GameClient.battle(gameId, dragon) }

    // then
    assertThat(result).isInstanceOf(RuntimeException::class.java)
        .hasMessage("Could not retrieve game (id='10011') battle result")
  }

}
