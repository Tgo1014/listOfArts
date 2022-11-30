package tgo1014.listofbeers.data.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IngredientsData(
    @SerialName("malt")
    val malt: List<MaltData>? = null,
    @SerialName("hops")
    val hops: List<HopsData>? = null,
    @SerialName("yeast")
    val yeast: String? = null
)