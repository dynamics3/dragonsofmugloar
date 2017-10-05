package ee.alex.dragonsofmugloar.client

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import ee.alex.dragonsofmugloar.model.WeatherCondition.FOG
import ee.alex.dragonsofmugloar.model.WeatherCondition.HEAVY_RAIN
import ee.alex.dragonsofmugloar.model.WeatherCondition.LONG_DRY
import ee.alex.dragonsofmugloar.model.WeatherCondition.NORMAL
import ee.alex.dragonsofmugloar.model.WeatherCondition.STORM
import khttp.responses.Response
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner

/**
 * @author Aleksei Kulitškov
 */
@RunWith(PowerMockRunner::class)
@PrepareForTest(WeatherClient::class)
class WeatherClientTest {

  @Test
  fun getWeatherCondition_ReturnsLongDryWeatherCondition_IfStatusCodeIs200AndTextIsNotEmptyAndWeatherCodeIsTE() {
    // given
    val gameId = 10011
    val weatherXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><report><time/><coords><x>3916.234</x><y>169.914</y>" +
        "<z>6.33</z></coords><code>T E</code><message>Dry message</message><varX-Rating>12</varX-Rating></report>"

    val response: Response = mock()

    PowerMockito.spy(WeatherClient::class.java)
    PowerMockito.doReturn(response).`when`(WeatherClient::class.java, "getWeatherConditionFromAPI", gameId)

    whenever(response.statusCode).thenReturn(200)
    whenever(response.text).thenReturn(weatherXml)

    // when
    val result = WeatherClient.getWeatherCondition(gameId)

    // then
    assertThat(result).isEqualTo(LONG_DRY)
  }

  @Test
  fun getWeatherCondition_ReturnsFogWeatherCondition_IfStatusCodeIs200AndTextIsNotEmptyAndWeatherCodeIsFUNDEFINEDG() {
    // given
    val gameId = 10011
    val weatherXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><report><time/><coords><x>3916.234</x><y>169.914</y>" +
        "<z>6.33</z></coords><code>FUNDEFINEDG</code><message>Fog message</message><varX-Rating>12</varX-Rating></report>"

    val response: Response = mock()

    PowerMockito.spy(WeatherClient::class.java)
    PowerMockito.doReturn(response).`when`(WeatherClient::class.java, "getWeatherConditionFromAPI", gameId)

    whenever(response.statusCode).thenReturn(200)
    whenever(response.text).thenReturn(weatherXml)

    // when
    val result = WeatherClient.getWeatherCondition(gameId)

    // then
    assertThat(result).isEqualTo(FOG)
  }

  @Test
  fun getWeatherCondition_ReturnsNormalWeatherCondition_IfStatusCodeIs200AndTextIsNotEmptyAndWeatherCodeIsNMR() {
    // given
    val gameId = 10011
    val weatherXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><report><time/><coords><x>3916.234</x><y>169.914</y>" +
        "<z>6.33</z></coords><code>NMR</code><message>Normal message</message><varX-Rating>12</varX-Rating></report>"

    val response: Response = mock()

    PowerMockito.spy(WeatherClient::class.java)
    PowerMockito.doReturn(response).`when`(WeatherClient::class.java, "getWeatherConditionFromAPI", gameId)

    whenever(response.statusCode).thenReturn(200)
    whenever(response.text).thenReturn(weatherXml)

    // when
    val result = WeatherClient.getWeatherCondition(gameId)

    // then
    assertThat(result).isEqualTo(NORMAL)
  }

  @Test
  fun getWeatherCondition_ReturnsHeavyRainWeatherCondition_IfStatusCodeIs200AndTextIsNotEmptyAndWeatherCodeIsHVA() {
    // given
    val gameId = 10011
    val weatherXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><report><time/><coords><x>3916.234</x><y>169.914</y>" +
        "<z>6.33</z></coords><code>HVA</code><message>Heavy rain message</message><varX-Rating>12</varX-Rating></report>"

    val response: Response = mock()

    PowerMockito.spy(WeatherClient::class.java)
    PowerMockito.doReturn(response).`when`(WeatherClient::class.java, "getWeatherConditionFromAPI", gameId)

    whenever(response.statusCode).thenReturn(200)
    whenever(response.text).thenReturn(weatherXml)

    // when
    val result = WeatherClient.getWeatherCondition(gameId)

    // then
    assertThat(result).isEqualTo(HEAVY_RAIN)
  }

