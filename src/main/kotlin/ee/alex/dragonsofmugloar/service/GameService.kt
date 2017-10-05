package ee.alex.dragonsofmugloar.service

import ee.alex.dragonsofmugloar.client.GameClient
import ee.alex.dragonsofmugloar.model.BattleResult
import ee.alex.dragonsofmugloar.model.Dragon
import ee.alex.dragonsofmugloar.model.DragonSkill
import ee.alex.dragonsofmugloar.model.DragonSkillType
import ee.alex.dragonsofmugloar.model.DragonSkillType.CLAW_SHARPNESS
import ee.alex.dragonsofmugloar.model.DragonSkillType.FIRE_BREATH
import ee.alex.dragonsofmugloar.model.DragonSkillType.SCALE_THICKNESS
import ee.alex.dragonsofmugloar.model.DragonSkillType.WING_STRENGTH
import ee.alex.dragonsofmugloar.model.Game
import ee.alex.dragonsofmugloar.model.Knight
import ee.alex.dragonsofmugloar.model.WeatherCondition
import ee.alex.dragonsofmugloar.model.WeatherCondition.HEAVY_RAIN
import ee.alex.dragonsofmugloar.model.WeatherCondition.LONG_DRY
import ee.alex.dragonsofmugloar.model.WeatherCondition.STORM

/**
 * @author Aleksei Kulitškov
 */
object GameService {

  @JvmStatic
  fun getNew(): Game? = GameClient.getNew()

  @JvmStatic
  fun getDragon(knight: Knight, weatherCondition: WeatherCondition): Dragon? {
    return when (weatherCondition) {
      STORM -> null
      HEAVY_RAIN -> Dragon(scaleThickness = 5, clawSharpness = 10, wingStrength = 5, fireBreath = 0)
      LONG_DRY -> Dragon(scaleThickness = 5, clawSharpness = 5, wingStrength = 5, fireBreath = 5)
      else -> getDragonByKnightSkills(knight)
    }
  }

  private fun getDragonByKnightSkills(knight: Knight): Dragon {
    val dragonSkills = getSortedDragonSkills(knight)
    upgradeDragonSkills(dragonSkills)

    return Dragon(
        getUpgradedSkillPoints(dragonSkills, SCALE_THICKNESS),
        getUpgradedSkillPoints(dragonSkills, CLAW_SHARPNESS),
        getUpgradedSkillPoints(dragonSkills, WING_STRENGTH),
        getUpgradedSkillPoints(dragonSkills, FIRE_BREATH)
    )
  }

  private fun getUpgradedSkillPoints(dragonSkills: List<DragonSkill>, skillType: DragonSkillType): Int {
    return dragonSkills.single { it.skillType == skillType }.points
  }

  private fun getSortedDragonSkills(knight: Knight): List<DragonSkill> {
    return listOf(
        DragonSkill(SCALE_THICKNESS, knight.attack),
        DragonSkill(CLAW_SHARPNESS, knight.armor),
        DragonSkill(WING_STRENGTH, knight.agility),
        DragonSkill(FIRE_BREATH, knight.endurance)
    ).sortedByDescending { it.points }
  }

  private fun upgradeDragonSkills(dragonSkills: List<DragonSkill>) {
    // add 2 points to the most upgraded skill of the knight
    dragonSkills[0].points += 2
    // decrease next two skills by 1 to achieve balance of 20 points
    dragonSkills[1].points -= 1
    dragonSkills[2].points -= 1
  }

  @JvmStatic
  fun battle(gameId: Int, dragon: Dragon?): BattleResult {
    return GameClient.battle(gameId, dragon)
  }

}
