package tgo1014.listofbeers.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Temp(
    val unit: String = "",
    val value: Double = 0.0,
)