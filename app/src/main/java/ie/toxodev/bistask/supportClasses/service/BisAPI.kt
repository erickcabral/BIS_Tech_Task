package ie.toxodev.bistask.supportClasses.service

import ie.toxodev.bistask.supportClasses.responses.errorResponse.ErrorDetailResponse
import ie.toxodev.bistask.supportClasses.responses.sourceErrorResponse.ErrorSourcesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BisAPI {

    @GET("OpsErrors/{hours}")
    suspend fun fetchErrorSources(
        @Path("hours") hours: Int
    ): Response<ErrorSourcesResponse>

    @GET("OpsErrors/{source}/errors")
    suspend fun fetchSourceErrorDetails(
        @Path("source") source: String,
        @Query("hours") hours: Int
    ): Response<ErrorDetailResponse>
}