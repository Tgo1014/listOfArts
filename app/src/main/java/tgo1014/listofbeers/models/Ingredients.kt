package tgo1014.listofbeers.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Ingredients(
    val hops: List<Any>,
    val malt: List<Malt>,
    val yeast: String,
)