package ie.toxodev.bistask.supportClasses

import androidx.lifecycle.MutableLiveData
import ie.toxodev.bistask.supportClasses.responses.errorResponse.ErrorDetailResponse
import ie.toxodev.bistask.supportClasses.responses.sourceErrorResponse.ErrorSourcesResponse
import ie.toxodev.bistask.supportClasses.service.BisService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class Repository(private val service: BisService) {
    var scope: CoroutineScope? = null
    val lvdErrorSourcesResponse: MutableLiveData<Result<ErrorSourcesResponse>> = MutableLiveData()
    val lvdErrorDetailsResponse: MutableLiveData<Result<ErrorDetailResponse>> = MutableLiveData()

    fun setupScope(scope: CoroutineScope, dispatcher: CoroutineDispatcher) {
        this.scope = CoroutineScope(scope.coroutineContext + dispatcher)
    }

    fun fetchErrorSources(hours: Int) {
        this.scope?.launch {
            service.fetchErrorSources(hours).collect {
                lvdErrorSourcesResponse.postValue(it)
            }
        }
    }

    fun fetchErrorDetails(source: String, hour: Int) {
        this.scope?.launch {
            service.fetchErrorDetails(source, hour).collect {
                lvdErrorDetailsResponse.postValue(it)
            }
        }
    }
}

