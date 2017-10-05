package ee.alex.dragonsofmugloar.helper

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.slf4j.Logger

/**
 * @author Aleksei Kulitškov
 */
class LoggerHelperTest {

  @Test
  fun loggerFor_LoggerForMyTestClass_IfUsingMyTestClass() {
    // when
    val result = loggerFor(MyTestClass::class)

    // then
    assertThat(result).isInstanceOf(Logger::class.java)
    assertThat(result.name).isEqualTo("ee.alex.dragonsofmugloar.helper.LoggerHelperTest\$MyTestClass")
  }

  internal class MyTestClass

}
