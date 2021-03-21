package ie.toxodev.bistask.fragViews.errorsDetailViewer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.toxodev.bistask.supportClasses.Repository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ViewModelErrorDetails @Inject constructor(val repository: Repository) : ViewModel() {

    companion object {
        const val TAG = "<<_TAG_ViewModelErrorDetails_>>"
    }

    val lvdHours = MutableLiveData<Int>()

    init {
        this.setupRepository(viewModelScope, Dispatchers.IO)
    }

    fun setupRepository(scope: CoroutineScope, dispatcher: CoroutineDispatcher) {
        this.repository.setupScope(scope, dispatcher)
    }

    fun fetchErrorDetails(source: String, hours: Int) {
        this.repository.fetchErrorDetails(source, hours)
    }

    fun getErrorDetailsResponse() = this.repository.lvdErrorDetailsResponse
}