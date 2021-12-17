package tgo1014.beerbox.models

import kotlinx.serialization.Serializable

@Serializable
data class BoilVolume(
    val unit: String? = "",
    val value: Int? = 0,
)