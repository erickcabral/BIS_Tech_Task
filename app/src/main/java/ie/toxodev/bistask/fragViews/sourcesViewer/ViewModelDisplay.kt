package ie.toxodev.bistask.fragViews.sourcesViewer

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
class ViewModelDisplay @Inject constructor(private val repository: Repository) : ViewModel() {

    val lvdHours = MutableLiveData<Int>()

    init {
        setupRepository(viewModelScope, Dispatchers.IO)
    }

    fun setupRepository(scope: CoroutineScope, dispatcher: CoroutineDispatcher) {
        this.repository.setupScope(scope, dispatcher)
    }

    fun fetchErrorSources(hours: Int) {
        this.lvdHours.value = hours
        this.repository.fetchErrorSources(hours)
    }

    fun fetchErrorDetails(source: String, hour: Int) {
        this.repository.fetchErrorDetails(source, hour)
    }

    // ================== GETTERS ==================== //
    fun getErrorSources() = this.repository.lvdErrorSourcesResponse
    fun getErrorDetails() = this.repository.lvdErrorDetailsResponse
}
