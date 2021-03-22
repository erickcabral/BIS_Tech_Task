package ie.toxodev.bistask.tests

import android.content.SharedPreferences
import androidx.lifecycle.SavedStateHandle
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import ie.toxodev.bistask.baseJunitTest.BaseJunitTest
import ie.toxodev.bistask.fragViews.sourcesViewer.ViewModelDisplay
import ie.toxodev.bistask.supportClasses.Repository
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class ViewModelDisplayShould : BaseJunitTest() {

    private val viewModel: ViewModelDisplay
    private val mckSharedPreferences = mock<SharedPreferences>()
    private val mckSaveStateHandle = mock<SavedStateHandle>()
    private val mckRepository: Repository = mock()
    private val mckDispatcher: CoroutineDispatcher = mock()
    private val mckScope: CoroutineScope = mock()

    init {
        viewModel = ViewModelDisplay(mckSharedPreferences, mckRepository, mckSaveStateHandle)
    }

    @Test
    fun setup_repository() {
        this.viewModel.setupRepository(mckScope, mckDispatcher)
        verify(mckRepository, times(1)).setupScope(mckScope, mckDispatcher)
    }

    @Test
    fun call_fetch_repository_fetch_errors_method() = runBlockingTest {
        val hours = 10
        viewModel.fetchErrorSources(hours)
        verify(mckRepository, times(1)).fetchErrorSources(hours)
    }

    @Test
    fun get_errors_response() {
        this.viewModel.getErrorSources()
        verify(mckRepository, times(1)).lvdErrorSourcesResponse
    }

    @Test
    fun call_fetch_errors_sources_method() {
        val source = "Dummy Source"
        val hour = 10
        viewModel.fetchErrorDetails(source, hour)
        verify(mckRepository, times(1)).fetchErrorDetails(source, hour)
    }

    @Test
    fun check_for_timestamp_already_set_in_shared_preferences() {
        this.viewModel.checkTimeStamp()
        verify(mckSharedPreferences, times(1)).getInt("TIMESTAMP", 4)
    }

    @Test
    fun save_new_timestamp_in_shared_preferences() {
        val newStamp = 2
        whenever(
            mckSharedPreferences.edit()
        ).thenReturn(mock())

        this.viewModel.setNewTimestamp(newStamp)
        verify(mckSharedPreferences, times(1)).edit().putInt(ViewModelDisplay.TIMESTAMP, newStamp)
        assertTrue(this.viewModel.lvdNewStampResult.value!!)
    }
}