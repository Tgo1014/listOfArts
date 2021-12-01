package tgo1014.listofbeers.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Malt(
    val amount: Amount,
    val name: String,
)