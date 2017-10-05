package ee.alex.dragonsofmugloar.service

import com.nhaarman.mockito_kotlin.whenever
import ee.alex.dragonsofmugloar.client.GameClient
import ee.alex.dragonsofmugloar.model.BattleResult
import ee.alex.dragonsofmugloar.model.BattleStatus.DEFEAT
import ee.alex.dragonsofmugloar.model.BattleStatus.VICTORY
import ee.alex.dragonsofmugloar.model.Dragon
import ee.alex.dragonsofmugloar.model.Game
import ee.alex.dragonsofmugloar.model.Knight
import ee.alex.dragonsofmugloar.model.WeatherCondition.FOG
import ee.alex.dragonsofmugloar.model.WeatherCondition.HEAVY_RAIN
import ee.alex.dragonsofmugloar.model.WeatherCondition.LONG_DRY
import ee.alex.dragonsofmugloar.model.WeatherCondition.NORMAL
import ee.alex.dragonsofmugloar.model.WeatherCondition.STORM
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.api.mockito.PowerMockito.mockStatic
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
class GameServiceTest {

  @Test
  fun getNew_ReturnsGame_IfGameClientReturnsGame() {
    // given
    val game = Game(
        gameId = 10011,
        knight = Knight(name = "knight1", attack = 8, armor = 5, agility = 3, endurance = 4)
    )

    mockStatic(GameClient::class.java)
    whenever(GameClient.getNew()).thenReturn(game)

    // when
    val result = GameService.getNew()

    // then
    assertThat(result).isSameAs(game)
  }

  @Test
  fun getNew_ReturnsNull_IfGameClientReturnsNull() {
    // given
    mockStatic(GameClient::class.java)
    whenever(GameClient.getNew()).thenReturn(null)

    // when
    val result = GameService.getNew()

    // then
    assertThat(result).isNull()
  }

  @Test
  fun getDragon_ReturnsNull_IfWeatherConditionIsStorm() {
    // given
    val knight = Knight(name = "knight1", attack = 8, armor = 5, agility = 3, endurance = 4)

    // when
    val result = GameService.getDragon(knight, STORM)

    // then
    assertThat(result).isNull()
  }

  @Test
  fun getDragon_ReturnsDragonWithTenPointsOfClawSharpnessAndZeroFireBreath_IfWeatherConditionIsHeavyRain() {
    // given
    val knight = Knight(name = "knight1", attack = 8, armor = 5, agility = 3, endurance = 4)

    // when
    val result = GameService.getDragon(knight, HEAVY_RAIN)

    // then
    assertThat(result).isEqualTo(Dragon(scaleThickness = 5, clawSharpness = 10, wingStrength = 5, fireBreath = 0))
  }

  @Test
  fun getDragon_ReturnsDragonWithBalancedPointsOfFiveEach_IfWeatherConditionIsLongDry() {
    // given
    val knight = Knight(name = "knight1", attack = 8, armor = 5, agility = 3, endurance = 4)

    // when
    val result = GameService.getDragon(knight, LONG_DRY)

    // then
    assertThat(result).isEqualTo(Dragon(scaleThickness = 5, clawSharpness = 5, wingStrength = 5, fireBreath = 5))
  }

  @Test
  fun getDragon_ReturnsDragonWithSkillsBasedOnKnightSkills_IfWeatherConditionIsFog() {
    // given
    val knight = Knight(name = "knight1", attack = 8, armor = 5, agility = 3, endurance = 4)

    // when
    val result = GameService.getDragon(knight, FOG)

    // then
    assertThat(result).isEqualTo(Dragon(scaleThickness = 10, clawSharpness = 4, wingStrength = 3, fireBreath = 3))
  }

  @Test
  fun getDragon_ReturnsDragonWithSkillsBasedOnKnightSkills_IfWeatherConditionIsNormal() {
    // given
    val knight = Knight(name = "knight1", attack = 3, armor = 7, agility = 5, endurance = 5)

    // when
    val result = GameService.getDragon(knight, NORMAL)

    // then
    assertThat(result).isEqualTo(Dragon(scaleThickness = 3, clawSharpness = 9, wingStrength = 4, fireBreath = 4))
  }

  @Test
  fun battle_CallsGameClientMethodAndReturnsBattleResult_IfDragonIsNotNull() {
    // given
    val gameId = 10011
    val dragon = Dragon(scaleThickness = 10, clawSharpness = 4, wingStrength = 2, fireBreath = 4)
    val battleResult = BattleResult(VICTORY, "Dragon won")

    mockStatic(GameClient::class.java)
    whenever(GameClient.battle(gameId, dragon)).thenReturn(battleResult)

    // when
    val result = GameService.battle(gameId, dragon)

    // then
    assertThat(result).isSameAs(battleResult)
  }

  @Test
  fun battle_CallsGameClientMethodAndReturnsBattleResult_IfDragonIsNull() {
    // given
    val gameId = 10011
    val battleResult = BattleResult(DEFEAT, "Dragon lost")

    mockStatic(GameClient::class.java)
    whenever(GameClient.battle(gameId, null)).thenReturn(battleResult)

    // when
    val result = GameService.battle(gameId, null)

    // then
    assertThat(result).isSameAs(battleResult)
  }

}
