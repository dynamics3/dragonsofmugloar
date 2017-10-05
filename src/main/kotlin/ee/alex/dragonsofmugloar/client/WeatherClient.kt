package ee.alex.dragonsofmugloar.client

import ee.alex.dragonsofmugloar.model.WeatherCondition
import khttp.get
import khttp.responses.Response

/**
 * @author Aleksei Kulitškov
 */
object WeatherClient {

  private const val WEATHER_CODE_REGEX = "<code>(.*?)</code>"

  @JvmStatic
  fun getWeatherCondition(gameId: Int): WeatherCondition {
    val response = getWeatherConditionFromAPI(gameId)

    return if (response.statusCode == 200 && response.text.isNotEmpty()) {
      response.toWeatherCondition()
    } else {
      throw RuntimeException("Could not retrieve game (id='$gameId') weather condition")
    }
  }

  @JvmStatic
  private fun getWeatherConditionFromAPI(gameId: Int)
      = get("http://www.dragonsofmugloar.com/weather/api/report/$gameId")

  private fun Response.toWeatherCondition(): WeatherCondition {
    val result = Regex(WEATHER_CODE_REGEX).find(this.text)
        ?: throw RuntimeException("Weather condition code is missing")

    return WeatherCondition.fromCode(result.groupValues.last()) ?: throw RuntimeException("Unknown weather condition")
  }

}
