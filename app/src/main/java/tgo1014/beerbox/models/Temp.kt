package tgo1014.beerbox.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Temp(
    @SerialName("value")
    val value: Int? = null,
    @SerialName("unit")
    val unit: String? = null
)