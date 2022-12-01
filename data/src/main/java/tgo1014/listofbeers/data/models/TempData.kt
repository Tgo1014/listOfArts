package tgo1014.listofbeers.data.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TempData(
    @SerialName("value")
    val value: Double? = null,
    @SerialName("unit")
    val unit: String? = null
)