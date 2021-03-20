package ie.toxodev.bistask.tests

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import getValueForTest
import ie.toxodev.bistask.baseJunitTest.BaseJunitTest
import ie.toxodev.bistask.supportClasses.Repository
import ie.toxodev.bistask.supportClasses.responses.errorResponse.ErrorResponse
import ie.toxodev.bistask.supportClasses.responses.errorResponse.ErrorResponseItem
import ie.toxodev.bistask.supportClasses.service.BisService
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.flow.flow
import org.junit.Test

class RepositoryShould : BaseJunitTest() {

    private val repository: Repository
    private val mckApi: BisService = mock()
    private val scope = mainCoroutineScopeRule
    private val dispatcher = mainCoroutineScopeRule.dispatcher


    private val errorItem = ErrorResponseItem().apply {
        this.noErrors = 1
        this.source = "dummy 1"
    }
    private val errorItem2 = ErrorResponseItem().apply {
        this.noErrors = 2
        this.source = "dummy 2"
    }

    private val errorResponse = ErrorResponse().apply {
        this.add(errorItem)
        this.add(errorItem2)
    }

    init {
        this.repository = Repository(mckApi)
        this.repository.setupScope(scope, dispatcher)
    }

    @Test
    fun setup_repository_scope() {
        assertNotNull(repository.scope)
    }

    @Test
    fun fetch_error_and_update_live_data() {
        val hours = 4
        whenever(mckApi.fetchErrors(hours)).thenReturn(flow {
            emit(Result.success(errorResponse))
        })
        repository.fetchErrors(hours)
        verify(mckApi, times(1)).fetchErrors(hours)

        repository.lvdErrorsResponse.getValueForTest().run {
            assertNotNull(this)
            assertNotNull(this!!.getOrNull())
            this.getOrNull()!!.run {
                assertEquals(errorResponse, this)
            }
        }
    }

    // ============================= SOURCE ERROR TEST ============================ //
    private val sourceResponseItem1 = ErrorSourceResponseItem().apply {
        date = "18-03-21"
        this.name = "Source Dummy Name"
    }
    private val sourceResponseItem2 = ErrorSourceResponseItem().apply {
        date = "18-03-21"
        this.name = "Source Dummy Name 2"
    }
    private val sourceResponse = ErrorSourceResponse().apply {
        this.add(sourceResponseItem1)
        this.add(sourceResponseItem2)
    }

    @Test
    fun fetch_source_errors_and_update_live_data() {
        val source = "DummySource"
        val hours = 24
        whenever(mckApi.fetchSourceErrors(source, hours)).thenReturn(flow {
            emit(
                Result.success(
                    sourceResponse
                )
            )
        })
        repository.fetchErrorsSources(source, hours)
        verify(mckApi, times(1)).fetchSourceErrors(source, hours)

        repository.lvdSourceErrorsResponse.getValueForTest().run {
            assertNotNull(this)
        }
    }
}