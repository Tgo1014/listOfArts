package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountFacetsResponse(
    @SerialName("hasimage")
    val hasimage: Int? = null,
    @SerialName("ondisplay")
    val ondisplay: Int? = null
)