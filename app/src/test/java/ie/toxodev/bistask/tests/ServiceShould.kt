package ie.toxodev.bistask.tests

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import ie.toxodev.bistask.baseJunitTest.BaseJunitTest
import ie.toxodev.bistask.supportClasses.responses.errorResponse.ErrorDetailResponse
import ie.toxodev.bistask.supportClasses.responses.sourceErrorResponse.ErrorSourcesResponse
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

    // ==================== ERROR RESPONSE TEST ================= //
    private val sourcesResponse = ErrorSourcesResponse()
    @Test
    fun fetch_error_sources_from_server() = runBlockingTest {
        val hours = 4
        whenever(mckAPI.fetchErrorSources(hours)).thenReturn(
            Response.success(sourcesResponse)
        )
        service.fetchErrorSources(hours).collect {
            verify(mckAPI, times(1)).fetchErrorSources(hours)
            assertNotNull(it)
        }
    }

    // ==================== SOURCE RESPONSE TEST ================= //

    private val errorDetailResponse = ErrorDetailResponse()
    @Test
    fun fetch_source_errors_from_server() = runBlockingTest {
        val source = "Dummy Source"
        val hours = 20
        val sourceResponse = ErrorSourcesResponse()
        whenever(
            mckAPI.fetchSourceErrorDetails(
                source,
                hours
            )
        ).thenReturn(Response.success(errorDetailResponse))
        service.fetchErrorDetails(source, hours).collect {
            verify(mckAPI, times(1)).fetchSourceErrorDetails(source, hours)
            assertNotNull(it)
            assertEquals(sourceResponse, it.getOrNull())
        }
    }
}