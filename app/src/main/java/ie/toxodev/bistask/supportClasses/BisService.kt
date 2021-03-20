package ie.toxodev.bistask.supportClasses

import ie.toxodev.bistask.supportClasses.responses.errorResponse.ErrorResponse
import ie.toxodev.bistask.supportClasses.responses.sourceErrorsResponse.SourceErrorsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BisService @Inject constructor(private val bisAPI: BisAPI) {

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

   suspend fun fetchSourceErrors(source: String, hours: Int) : Flow<Result<SourceErrorsResponse>>{
        return flow <Result<SourceErrorsResponse>>{
            bisAPI.fetchSourceErrors(source, hours).run {
                if (this.isSuccessful && this.body()!=null){
                    this.body()!!.also {
                        emit(Result.success(it))
                    }
                }else{
                    emit(Result.failure(Throwable("Source Error Response failed")))
                }
            }
        }.catch {
            emit(Result.failure(Throwable("Server returned an INVALID response")))
        }
    }

}