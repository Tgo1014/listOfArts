package tgo1014.beerbox.models

import kotlinx.serialization.Serializable

@Serializable
data class Amount(
    val unit: String? = "",
    val value: Double? = 0.0,
)