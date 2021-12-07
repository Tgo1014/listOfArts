package tgo1014.listofbeers.models

import kotlinx.serialization.Serializable

@Serializable
data class BoilVolume(
    val unit: String? = "",
    val value: Int? = 0,
)