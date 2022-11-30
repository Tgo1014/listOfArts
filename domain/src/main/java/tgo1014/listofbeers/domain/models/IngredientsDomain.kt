package tgo1014.listofbeers.domain.models

data class IngredientsDomain(
    val malt: List<MaltDomain>? = null,
    val hops: List<HopsDomain>? = null,
    val yeast: String? = null
)