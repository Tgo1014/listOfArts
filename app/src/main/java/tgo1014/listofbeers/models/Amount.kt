package tgo1014.listofbeers.models

import kotlinx.serialization.Serializable

@Serializable
data class Amount(
    val unit: String? = "",
    val value: Double? = 0.0,
)