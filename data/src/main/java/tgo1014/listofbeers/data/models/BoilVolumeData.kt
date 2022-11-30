package tgo1014.listofbeers.data.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BoilVolumeData(
    @SerialName("value")
    val value: Int? = null,
    @SerialName("unit")
    val unit: String? = null
)