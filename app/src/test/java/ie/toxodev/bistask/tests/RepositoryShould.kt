package ie.toxodev.bistask.tests

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import getValueForTest
import ie.toxodev.bistask.baseJunitTest.BaseJunitTest
import ie.toxodev.bistask.supportClasses.Repository
import ie.toxodev.bistask.supportClasses.responses.errorResponse.ErrorDetailModel
import ie.toxodev.bistask.supportClasses.responses.errorResponse.ErrorDetailResponse
import ie.toxodev.bistask.supportClasses.responses.sourceErrorResponse.ErrorSourcesResponse
import ie.toxodev.bistask.supportClasses.responses.sourceErrorResponse.SourceErrorModel
import ie.toxodev.bistask.supportClasses.service.BisService
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class RepositoryShould : BaseJunitTest() {

    private val repository: Repository
    private val mckApi: BisService = mock()
    private val scope = mainCoroutineScopeRule
    private val dispatcher = mainCoroutineScopeRule.dispatcher


    init {
        this.repository = Repository(mckApi)
        this.repository.setupScope(scope, dispatcher)
    }

    @Test
    fun setup_repository_scope() {
        assertNotNull(repository.scope)
    }
    //==================== ERROR TEST ================= //

    private val sourceModel1 = SourceErrorModel(1, "dummy 1")
    private val sourceModel2 = SourceErrorModel(44, "dummy 1")

    private val sourceErrorResponse = ErrorSourcesResponse().apply {
        this.add(sourceModel1)
        this.add(sourceModel2)
    }

    @Test
    fun fetch_error_sources_and_update_live_data() = runBlockingTest{
        val hours = 4
        whenever(mckApi.fetchErrorSources(hours)).thenReturn(flow {
            emit(Result.success(sourceErrorResponse))
        })
        repository.fetchErrorSources(hours)
        verify(mckApi, times(1)).fetchErrorSources(hours)

        repository.lvdErrorSourcesResponse.getValueForTest().run {
            assertNotNull(this)
            assertNotNull(this!!.getOrNull())
            this.getOrNull()!!.run {
                assertEquals(sourceErrorResponse, this)
            }
        }
    }

    // ============================= ERROR DETAILS TEST ============================ //


    private val errorModel1 = ErrorDetailModel().apply {
        this.date = "18-03-21"
        this.name = "Source Dummy Name"
    }
    private val errorModel2 = ErrorDetailModel().apply {
        date = "18-03-21"
        this.name = "Source Dummy Name 2"
    }
    private val errorDetailsResponse = ErrorDetailResponse().apply {
        this.add(errorModel1)
        this.add(errorModel2)
    }

    @Test
    fun fetch_errors_details_and_update_live_data()= runBlockingTest {
        val source = "DummySource"
        val hours = 24
        whenever(mckApi.fetchErrorDetails(source, hours)).thenReturn(flow {
            emit(
                Result.success(errorDetailsResponse)
            )
        })
        repository.fetchErrorDetails(source, hours)
        verify(mckApi, times(1)).fetchErrorDetails(source, hours)

        repository.lvdErrorDetailsResponse.getValueForTest().run {
            assertNotNull(this)
        }
    }
}