package ie.toxodev.bistask.supportClasses

import androidx.lifecycle.MutableLiveData
import ie.toxodev.bistask.supportClasses.responses.ErrorResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

class Repository(private val service: BisService) {

    var scope: CoroutineScope? = null

    val lvdErrorsResponse: MutableLiveData<Result<ErrorResponse>> = MutableLiveData()

    fun setupScope(scope: CoroutineScope, dispatcher: CoroutineDispatcher) {
        this.scope = scope.plus(dispatcher)
    }

    fun fetchErrors(hours:Int) {
        this.scope?.launch {
            service.fetchErrors(hours).collect {
                lvdErrorsResponse.postValue(it)
            }
        }
    }
}

