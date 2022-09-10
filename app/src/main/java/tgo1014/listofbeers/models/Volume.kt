package tgo1014.listofbeers.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Volume(
    @SerialName("value")
    val value: Int? = null,
    @SerialName("unit")
    val unit: String? = null
)