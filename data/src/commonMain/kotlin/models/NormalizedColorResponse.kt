package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NormalizedColorResponse(
    @SerialName("percentage")
    val percentage: Int? = null,
    @SerialName("hex")
    val hex: String? = null
)