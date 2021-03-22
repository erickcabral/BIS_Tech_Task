package ie.toxodev.bistask.views

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.internal.performActionOnView
import ie.toxodev.bistask.R
import ie.toxodev.bistask.baseAndroidTest.BaseAndroidTest
import ie.toxodev.bistask.recyclerAdapters.AdapterErrorSources
import org.junit.Before
import org.junit.Test

class DisplayViewFeatures : BaseAndroidTest() {

    @Before
    fun setup() {
        Thread.sleep(1000)
    }

    @Test
    fun display_full_layout() {
        assertDisplayed(R.id.layoutSourceDisplay)
        assertDisplayed(R.id.btnTimeStamp)
        assertDisplayed(R.id.recyclerSources)
    }

    @Test
    fun open_timestamp_setup_when_button_clicked() {
        performActionOnView(withId(R.id.btnTimeStamp), click())
        assertDisplayed(R.id.layoutDialog)
    }

    @Test
    fun open_error_detail_when_recycler_item_clicked() {
        onView(withId(R.id.recyclerSources)).check(
            matches(hasMinimumChildCount(1))
        )
        RecyclerViewActions.scrollToPosition<AdapterErrorSources.VieHolderSources>(9)
        RecyclerViewActions.actionOnItemAtPosition<AdapterErrorSources.VieHolderSources>(9, click())
        Thread.sleep(1000)
        assertDisplayed(R.id.layoutErrorDetails)
    }
}