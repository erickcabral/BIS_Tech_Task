package ie.toxodev.bistask.supportClasses

import ie.toxodev.bistask.supportClasses.responses.ErrorResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface BisAPI {

    @GET("OPSERRORS / {HOURS}")
    suspend fun fetchErrors(
        @Path("HOURS") hours: Int
    ): Response<ErrorResponse>
}