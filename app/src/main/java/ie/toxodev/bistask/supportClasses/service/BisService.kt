package ie.toxodev.bistask.supportClasses.service

import ie.toxodev.bistask.supportClasses.responses.errorResponse.ErrorDetailResponse
import ie.toxodev.bistask.supportClasses.responses.sourceErrorResponse.ErrorSourcesResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BisService @Inject constructor(private val bisAPI: BisAPI) {

    suspend fun fetchErrorSources(hours: Int): Flow<Result<ErrorSourcesResponse>> {
        return flow<Result<ErrorSourcesResponse>> {
            bisAPI.fetchErrorSources(hours).run {
                if (this.isSuccessful && this.body() != null) {
                    this.body()!!.also { body ->
                        emit(Result.success(body))
                    }
                } else {
                    emit(Result.failure(Throwable("Error Response failed")))
                }
            }
        }.catch {
            emit(Result.failure(Throwable("Server returned an INVALID response")))
        }
    }

    suspend fun fetchErrorDetails(source: String, hours: Int): Flow<Result<ErrorDetailResponse>> {
        return flow<Result<ErrorDetailResponse>> {
            bisAPI.fetchSourceErrorDetails(source, hours).run {
                if (this.isSuccessful && this.body() != null) {
                    this.body()!!.also {
                        emit(Result.success(it))
                    }
                } else {
                    emit(Result.failure(Throwable("Source Error Response failed")))
                }
            }
        }.catch {
            emit(Result.failure(Throwable("Server returned an INVALID response")))
        }
    }

}