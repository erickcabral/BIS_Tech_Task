package ie.toxodev.bistask.supportClasses.responses.sourceErrorsResponse


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class SourceErrorItem(
    @SerialName("date")
    var date: String = "",
    @SerialName("name")
    var name: String = ""
)