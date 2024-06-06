package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FacetResponse(
    @SerialName("facets")
    val facets: List<FacetsResponse>? = listOf(),
    @SerialName("name")
    val name: String? = "",
    @SerialName("otherTerms")
    val otherTerms: Int? = 0,
    @SerialName("prettyName")
    val prettyName: Int? = 0
)