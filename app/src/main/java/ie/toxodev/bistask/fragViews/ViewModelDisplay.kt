package ie.toxodev.bistask.fragViews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ie.toxodev.bistask.supportClasses.Repository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class ViewModelDisplay(private val repository: Repository) : ViewModel() {



    init {
        setupRepository(viewModelScope, Dispatchers.IO)
    }


    fun setupRepository(scope: CoroutineScope, dispatcher: CoroutineDispatcher) {
        this.repository.setupScope(scope, dispatcher)
    }

    fun fetchErrors(hours:Int) {
        this.repository.fetchErrors(hours)
    }

    fun getErrorsResponse() = this.repository.lvdErrorsResponse
}
