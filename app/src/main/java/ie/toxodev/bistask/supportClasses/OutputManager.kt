package ie.toxodev.bistask.supportClasses

import android.content.Context
import android.content.DialogInterface
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object OutputManager {

    fun displayAlertPositiveOnly(context: Context, title: String, message: String) {
        MaterialAlertDialogBuilder(context).setTitle(title)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
            })
            .create()
            .show()
    }



}