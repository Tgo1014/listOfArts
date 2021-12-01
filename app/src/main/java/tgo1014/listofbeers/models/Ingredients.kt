package tgo1014.listofbeers.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Ingredients(
    val hops: List<Any>? = listOf(),
    val malt: List<Malt>? = listOf(),
    val yeast: String? = "",
)