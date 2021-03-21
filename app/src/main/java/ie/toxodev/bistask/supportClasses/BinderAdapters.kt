package ie.toxodev.bistask.supportClasses

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import ie.toxodev.bistask.recyclerAdapters.AdapterErrorDetails
import ie.toxodev.bistask.recyclerAdapters.AdapterErrorSources
import ie.toxodev.bistask.supportClasses.binderModels.AdapterErrorSourcesModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@BindingAdapter("bind:recycler_errors")
fun recyclerErrorsLoader(recyclerView: RecyclerView, adapter: AdapterErrorDetails?) {
    adapter?.let {
        recyclerView.adapter = it
        it.notifyDataSetChanged()
    }
}

@BindingAdapter("bind:recycler_sources")
fun recyclerSourcesLoader(recyclerView: RecyclerView, errorSources: AdapterErrorSourcesModel?) {
    errorSources?.let {
        AdapterErrorSources(it).run {
            recyclerView.adapter = this
            this.notifyDataSetChanged()
        }
    }
}

@BindingAdapter("bind:format_date")
fun formatDate(textView: TextView, date:String?){
    date?.let {date->
        DateTimeFormatter.ISO_LOCAL_DATE_TIME.parse(date).run {
            LocalDateTime.from(this).also { localDateTime->
                localDateTime.format(DateTimeFormatter.ofPattern("MMM dd,YYYY - HH:mm:ss")).also { formatted->
                    textView.text = formatted
                }
            }
        }
    }
}