package ie.toxodev.bistask.supportClasses

import ie.toxodev.bistask.supportClasses.responses.ErrorResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class BisService(private val bisAPI: BisAPI) {

    fun fetchErrors(hours: Int): Flow<Result<ErrorResponse>> {
        return flow<Result<ErrorResponse>> {
            bisAPI.fetchErrors(hours).run {
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

}