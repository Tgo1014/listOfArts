package tgo1014.listofarts.data.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FacetsResponse(
    @SerialName("key")
    val key: String? = null,
    @SerialName("value")
    val value: Int? = null
)