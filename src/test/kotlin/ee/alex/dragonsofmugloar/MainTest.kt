package ee.alex.dragonsofmugloar

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import ee.alex.dragonsofmugloar.Main.Companion.main
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.api.mockito.PowerMockito.whenNew
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner

@RunWith(PowerMockRunner::class)
@PrepareForTest(Main::class)
class MainTest {

  @Test
  fun main_RunsApplication() {
    // given
    val applicationMock: Application = mock()
    val args = arrayOf("-limit", "10")

    whenNew(Application::class.java).withNoArguments().thenReturn(applicationMock)

    // when
    main(args)

    // then
    verify(applicationMock).run(args)
  }

}
