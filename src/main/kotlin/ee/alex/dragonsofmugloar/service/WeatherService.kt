package ee.alex.dragonsofmugloar.service

import ee.alex.dragonsofmugloar.client.WeatherClient
import ee.alex.dragonsofmugloar.model.WeatherCondition

/**
 * @author Aleksei Kulitškov
 */
object WeatherService {

  @JvmStatic
  fun getWeatherCondition(gameId: Int): WeatherCondition {
    return WeatherClient.getWeatherCondition(gameId)
  }

}
