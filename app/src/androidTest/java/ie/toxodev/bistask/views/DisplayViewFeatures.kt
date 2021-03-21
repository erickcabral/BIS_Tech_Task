package ie.toxodev.bistask.views

import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import ie.toxodev.bistask.R
import org.junit.Test

class DisplayViewFeatures {

    @Test
    fun display_full_layout(){
        assertDisplayed(R.id.layoutDisplay)
    }
}