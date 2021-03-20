package ie.toxodev.bistask.supportClasses.service

import ie.toxodev.bistask.supportClasses.responses.errorResponse.ErrorResponse
import ie.toxodev.bistask.supportClasses.responses.sourceErrorsResponse.SourceErrorsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BisAPI {

    @GET("OpsErrors/{hours}")
    suspend fun fetchErrors(
        @Path("hours") hours: Int
    ): Response<ErrorResponse>

    @GET("OpsErrors/{source}/errors")
    suspend fun fetchSourceErrors(
        @Path("source") source: String,
        @Query("hours") hours: Int
    ): Response<SourceErrorsResponse>
}