package tgo1014.beerbox.models

import kotlinx.serialization.Serializable

@Serializable
data class Ingredients(
    val hops: List<Hops>? = listOf(),
    val malt: List<Malt>? = listOf(),
    val yeast: String? = "",
)