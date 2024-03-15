package tgo1014.listofbeers.data.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArtObjectPageResponse(
    @SerialName("id")
    val id: String? = null,
    @SerialName("lang")
    val lang: String? = null,
    @SerialName("objectNumber")
    val objectNumber: String? = null,
    @SerialName("tags")
    val tags: List<String>? = null,
    @SerialName("plaqueDescription")
    val plaqueDescription: String? = null,
    @SerialName("createdOn")
    val createdOn: String? = null,
    @SerialName("updatedOn")
    val updatedOn: String? = null,
)