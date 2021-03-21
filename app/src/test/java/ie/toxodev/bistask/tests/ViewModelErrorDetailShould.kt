package ie.toxodev.bistask.tests

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import ie.toxodev.bistask.baseJunitTest.BaseJunitTest
import ie.toxodev.bistask.fragViews.errorsDetailViewer.ViewModelErrorDetails
import ie.toxodev.bistask.supportClasses.Repository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import org.junit.Test

class ViewModelErrorDetailShould:BaseJunitTest() {

    private val viewModel:ViewModelErrorDetails
    private val mckRepository = mock<Repository>()
    private val mckScope = mock<CoroutineScope>()
    private val mckDispatcher = mock<CoroutineDispatcher>()

    init {
        this.viewModel = ViewModelErrorDetails(mckRepository)
    }

    @Test
    fun setup_repository(){
        this.viewModel.setupRepository(mckScope, mckDispatcher)
    }

    @Test
    fun call_fetch_error_details_method(){
        val errorSource = "Dummy Source"
        val hours = 4
        this.viewModel.fetchErrorDetails(errorSource, hours)
        verify(mckRepository, times(1)).fetchErrorDetails(errorSource, hours)
    }

    @Test
    fun get_error_details_response(){
        this.viewModel.getErrorDetailsResponse()
        verify(mckRepository, times(1)).lvdErrorDetailsResponse
    }
}