package ie.toxodev.bistask.recyclerAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ie.toxodev.bistask.R
import ie.toxodev.bistask.databinding.ContSourceErrorsBinding
import ie.toxodev.bistask.supportClasses.binderModels.AdapterErrorSourcesModel

class AdapterErrorSources(private val adapterModel:AdapterErrorSourcesModel) :
    RecyclerView.Adapter<AdapterErrorSources.VieHolderSources>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VieHolderSources {
        LayoutInflater.from(parent.context).run {
            DataBindingUtil.inflate<ContSourceErrorsBinding>(
                this,
                R.layout.cont_source_errors,
                parent,
                false
            ).run {
                return VieHolderSources(this)
            }
        }
    }

    override fun onBindViewHolder(holder: VieHolderSources, position: Int) {
        holder.binder.sourceModel = adapterModel.list[position]
        holder.binder.clicklistener = adapterModel.clickListener
    }

    override fun getItemCount(): Int = adapterModel.list.size



    class VieHolderSources(val binder: ContSourceErrorsBinding) :
        RecyclerView.ViewHolder(binder.root)

}