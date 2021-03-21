package ie.toxodev.bistask.fragViews.errorsDetailViewer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import ie.toxodev.bistask.R
import ie.toxodev.bistask.databinding.ViewErrorsDetailedBinding
import ie.toxodev.bistask.recyclerAdapters.AdapterErrorDetails

@AndroidEntryPoint
class ViewErrorDetail : Fragment() {
    companion object {
        const val TAG = "<<_TAG_ViewErrorDetail_>>"
        const val SOURCE = "SOURCE"
        const val HOURS = "HOURS"
    }

    private lateinit var vBinder: ViewErrorsDetailedBinding //Layout Binder
    private val vModel: ViewModelErrorDetails by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.vBinder = DataBindingUtil.inflate(
            inflater,
            R.layout.view_errors_detailed,
            container, false
        )

        this.initializeLiveData()

        arguments?.let { bundle ->
            bundle.getString(SOURCE).also { source ->
                bundle.getInt(HOURS).also { hours ->
                    if (source != null && hours != 0) {
                        this.vBinder.source = source
                        this.vModel.fetchErrorDetails(source, hours)
                    } else {
                        TODO("Implement later")
                    }
                }
            }
        }

        return this.vBinder.root
    }


    private fun initializeLiveData() {
        this.vModel.getErrorDetailsResponse().observe(viewLifecycleOwner, Observer { erroDetail ->
            erroDetail.onSuccess {
                vBinder.totalErrors = it.size.toString()
                AdapterErrorDetails(it).run {
                    vBinder.errorDetailsAdapter = this
                }
            }.onFailure {

            }
        })
    }
}