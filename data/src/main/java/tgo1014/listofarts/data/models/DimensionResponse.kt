package tgo1014.listofarts.data.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DimensionResponse(
    @SerialName("unit")
    val unit: String? = null,
    @SerialName("type")
    val type: String? = null,
    @SerialName("value")
    val value: String? = null
)