package ie.toxodev.bistask.tests

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import ie.toxodev.bistask.baseJunitTest.BaseJunitTest
import ie.toxodev.bistask.fragViews.ViewModelDisplay
import ie.toxodev.bistask.supportClasses.Repository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class ViewModelDisplayShould : BaseJunitTest() {

    private val viewModel: ViewModelDisplay
    private val mckRepository: Repository = mock()
    private val mckDispatcher: CoroutineDispatcher = mock()
    private val mckScope: CoroutineScope = mock()

    init {
        viewModel = ViewModelDisplay(mckRepository)
    }

    @Test
    fun setup_repository() {
        this.viewModel.setupRepository(mckScope, mckDispatcher)
        verify(mckRepository, times(1)).setupScope(mckScope, mckDispatcher)
    }

    @Test
    fun call_fetch_repository_fetch_errors_method() = runBlockingTest {
        val hours = 10
        viewModel.fetchErrors(hours)
        verify(mckRepository, times(1)).fetchErrors(hours)
    }

    @Test
    fun get_errors_response() {
        this.viewModel.getErrorsResponse()
        verify(mckRepository, times(1)).lvdErrorsResponse
    }
}