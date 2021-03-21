package ie.toxodev.bistask.supportClasses.responses.errorResponse


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ErrorDetailModel(
    @SerialName("date")
    var date: String = "",
    @SerialName("name")
    var name: String = ""
)