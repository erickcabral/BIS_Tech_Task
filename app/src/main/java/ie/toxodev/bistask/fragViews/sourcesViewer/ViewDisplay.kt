package ie.toxodev.bistask.fragViews.sourcesViewer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import ie.toxodev.bistask.R
import ie.toxodev.bistask.databinding.ViewDisplayBinding
import ie.toxodev.bistask.fragViews.dialogView.ViewDialog
import ie.toxodev.bistask.supportClasses.OutputManager
import ie.toxodev.bistask.supportClasses.binderModels.AdapterErrorSourcesModel

@AndroidEntryPoint
class ViewDisplay : Fragment(), AdapterErrorSourcesModel.IContErrorSourceListener,
    View.OnClickListener {
    companion object {
        const val TAG = "<<_TAG_ViewDisplay_>>"
    }

    private lateinit var vBinder: ViewDisplayBinding //Layout Binder
    private val vModel: ViewModelDisplay by viewModels()

    private lateinit var dialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.vBinder = DataBindingUtil.inflate(
            inflater,
            R.layout.view_display,
            container, false
        )

        this.vBinder.btnTimeStamp.setOnClickListener { btn ->
            ViewDialog(requireContext(), this).createDialog().also {
                this.dialog = it
                this.dialog.show()
            }
        }

        this.initializeLiveData()

        this.vModel.fetchErrorSources(3)
        return this.vBinder.root
    }


    private fun initializeLiveData() {
        this.vModel.getErrorSources().observe(viewLifecycleOwner, Observer { result ->
            result.onSuccess {
                AdapterErrorSourcesModel(it, this).run {
                    vBinder.adapterModel = this
                }
            }.onFailure {
                OutputManager.displayAlertPositiveOnly(
                    requireContext(),
                    "Server Error",
                    it.message.toString()
                )
            }
        })

        this.vModel.lvdHours.observe(viewLifecycleOwner, Observer {
            this.vBinder.hours = it
        })
    }

    override fun onItemClicked(view: View) {
        view.tag.toString().run {
            vModel.lvdHours.value?.let { hours ->
                ViewDisplayDirections.toErrorDetails(this, hours).run {
                    findNavController().navigate(this)
                }
            }
        }
    }

    override fun onClick(v: View?) {
        v?.let {
            when (it.id) {
                R.id.btnPositive -> setNewTimeStamp(dialog)
                else -> return
            }
        }
    }

    private fun setNewTimeStamp(dialog: AlertDialog) {
        dialog.run {
            (this.findViewById(R.id.inpTextView) as? TextInputEditText)?.run {
                if (!this.text.isNullOrEmpty()) {
                    this.text.toString().run {
                        vModel.fetchErrorSources(this.toInt())
                    }
                } else {
                    this.error = "Field can't be empty"
                    return
                }
            }
            this.dismiss()
        }
    }
}