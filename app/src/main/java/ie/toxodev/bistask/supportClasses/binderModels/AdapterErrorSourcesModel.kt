package ie.toxodev.bistask.supportClasses.binderModels

import android.view.View
import ie.toxodev.bistask.supportClasses.responses.sourceErrorResponse.SourceErrorModel

class AdapterErrorSourcesModel(
    val list: List<SourceErrorModel>,
    val clickListener: IContErrorSourceListener
) {

    interface IContErrorSourceListener {
        fun onItemClicked(view: View)
    }
}