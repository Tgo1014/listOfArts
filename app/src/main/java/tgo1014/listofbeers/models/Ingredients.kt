package tgo1014.listofbeers.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Ingredients(
    @SerialName("malt")
    val malt: List<Malt>? = null,
    @SerialName("hops")
    val hops: List<Hops>? = null,
    @SerialName("yeast")
    val yeast: String? = null
)