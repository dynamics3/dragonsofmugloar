package ee.alex.dragonsofmugloar.model

import com.google.gson.annotations.SerializedName

/**
 * @author Aleksei Kulitškov
 */
enum class BattleStatus {
  @SerializedName("Victory")
  VICTORY,
  @SerializedName("Defeat")
  DEFEAT
}
