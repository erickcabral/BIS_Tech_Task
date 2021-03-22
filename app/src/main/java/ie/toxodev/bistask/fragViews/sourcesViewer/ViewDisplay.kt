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

        vModel.checkTimeStamp()

        return this.vBinder.root
    }

    private fun openTimeStampDialog() {
        ViewDialog(requireContext(), this).createDialog().also {
            this.dialog = it
            this.dialog.show()
        }
    }


    private fun initializeLiveData(fragmentActivity: FragmentActivity) {
        this.vModel.getErrorSources().observe(fragmentActivity, Observer { result ->
            result.onSuccess {
                vModel.errorSourcesResponse = it
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

        this.vModel.lvdHours.observe(fragmentActivity, Observer { hours ->
            if (hours == 0) {
                this.openTimeStampDialog()
            } else {
                this.vBinder.hours = hours
                this.vModel.fetchErrorSources(hours)
            }
        })

        this.vModel.lvdNewStampResult.observe(fragmentActivity, Observer { success ->
            if (success) {
                OutputManager.displayAlertPositiveOnly(
                    requireContext(),
                    "Timestamp Saving result:",
                    "Success"
                )
            } else {
                OutputManager.displayAlertPositiveOnly(
                    requireContext(),
                    "Timestamp Saving result:",
                    "Failed"
                )
            }
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