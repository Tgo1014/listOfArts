package tgo1014.listofbeers.data.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AcquisitionResponse(
    @SerialName("method")
    val method: String? = null,
    @SerialName("date")
    val date: String? = null,
    @SerialName("creditLine")
    val creditLine: String? = null
)