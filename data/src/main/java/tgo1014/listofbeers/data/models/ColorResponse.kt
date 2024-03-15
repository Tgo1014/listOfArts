package tgo1014.listofbeers.data.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ColorResponse(
    @SerialName("percentage")
    val percentage: Int? = null,
    @SerialName("hex")
    val hex: String? = null
)