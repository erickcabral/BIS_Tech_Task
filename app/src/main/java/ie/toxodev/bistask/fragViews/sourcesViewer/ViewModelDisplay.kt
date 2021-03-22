package ie.toxodev.bistask.fragViews.sourcesViewer

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.toxodev.bistask.supportClasses.Repository
import ie.toxodev.bistask.supportClasses.responses.sourceErrorResponse.ErrorSourcesResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ViewModelDisplay @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val repository: Repository
) : ViewModel() {

    companion object {
        const val TAG = "<<_VM_SOURCE_ERRORS_>>"
        const val HOURS = "HOURS"
        const val SAVING_RESULT = "SAVED_RESULT"
    }

    val lvdHourSavingResult: MutableLiveData<Boolean> = MutableLiveData()
    var lvdHours: MutableLiveData<Int> = MutableLiveData()

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

    fun checkCache() {
        sharedPreferences.getInt(HOURS, 0).run {
            lvdHours.value = this
        }
    }

    fun setNewTimestamp(hour: Int) {
        this.sharedPreferences.edit().putInt(HOURS, hour).commit().also { isSuccess ->
            if (isSuccess) {
                lvdHours.value = hour
            }
            lvdHourSavingResult.value = isSuccess
        }
    }

    // ================== GETTERS ==================== //
    fun getErrorSources(): LiveData<Result<ErrorSourcesResponse>> =
        this.repository.lvdErrorSourcesResponse

}
