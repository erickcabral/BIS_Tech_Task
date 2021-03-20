package ie.toxodev.bistask.tests

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import ie.toxodev.bistask.baseJunitTest.BaseJunitTest
import ie.toxodev.bistask.supportClasses.responses.errorResponse.ErrorResponse
import ie.toxodev.bistask.supportClasses.service.BisAPI
import ie.toxodev.bistask.supportClasses.service.BisService
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import retrofit2.Response

class ServiceShould : BaseJunitTest() {

    private val service: BisService
    private val mckAPI: BisAPI = mock()

    init {
        this.service = BisService(mckAPI)
    }

    @Test
    fun fetch_error_from_data_base() = runBlockingTest {
        val hours = 4
        whenever(mckAPI.fetchErrors(hours)).thenReturn(
            Response.success(ErrorResponse())
        )
        service.fetchErrors(hours).collect {
            verify(mckAPI, times(1)).fetchErrors(hours)
            assertNotNull(it)
        }
    }

    // ==================== SOURCE RESPONSE TEST ================= //

    @Test
    fun fetch_source_errors_from_server() = runBlockingTest {
        val source = "Dummy Source"
        val hours = 20
        val sourceResponse = ErrorSourceResponse()
        whenever(
            mckAPI.fetchSourceErrors(
                source,
                hours
            )
        ).thenReturn(Response.success(sourceResponse))
        service.fetchSourceErrors(source, hours).collect {
            verify(mckAPI, times(1)).fetchSourceErrors(source, hours)
            assertNotNull(it)
            assertEquals(sourceResponse, it.getOrNull())
        }
    }
}