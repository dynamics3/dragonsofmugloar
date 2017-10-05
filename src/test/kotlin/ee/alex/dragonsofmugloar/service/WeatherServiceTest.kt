package ee.alex.dragonsofmugloar.service

import com.nhaarman.mockito_kotlin.whenever
import ee.alex.dragonsofmugloar.client.WeatherClient
import ee.alex.dragonsofmugloar.model.WeatherCondition.NORMAL
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.api.mockito.PowerMockito.mockStatic
import org.powermock.api.mockito.PowerMockito.verifyStatic
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner

/**
 * @author Aleksei Kulitškov
 */
@RunWith(PowerMockRunner::class)
@PrepareForTest(WeatherClient::class)
class WeatherServiceTest {

  @Test
  fun getWeatherCondition_NormalWeatherCondition_IfWeatherClientReturnsNormalWeatherCondition() {
    // given
    mockStatic(WeatherClient::class.java)
    whenever(WeatherClient.getWeatherCondition(10011)).thenReturn(NORMAL)

    // when
    val result = WeatherService.getWeatherCondition(10011)

    // when
    assertThat(result).isEqualTo(NORMAL)

    verifyStatic(WeatherClient::class.java)
    WeatherClient.getWeatherCondition(10011)
  }

}