  @Test
  fun getWeatherCondition_ReturnsStormWeatherCondition_IfStatusCodeIs200AndTextIsNotEmptyAndWeatherCodeIsSRO() {
    // given
    val gameId = 10011
    val weatherXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><report><time/><coords><x>3916.234</x><y>169.914</y>" +
        "<z>6.33</z></coords><code>SRO</code><message>Storm message</message><varX-Rating>12</varX-Rating></report>"

    val response: Response = mock()

    PowerMockito.spy(WeatherClient::class.java)
    PowerMockito.doReturn(response).`when`(WeatherClient::class.java, "getWeatherConditionFromAPI", gameId)

    whenever(response.statusCode).thenReturn(200)
    whenever(response.text).thenReturn(weatherXml)

    // when
    val result = WeatherClient.getWeatherCondition(gameId)

    // then
    assertThat(result).isEqualTo(STORM)
  }

  @Test
  fun getWeatherCondition_ThrowsRuntimeException_IfStatusCodeIs200ButTextIsEmpty() {
    // given
    val gameId = 10011
    val response: Response = mock()

    PowerMockito.spy(WeatherClient::class.java)
    PowerMockito.doReturn(response).`when`(WeatherClient::class.java, "getWeatherConditionFromAPI", gameId)

    whenever(response.statusCode).thenReturn(200)
    whenever(response.text).thenReturn("")

    // when
    val result = catchThrowable { WeatherClient.getWeatherCondition(gameId) }

    // then
    assertThat(result).isInstanceOf(RuntimeException::class.java)
        .hasMessage("Could not retrieve game (id='10011') weather condition")
  }

  @Test
  fun getWeatherCondition_ThrowsRuntimeException_IfStatusCodeIs400() {
    // given
    val gameId = 10011
    val response: Response = mock()

    PowerMockito.spy(WeatherClient::class.java)
    PowerMockito.doReturn(response).`when`(WeatherClient::class.java, "getWeatherConditionFromAPI", gameId)

    whenever(response.statusCode).thenReturn(400)

    // when
    val result = catchThrowable { WeatherClient.getWeatherCondition(gameId) }

    // then
    assertThat(result).isInstanceOf(RuntimeException::class.java)
        .hasMessage("Could not retrieve game (id='10011') weather condition")
  }

  @Test
  fun getWeatherCondition_ThrowsRuntimeException_IfNoCodeTag() {
    // given
    val gameId = 10011
    val weatherXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><report><time/><coords><x>3916.234</x><y>169.914</y>" +
        "<z>6.33</z></coords><message>Storm message</message><varX-Rating>12</varX-Rating></report>"

    val response: Response = mock()

    PowerMockito.spy(WeatherClient::class.java)
    PowerMockito.doReturn(response).`when`(WeatherClient::class.java, "getWeatherConditionFromAPI", gameId)

    whenever(response.statusCode).thenReturn(200)
    whenever(response.text).thenReturn(weatherXml)

    // when
    val result = catchThrowable { WeatherClient.getWeatherCondition(gameId) }

    // then
    assertThat(result).isInstanceOf(RuntimeException::class.java)
        .hasMessage("Weather condition code is missing")
  }

  @Test
  fun getWeatherCondition_ThrowsRuntimeException_IfUnknownCode() {
    // given
    val gameId = 10011
    val weatherXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><report><time/><coords><x>3916.234</x><y>169.914</y>" +
        "<z>6.33</z></coords><code>NON_EXISTING</code><message>Storm message</message><varX-Rating>12</varX-Rating></report>"

    val response: Response = mock()

    PowerMockito.spy(WeatherClient::class.java)
    PowerMockito.doReturn(response).`when`(WeatherClient::class.java, "getWeatherConditionFromAPI", gameId)

    whenever(response.statusCode).thenReturn(200)
    whenever(response.text).thenReturn(weatherXml)

    // when
    val result = catchThrowable { WeatherClient.getWeatherCondition(gameId) }

    // then
    assertThat(result).isInstanceOf(RuntimeException::class.java)
        .hasMessage("Unknown weather condition")
  }

  @Test
  fun getWeatherCondition_ReturnFirstOccurrenceThatIsStormWeatherCondition_IfMultipleCodeTags() {
    // given
    val gameId = 10011
    val weatherXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><report><time/><coords><x>3916.234</x><y>169.914</y>" +
        "<z>6.33</z></coords><code>SRO</code><code>NMR</code><message>Storm message</message><varX-Rating>12</varX-Rating></report>"

    val response: Response = mock()

    PowerMockito.spy(WeatherClient::class.java)
    PowerMockito.doReturn(response).`when`(WeatherClient::class.java, "getWeatherConditionFromAPI", gameId)

    whenever(response.statusCode).thenReturn(200)
    whenever(response.text).thenReturn(weatherXml)

    // when
    val result = WeatherClient.getWeatherCondition(gameId)

    // then
    assertThat(result).isEqualTo(STORM)
  }

}
