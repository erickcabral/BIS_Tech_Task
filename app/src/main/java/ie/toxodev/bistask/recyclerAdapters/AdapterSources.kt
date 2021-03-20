package ie.toxodev.bistask.recyclerAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ie.toxodev.bistask.R
import ie.toxodev.bistask.databinding.ContErrorInfoBinding
import ie.toxodev.bistask.supportClasses.responses.sourceErrorsResponse.SourceErrorsResponse

class AdapterSources(private val sources: SourceErrorsResponse) :
    RecyclerView.Adapter<AdapterSources.VieHolderSources>() {


    class VieHolderSources(val binder: ContErrorInfoBinding) : RecyclerView.ViewHolder(binder.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VieHolderSources {
        LayoutInflater.from(parent.context).run {
            DataBindingUtil.inflate<ContErrorInfoBinding>(
                this,
                R.layout.cont_error_info,
                parent,
                false
            ).run {
                return VieHolderSources(this)
            }
        }
    }

    override fun onBindViewHolder(holder: VieHolderSources, position: Int) {
        holder.binder.sourceModel = sources[position]
    }

    override fun getItemCount(): Int = sources.size
}