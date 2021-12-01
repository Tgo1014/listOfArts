package tgo1014.listofbeers.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Beer(
    val abv: Double? = 0.0,
    @Json(name = "attenuation_level")
    val attenuationLevel: Double? = 0.0,
    @Json(name = "boil_volume")
    val boilVolume: BoilVolume? = BoilVolume(),
    @Json(name = "brewers_tips")
    val brewersTips: String? = "",
    @Json(name = "contributed_by")
    val contributedBy: String? = "",
    val description: String? = "",
    val ebc: Double? = 0.0,
    @Json(name = "first_brewed")
    val firstBrewed: String? = "",
    @Json(name = "food_pairing")
    val foodPairing: List<String>? = listOf(),
    val ibu: Double? = 0.0,
    val id: Int? = 0,
    @Json(name = "image_url")
    val imageUrl: String? = "",
    val ingredients: Ingredients? = Ingredients(),
    val method: Method? = Method(),
    val name: String? = "",
    val ph: Double? = 0.0,
    val srm: Double? = 0.0,
    val tagline: String? = "",
    @Json(name = "target_fg")
    val targetFg: Double? = 0.0,
    @Json(name = "target_og")
    val targetOg: Double? = 0.0,
    val volume: Volume? = Volume(),
)