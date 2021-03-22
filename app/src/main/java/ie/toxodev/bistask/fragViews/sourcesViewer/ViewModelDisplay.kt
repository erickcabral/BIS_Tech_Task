package ie.toxodev.bistask.fragViews.sourcesViewer

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.toxodev.bistask.supportClasses.Repository
import ie.toxodev.bistask.supportClasses.responses.sourceErrorResponse.ErrorSourcesResponse
import ie.toxodev.bistask.supportClasses.responses.sourceErrorResponse.SourceErrorModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ViewModelDisplay @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val repository: Repository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        const val TAG = "<<_VM_SOURCE_ERRORS_>>"
        const val TIMESTAMP = "TIMESTAMP"
        const val HOURS = "HOURS"
        const val SAVING_RESULT = "SAVED_RESULT"
        const val SOURCES = "SOURCES"
    }

    var errorSourcesResponse: List<SourceErrorModel> = emptyList()

    val lvdHours = this.savedStateHandle.getLiveData(HOURS, 0)

    val lvdNewStampResult = this.savedStateHandle.getLiveData<Boolean>(SAVING_RESULT)

    init {
        setupRepository(viewModelScope, Dispatchers.IO)
    }

    fun setupRepository(scope: CoroutineScope, dispatcher: CoroutineDispatcher) {
        this.repository.setupScope(scope, dispatcher)
    }

    fun fetchErrorSources(hours: Int) {
        this.repository.fetchErrorSources(hours)
    }

    fun fetchErrorDetails(source: String, hour: Int) {
        this.repository.fetchErrorDetails(source, hour)
    }

    fun checkTimeStamp() {
        sharedPreferences.getInt(TIMESTAMP, 0).run {
            savedStateHandle[HOURS] = this
        }
    }

    fun setNewTimestamp(newStamp: Int) {
        this.sharedPreferences.edit().putInt(TIMESTAMP, newStamp).commit().also { isSuccess ->
            if (isSuccess) {
                savedStateHandle[HOURS] = newStamp
            }
            this.savedStateHandle[SAVING_RESULT] = isSuccess
        }
    }

    // ================== GETTERS ==================== //
    fun getErrorSources(): LiveData<Result<ErrorSourcesResponse>> =
        this.repository.lvdErrorSourcesResponse

}
