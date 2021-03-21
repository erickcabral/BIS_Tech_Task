package ie.toxodev.bistask.fragViews.dialogView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import ie.toxodev.bistask.R

class ViewDialog(context: Context, private val clickListener: View.OnClickListener) :
    MaterialAlertDialogBuilder(context) {

    private val inputEditText: TextInputEditText
    private val view: View

    init {
        LayoutInflater.from(context).inflate(R.layout.view_dialog, null).run {
            this.findViewById<TextInputEditText>(R.id.inpTextView).run {
                inputEditText = this
            }
            this.findViewById<MaterialButton>(R.id.btnPositive).setOnClickListener(clickListener)
            view = this
            setCancelable(false)
            setView(this)
        }
    }

    fun createDialog(): AlertDialog {
        return this.create().also { alert ->
            view.findViewById<MaterialButton>(R.id.btnNegative).setOnClickListener {
                alert.dismiss()
            }
        }
    }

}