package ie.toxodev.bistask.views

import androidx.navigation.findNavController
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import ie.toxodev.bistask.R
import ie.toxodev.bistask.baseAndroidTest.BaseAndroidTest
import ie.toxodev.bistask.fragViews.sourcesViewer.ViewDisplayDirections
import org.junit.Before
import org.junit.Test

class ErrorDetailsFeatures : BaseAndroidTest() {

    @Before
    fun setup() {
        mainActivityScenarioRule.scenario.onActivity {
            ViewDisplayDirections.toErrorDetails("MSTProc-PULSRISK02", 4).run {
                it.findNavController(R.id.navHostMain).navigate(this)
            }
            Thread.sleep(2000)
        }
    }

    @Test
    fun display_layout() {
        assertDisplayed(R.id.layoutErrorDetails)
        assertDisplayed(R.id.recyclerErrors)
        assertDisplayed(R.id.linSource)
        assertDisplayed(R.id.linTotalErrors)
        assertDisplayed(R.id.tvErrorSource)
    }

    @Test
    fun load_recycler_view() {
        Thread.sleep(1000)
        onView(withId(R.id.recyclerErrors)).check(
            matches(hasMinimumChildCount(1))
        )
    }

    @Test
    fun pop_view_when_back_pressed() {
        pressBack()
        assertDisplayed(R.id.layoutSourceDisplay)
    }
}