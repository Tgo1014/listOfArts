package tgo1014.listofbeers.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BeerData(
    @SerialName("id")
    val id: Int? = 0,
    @SerialName("name")
    val name: String? = "",
    @SerialName("tagline")
    val tagline: String? = "",
    @SerialName("first_brewed")
    val firstBrewed: String? = "",
    @SerialName("description")
    val description: String? = "",
    @SerialName("image_url")
    val imageUrl: String? = "",
    @SerialName("abv")
    val abv: Double? = 0.0,
    @SerialName("ibu")
    val ibu: Double? = 0.0,
    @SerialName("target_fg")
    val targetFg: Double? = 0.0,
    @SerialName("target_og")
    val targetOg: Double? = 0.0,
    @SerialName("ebc")
    val ebc: Double? = 0.0,
    @SerialName("srm")
    val srm: Double? = 0.0,
    @SerialName("ph")
    val ph: Double? = 0.0,
    @SerialName("attenuation_level")
    val attenuationLevel: Double? = 0.0,
    @SerialName("volume")
    val volume: VolumeData? = VolumeData(),
    @SerialName("boil_volume")
    val boilVolume: BoilVolumeData? = BoilVolumeData(),
    @SerialName("method")
    val method: MethodData? = MethodData(),
    @SerialName("ingredients")
    val ingredients: IngredientsData? = IngredientsData(),
    @SerialName("food_pairing")
    val foodPairing: List<String>? = listOf(),
    @SerialName("brewers_tips")
    val brewersTips: String? = "",
    @SerialName("contributed_by")
    val contributedBy: String? = ""
)