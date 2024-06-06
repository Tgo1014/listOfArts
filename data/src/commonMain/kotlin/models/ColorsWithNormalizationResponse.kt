package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ColorsWithNormalizationResponse(
    @SerialName("originalHex")
    val originalHex: String? = null,
    @SerialName("normalizedHex")
    val normalizedHex: String? = null
)