package tgo1014.listofbeers.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Beer(
    val abv: Double,
    @Json(name = "attenuation_level")
    val attenuationLevel: Double,
    @Json(name = "boil_volume")
    val boilVolume: BoilVolume,
    @Json(name = "brewers_tips")
    val brewersTips: String,
    @Json(name = "contributed_by")
    val contributedBy: String,
    val description: String,
    val ebc: Double,
    @Json(name = "first_brewed")
    val firstBrewed: String,
    @Json(name = "food_pairing")
    val foodPairing: List<String>,
    val ibu: Double,
    val id: Int,
    @Json(name = "image_url")
    val imageUrl: String,
    val ingredients: Ingredients,
    val method: Method,
    val name: String,
    val ph: Double,
    val srm: Double,
    val tagline: String,
    @Json(name = "target_fg")
    val targetFg: Double,
    @Json(name = "target_og")
    val targetOg: Double,
    val volume: Volume,
)