package ie.toxodev.bistask.supportClasses

import androidx.lifecycle.MutableLiveData
import ie.toxodev.bistask.supportClasses.responses.errorResponse.ErrorResponse
import ie.toxodev.bistask.supportClasses.responses.sourceErrorsResponse.SourceErrorsResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

class Repository(private val service: BisService) {
    var scope: CoroutineScope? = null
    val lvdErrorsResponse: MutableLiveData<Result<ErrorResponse>> = MutableLiveData()
    val lvdSourceErrorsResponse: MutableLiveData<Result<SourceErrorsResponse>> = MutableLiveData()

    fun setupScope(scope: CoroutineScope, dispatcher: CoroutineDispatcher) {
        this.scope = scope.plus(dispatcher)
    }

    fun fetchErrors(hours: Int) {
        this.scope?.launch {
            service.fetchErrors(hours).collect {
                lvdErrorsResponse.postValue(it)
            }
        }
    }

    fun fetchErrorsSources(source: String, hour: Int) {
        this.scope?.launch {
            service.fetchSourceErrors(source, hour).collect {
                lvdSourceErrorsResponse.postValue(it)
            }
        }
    }
}

