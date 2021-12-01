package tgo1014.listofbeers.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BoilVolume(
    val unit: String,
    val value: Int,
)