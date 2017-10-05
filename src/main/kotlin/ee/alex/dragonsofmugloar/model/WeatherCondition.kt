package ee.alex.dragonsofmugloar.model

/**
 * @author Aleksei Kulitškov
 */
enum class WeatherCondition(val code: String) {

  LONG_DRY("T E"),
  FOG("FUNDEFINEDG"),
  NORMAL("NMR"),
  HEAVY_RAIN("HVA"),
  STORM("SRO");

  companion object {
    fun fromCode(code: String) = WeatherCondition.values().singleOrNull { it.code == code }
  }

}
