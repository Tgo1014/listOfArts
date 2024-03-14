package tgo1014.listofbeers.data.models.museum


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