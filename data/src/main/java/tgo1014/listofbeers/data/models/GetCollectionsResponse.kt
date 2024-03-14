package tgo1014.listofbeers.data.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetCollectionsResponse(
    @SerialName("elapsedMilliseconds")
    val elapsedMilliseconds: Int? = 0,
    @SerialName("count")
    val count: Int? = 0,
    @SerialName("countFacets")
    val countFacets: CountFacetsResponse? = CountFacetsResponse(),
    @SerialName("artObjects")
    val artObjects: List<ArtObjectResponse>? = listOf(),
    @SerialName("facets")
    val facets: List<FacetResponse>? = listOf()
)