package tgo1014.listofbeers.domain.models

data class BeerDomain(
    val id: Int? = 0,
    val name: String? = "",
    val tagline: String? = "",
    val firstBrewed: String? = "",
    val description: String? = "",
    val imageUrl: String? = "",
    val abv: Double? = 0.0,
    val ibu: Double? = 0.0,
    val targetFg: Double? = 0.0,
    val targetOg: Double? = 0.0,
    val ebc: Double? = 0.0,
    val srm: Double? = 0.0,
    val ph: Double? = 0.0,
    val attenuationLevel: Double? = 0.0,
    val volume: VolumeDomain? = VolumeDomain(),
    val boilVolume: BoilVolumeDomain? = BoilVolumeDomain(),
    val method: MethodDomain? = MethodDomain(),
    val ingredients: IngredientsDomain? = IngredientsDomain(),
    val foodPairing: List<String>? = listOf(),
    val brewersTips: String? = "",
    val contributedBy: String? = ""
)