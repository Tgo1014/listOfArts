package tgo1014.listofarts.data.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HeaderImageResponse(
    @SerialName("guid")
    val guid: String? = null,
    @SerialName("offsetPercentageX")
    val offsetPercentageX: Int? = null,
    @SerialName("offsetPercentageY")
    val offsetPercentageY: Int? = null,
    @SerialName("width")
    val width: Int? = null,
    @SerialName("height")
    val height: Int? = null,
    @SerialName("url")
    val url: String? = null
)