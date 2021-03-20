package ie.toxodev.bistask.supportClasses.responses


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ErrorResponseItem(
    @SerialName("noErrors")
    var noErrors: Int = 0,
    @SerialName("source")
    var source: String = ""
)