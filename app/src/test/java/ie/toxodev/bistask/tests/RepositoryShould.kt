package ie.toxodev.bistask.tests

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import getValueForTest
import ie.toxodev.bistask.baseJunitTest.BaseJunitTest
import ie.toxodev.bistask.supportClasses.BisService
import ie.toxodev.bistask.supportClasses.Repository
import ie.toxodev.bistask.supportClasses.responses.ErrorResponse
import ie.toxodev.bistask.supportClasses.responses.ErrorResponseItem
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
    fun fetch_error_and_update_live_data() = runBlockingTest {
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

}