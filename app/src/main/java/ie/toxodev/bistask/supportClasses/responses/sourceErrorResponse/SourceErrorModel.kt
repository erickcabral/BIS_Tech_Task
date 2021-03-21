package ie.toxodev.bistask.supportClasses.responses.sourceErrorResponse


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class SourceErrorModel(
    @SerialName("noErrors")
    var noErrors: Int,
    @SerialName("source")
    var source: String
)