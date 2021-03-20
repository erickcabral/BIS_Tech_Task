package ie.toxodev.bistask.fragViews

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
import ie.toxodev.bistask.databinding.ViewDisplayBinding
import ie.toxodev.bistask.recyclerAdapters.AdapterError
import ie.toxodev.bistask.recyclerAdapters.AdapterSources

@AndroidEntryPoint
class ViewDisplay : Fragment() {
    companion object {
        const val TAG = "<<_TAG_ViewDisplay_>>"
    }

    private lateinit var vBinder: ViewDisplayBinding //Layout Binder
    private val vModel: ViewModelDisplay by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.vBinder = DataBindingUtil.inflate(
            inflater,
            R.layout.view_display,
            container, false
        )

        this.initializeLiveData()

        this.vModel.fetchErrors(10)
        this.vModel.fetchErrorsSources("MSTProc-PULSOLDSA01", 10)
        return this.vBinder.root
    }


    private fun initializeLiveData() {
        this.vModel.getSourceErrorResponse().observe(viewLifecycleOwner, Observer { result ->
            result.onSuccess {
                AdapterSources(it).run {
                    vBinder.sourceAdapter = this
                }
            }.onFailure {

            }
        })
        this.vModel.getErrorsResponse().observe(viewLifecycleOwner, Observer { result ->
            result.onFailure {

            }.onSuccess {
                AdapterError(it).run {
                    vBinder.errorAdapter = this
                }
            }
        })
    }
}