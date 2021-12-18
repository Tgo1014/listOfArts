package tgo1014.beerbox.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Beer(
    val abv: Double? = 0.0,
    @SerialName("attenuation_level")
    val attenuationLevel: Double? = 0.0,
    @SerialName("boil_volume")
    val boilVolume: BoilVolume? = BoilVolume(),
    @SerialName("brewers_tips")
    val brewersTips: String? = "",
    @SerialName("contributed_by")
    val contributedBy: String? = "",
    val description: String? = "",
    val ebc: Double? = 0.0,
    @SerialName("first_brewed")
    val firstBrewed: String? = "",
    @SerialName("food_pairing")
    val foodPairing: List<String>? = listOf(),
    val ibu: Double? = 0.0,
    val id: Int = 0,
    @SerialName("image_url")
    val imageUrl: String? = "",
    val ingredients: Ingredients? = Ingredients(),
    val method: Method? = Method(),
    val name: String? = "",
    val ph: Double? = 0.0,
    val srm: Double? = 0.0,
    val tagline: String? = "",
    @SerialName("target_fg")
    val targetFg: Double? = 0.0,
    @SerialName("target_og")
    val targetOg: Double? = 0.0,
    val volume: Volume? = Volume(),
)