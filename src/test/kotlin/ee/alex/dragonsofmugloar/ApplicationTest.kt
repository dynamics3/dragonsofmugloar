package ee.alex.dragonsofmugloar

import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.whenever
import ee.alex.dragonsofmugloar.model.BattleResult
import ee.alex.dragonsofmugloar.model.BattleStatus.VICTORY
import ee.alex.dragonsofmugloar.model.Dragon
import ee.alex.dragonsofmugloar.model.Game
import ee.alex.dragonsofmugloar.model.Knight
import ee.alex.dragonsofmugloar.model.WeatherCondition.NORMAL
import ee.alex.dragonsofmugloar.service.GameService
import ee.alex.dragonsofmugloar.service.WeatherService
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.api.mockito.PowerMockito.mockStatic
import org.powermock.api.mockito.PowerMockito.verifyStatic
import org.powermock.api.mockito.mockpolicies.Slf4jMockPolicy
import org.powermock.core.classloader.annotations.MockPolicy
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner


/**
 * @author Aleksei Kulitškov
 */
@RunWith(PowerMockRunner::class)
@MockPolicy(Slf4jMockPolicy::class)
@PrepareForTest(GameService::class, WeatherService::class)
class ApplicationTest {

  private companion object MockData {
    val knight = Knight(name = "knight1", attack = 8, armor = 5, agility = 3, endurance = 4)
    val game = Game(gameId = 10011, knight = knight)
    val weatherCondition = NORMAL
    val dragon = Dragon(scaleThickness = 10, clawSharpness = 4, wingStrength = 2, fireBreath = 4)
  }

  private lateinit var application: Application

  @Before
  fun setUp() {
    application = Application()

    mockStatic(GameService::class.java, WeatherService::class.java)

    whenever(GameService.getNew()).thenReturn(game)
    whenever(WeatherService.getWeatherCondition(10011)).thenReturn(weatherCondition)
    whenever(GameService.getDragon(knight, weatherCondition)).thenReturn(dragon)
    whenever(GameService.battle(10011, dragon)).thenReturn(BattleResult(VICTORY, "Dragon won"))
  }

  @Test
  fun run_RunsOnlyOneGame_IfGameLimitIsSetTo1AndVictory() {
    // when
    application.run(arrayOf("-limit", "1"))

    // then
    verifyStatic(GameService::class.java)
    GameService.getNew()
    GameService.getDragon(knight, weatherCondition)
    GameService.battle(10011, dragon)

    verifyStatic(WeatherService::class.java)
    WeatherService.getWeatherCondition(10011)
  }

  @Test
  fun run_RunsTenGames_IfGameLimitIsSetButIsNotANumber() {
    // when
    application.run(arrayOf("-limit", "not_a_number"))

    // then
    verifyStatic(GameService::class.java, times(10))
    GameService.getNew()
    GameService.getDragon(knight, weatherCondition)
    GameService.battle(10011, dragon)

    verifyStatic(WeatherService::class.java, times(10))
    WeatherService.getWeatherCondition(10011)
  }

  @Test
  fun run_RunsTenGames_IfGameLimitIsNotSet() {
    // when
    application.run(emptyArray())

    // then
    verifyStatic(GameService::class.java, times(10))
    GameService.getNew()
    GameService.getDragon(knight, weatherCondition)
    GameService.battle(10011, dragon)

    verifyStatic(WeatherService::class.java, times(10))
    WeatherService.getWeatherCondition(10011)
  }

}
