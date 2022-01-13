package tgo1014.beerbox.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Beer(
    @SerialName("id")
    val id: Int = -1,
    @SerialName("name")
    val name: String? = null,
    @SerialName("tagline")
    val tagline: String? = null,
    @SerialName("first_brewed")
    val firstBrewed: String? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("image_url")
    val imageUrl: String? = null,
    @SerialName("abv")
    val abv: Double? = null,
    @SerialName("ibu")
    val ibu: Double? = null,
    @SerialName("target_fg")
    val targetFg: Double? = null,
    @SerialName("target_og")
    val targetOg: Double? = null,
    @SerialName("ebc")
    val ebc: Double? = null,
    @SerialName("srm")
    val srm: Double? = null,
    @SerialName("ph")
    val ph: Double? = null,
    @SerialName("attenuation_level")
    val attenuationLevel: Double? = null,
    @SerialName("volume")
    val volume: Volume? = null,
    @SerialName("boil_volume")
    val boilVolume: BoilVolume? = null,
    @SerialName("method")
    val method: Method? = null,
    @SerialName("ingredients")
    val ingredients: Ingredients? = null,
    @SerialName("food_pairing")
    val foodPairing: List<String>? = null,
    @SerialName("brewers_tips")
    val brewersTips: String? = null,
    @SerialName("contributed_by")
    val contributedBy: String? = null
)