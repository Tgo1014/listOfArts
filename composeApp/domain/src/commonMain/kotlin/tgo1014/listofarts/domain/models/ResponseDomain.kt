package tgo1014.listofarts.domain.models

data class ResponseDomain(
    val elapsedMilliseconds: Int = 0,
    val count: Int = 0,
    val countFacets: CountFacetsDomain = CountFacetsDomain(),
    val artObjects: List<ArtObjectDomain> = listOf(),
    val facets: List<FacetDomain> = listOf(),
)
