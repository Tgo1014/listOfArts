package tgo1014.beerbox.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Amount(
    @SerialName("value")
    val value: Double? = null,
    @SerialName("unit")
    val unit: String? = null
)