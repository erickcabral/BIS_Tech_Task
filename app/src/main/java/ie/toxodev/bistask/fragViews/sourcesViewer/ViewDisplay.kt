package ie.toxodev.bistask.fragViews.sourcesViewer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
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
    private val vModel: ViewModelDisplay by hiltNavGraphViewModels(R.id.navigation_main)

    private lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            initializeLiveData(it)
        }

    }

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
            this.openTimeStampDialog()
        }

        vModel.checkCache()

        return this.vBinder.root
    }

    private fun openTimeStampDialog() {
        ViewDialog(requireContext(), this).createDialog().also {
            this.dialog = it
            this.dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            this.dialog.show()
        }
    }


    private fun initializeLiveData(fragmentActivity: FragmentActivity) {

        this.vModel.lvdHours.observe(fragmentActivity, Observer {
            if (it == 0) {
                this.openTimeStampDialog()
            } else {
                this.vBinder.hours = it
                this.vModel.fetchErrorSources(it)
            }
        })

        this.vModel.getErrorSources().observe(fragmentActivity, Observer { result ->
            result.onSuccess {
                vBinder.adapterModel = AdapterErrorSourcesModel(it, this)
            }.onFailure {
                OutputManager.displayAlertPositiveOnly(
                    requireContext(),
                    "Server Error:",
                    it.message.toString()
                )
            }
        })

        this.vModel.lvdHourSavingResult.observe(fragmentActivity, Observer { isSuccess->
            if(isSuccess){
                OutputManager.displayAlertPositiveOnly(requireContext(), "Timestamp saving result:",
                "Timestamp saved successfully.")
            }else{
                OutputManager.displayAlertPositiveOnly(requireContext(), "Timestamp saving result:",
                    "Timestamp saving failed.")
            }
        })
    }

    override fun onItemClicked(view: View) {
        view.tag.toString().also { source ->
            vModel.lvdHours.value?.let {
                ViewDisplayDirections.toErrorDetails(source, it).run {
                    findNavController().navigate(this)
                }
            }
        }
    }

    override fun onClick(v: View?) {
        v?.let {
            when (it.id) {
                R.id.btnPositive -> setNewTimeStamp(dialog)
            }
        }
    }

    private fun setNewTimeStamp(dialog: AlertDialog) {
        dialog.run {
            (this.findViewById(R.id.inpTextView) as? TextInputEditText)?.run {
                when {
                    this.text.isNullOrEmpty() -> {
                        this.error = "Field can't be empty"
                        return
                    }
                    this.text.toString() == "0" -> {
                        this.error = "Timestamp must be greater than 0"
                        return
                    }
                    else -> {
                        this.text.toString().run {
                            vModel.setNewTimestamp(this.toInt())
                        }
                    }
                }
            }
            this.dismiss()
        }
    }
}