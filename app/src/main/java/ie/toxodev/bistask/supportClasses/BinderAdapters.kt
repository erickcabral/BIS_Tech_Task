package ie.toxodev.bistask.supportClasses

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import ie.toxodev.bistask.recyclerAdapters.AdapterError
import ie.toxodev.bistask.recyclerAdapters.AdapterSources

@BindingAdapter("bind:recycler_errors")
fun recyclerErrorsLoader(recyclerView: RecyclerView, adapter: AdapterError?) {
    adapter?.let {
        recyclerView.adapter = it
        it.notifyDataSetChanged()
    }

}

@BindingAdapter("bind:recycler_sources")
fun recyclerSourcesLoader(recyclerView: RecyclerView, adapter: AdapterSources?) {
    adapter?.let {
        recyclerView.adapter = it
        it.notifyDataSetChanged()
    }

}
