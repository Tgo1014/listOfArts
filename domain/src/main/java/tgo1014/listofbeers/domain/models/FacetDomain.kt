package tgo1014.listofbeers.domain.models

data class FacetDomain(
    val facets: List<FacetsDomain> = listOf(),
    val name: String = "",
    val otherTerms: Int = 0,
    val prettyName: Int = 0
)