package ie.toxodev.bistask.recyclerAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ie.toxodev.bistask.R
import ie.toxodev.bistask.databinding.ContErrorInfoBinding
import ie.toxodev.bistask.supportClasses.responses.errorResponse.ErrorResponse

class AdapterError(private val errorResponse: ErrorResponse) :
    RecyclerView.Adapter<AdapterError.ViewHolderErrors>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderErrors {
        LayoutInflater.from(parent.context).run {
            DataBindingUtil.inflate<ContErrorInfoBinding>(
                this,
                R.layout.cont_error_info,
                parent,
                false
            ).run {
                return ViewHolderErrors(this)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolderErrors, position: Int) {
        holder.binder.errorModel = errorResponse[position]
    }

    override fun getItemCount(): Int = errorResponse.size


    class ViewHolderErrors(val binder: ContErrorInfoBinding) :
        RecyclerView.ViewHolder(binder.root)
}
